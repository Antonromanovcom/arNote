package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.exceptions.MoexXmlResponseMappingException;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.CurrentPriceRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.DividendRs;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.enums.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexRowsForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDataRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.DataBlock;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexInstrumentDetailRowsRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.service.investment.cache.CacheService;
import com.antonromanov.arnote.service.investment.http.client.ArNoteHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.utils.ArNoteUtils.isInteger;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Slf4j
public class CalculateServiceImpl implements CalculateService {

    private final ArNoteHttpClient httpClient;
    private final CacheService cacheService;
    private final List<String> BOARD_GROUP_LIST = Arrays.asList("58", "193", "7", "67", "207");
    private Long lastQuote = 0L;
    private Long getAllSharesCount = 0L;

    public CalculateServiceImpl(ArNoteHttpClient httpClient, BondsRepo repo, CacheService cacheService) {
        this.httpClient = httpClient;
        this.cacheService = cacheService;
    }


    /**
     * Запросить дивиденды через API биржи, подсчитать сумму проценты относительно текущей цены акции и вернуть все это.
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    @Cacheable(cacheNames = "divsByTicker", key = "#user.id")
    public Optional<ConsolidatedDividendsRs> getDivsByTicker(ArNoteUser user, String ticker) {
        if (!isBlank(ticker)) {
            Optional<ConsolidatedDividendsRs> res = httpClient.sendAndParse(ticker);
            if (res.isPresent()) {
                res.get().calculatePercent(getHistory(ticker, getBoardId(ticker)));
                return res;
            }
        }
        return Optional.empty();
    }

    /**
     * Запросить текущую цену (ставку) бумаги.
     *
     * @return
     */
    @Override
    public Optional<Double> getCurrentQuoteByTicker(String ticker) {
        if (!isBlank(ticker)) {
            return (getCurrentQuoteByBoardId(getBoardId(ticker)))
                    .map(lq -> lq.getData()
                            .getRow()
                            .stream()
                            .filter(r -> ticker.equals(r.getSecid()))
                            .findFirst()
                            .map(q -> Double.valueOf(q.getPrevAdmittedQuote())))
                    .orElse(Optional.of((double) 0));

        } else {
            return Optional.empty();
        }
    }

    /**
     * Запросить текущую цену акции по тикеру. Та, что обновляется раз в 15 минут с торгов.
     *
     * @return
     */
    @Override
    public CurrentPriceRs getCurrentQuoteWith15MinuteUpdate(String ticker) {

            if (LocalTime.now().isBefore(LocalTime.of(10, 30))){
                return getCurrentQuoteByTicker(ticker)
                        .map(cp-> CurrentPriceRs.builder()
                                .ticker(ticker)
                                .currency(Currencies.RUB)
                                .currentPrice(cp)
                                .date(LocalDate.now())
                                .time(LocalTime.now())
                                .lastChange(null)
                                .lastChangePrcnt(null)
                                .build())
                        .orElse(CurrentPriceRs.builder()
                                .ticker(ticker)
                                .currency(Currencies.RUB)
                                .currentPrice(null)
                                .date(LocalDate.now())
                                .time(LocalTime.now())
                                .lastChange(null)
                                .lastChangePrcnt(null)
                                .build());
            } else {

                MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(RestTemplateOperation.GET_15_MINUTE_PRICE_UPDATE,
                        ticker, null);

                return CurrentPriceRs.builder()
                        .ticker(ticker)
                        .currency(Currencies.RUB)
                        .currentPrice(doc.getData().getRow().stream()
                                .filter(r->getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getLast15MinuteQuote)
                                .map(Double::parseDouble).orElse(0D))
                        .date(LocalDate.now())
                        .time(doc.getData().getRow().stream()
                                .filter(r->getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getUpdateTime)
                                .map(LocalTime::parse).orElse(LocalTime.now()))
                        .lastChange(doc.getData().getRow().stream()
                                .filter(r->getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getLastChange)
                                .map(Double::parseDouble).orElse(0D))
                        .lastChangePrcnt(doc.getData().getRow().stream()
                                .filter(r->getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getLastChangePrcnt)
                                .map(Double::parseDouble).orElse(0D))
                        .build();
            }
    }

    /**
     * Запросить дату по текущей цене акции по тикеру.
     *
     * @return
     */
    @Override
    public Optional<LocalDate> getCurrentQuoteDateByTicker(String ticker) {
        if (!isBlank(ticker)) {
            return (getCurrentQuoteByBoardId(getBoardId(ticker)))
                    .map(lq -> lq.getData()
                            .getRow()
                            .stream()
                            .filter(r -> ticker.equals(r.getSecid()))
                            .findFirst()
                            .map(q -> LocalDate.parse(q.getTradeDate()))
                            .orElse(LocalDate.now()));

        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MoexDocumentRs> getCurrentQuoteByBoardId(String boardId) {
        MoexDocumentRs resultDoc = cacheService.getQuotesByBoardId(boardId).orElseGet(() -> {
            lastQuote = lastQuote + 1;
            log.info("Запрос последней ставки по boardId: {}. Запрос №: {}", boardId, lastQuote);

            MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(RestTemplateOperation.GET_LAST_QUOTE_MOEX, null, boardId);
            cacheService.putLastQuotes(boardId, doc);
            return doc;
        });

        return Optional.of(resultDoc);
    }

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    public Optional<MoexDetailInfoRs> getDetailInfo(ArNoteUser user, String ticker) {
        if (!isBlank(ticker)) {
            return Optional.ofNullable((MoexDetailInfoRs) (httpClient.sendAndMarshall(RestTemplateOperation.
                    GET_INSTRUMENT_DETAIL_INFO, ticker, null)));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Запросить board_id.
     *
     * @param ticker - тикер.
     * @return
     */
    @Override
    public String getBoardId(String ticker) {
        return cacheService.getBoardIdByTicker(ticker).orElseGet(() -> {

            String boardId = ((MoexDocumentForBoardIdRs)
                    (httpClient.sendAndMarshall(RestTemplateOperation.GET_BOARD_ID, ticker, null)))
                    .getData()
                    .getRowList()
                    .stream()
                    .filter(MoexRowsForBoardIdRs::getIsPrimary)
                    .findFirst()
                    .map(MoexRowsForBoardIdRs::getBoardId)
                    .orElse(null);

            cacheService.putBoardId(ticker, boardId);
            return boardId;
        });
    }

    /**
     * Запросить имя инструмента.
     *
     * @param ticker - тикер.
     * @return
     */
    @Override
    @Cacheable(cacheNames = "instrumentNames", key = "#boardId + #ticker")
    public Optional<String> getInstrumentName(String boardId, String ticker) {
        if (!isBlank(ticker)) {
            return Optional.ofNullable(httpClient.sendAndMarshall(RestTemplateOperation.GET_INSTRUMENT_NAME, null, boardId))
                    .map(MoexDocumentRs.class::cast)
                    .map(p -> p.getData()
                            .getRow()
                            .stream()
                            .filter(r -> ticker.equals(r.getSecid()))
                            .findFirst()
                            .map(MoexRowsRs::getSecName))
                    .orElse(null);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Запросить и посчитать дельту.
     *
     * @param ticker            - тикер.
     * @param currentStockPrice - текущая цена по рынку (всегда в рублях).
     * @param purchaseList      - список покупок пользователя.
     * @return
     */
    @Override
    public DeltaRs calculateDelta(String boardId, String ticker, Double currentStockPrice, List<Purchase> purchaseList) {

        if (!isBlank(ticker) && !isBlank(boardId) && (currentStockPrice != null && currentStockPrice > 0)) {

            double tinkoffDeltaFinal = 0;
            double tinkoffDeltaPercent = 0;

            if (purchaseList != null && purchaseList.size() > 0) {

                /*
                 * Считаем среднюю цену покупки (сумма цена * лот)
                 */
                Double tkcAveragePurchasePrice = purchaseList.stream()
                        .map(p -> p.getPrice() * p.getLot())
                        .reduce((double) 0, Double::sum);

                double tinkoffSameLotButNewPrice = (purchaseList.stream()
                        .map(Purchase::getLot)
                        .reduce(0, Integer::sum)) * currentStockPrice;

                tinkoffDeltaFinal = tinkoffSameLotButNewPrice - tkcAveragePurchasePrice;
                tinkoffDeltaPercent = (tinkoffDeltaFinal * 100) / tinkoffSameLotButNewPrice;

            }

            MoexDocumentRs doc = getHistory(ticker, boardId);

            /*
             * Как считаем:
             *
             * deltaInRubles = текущая цена - (цена по самой ранней дате)
             * deltaPeriod = Миллисекунды от (текущая дата - (самая ранняя дата истории))
             * tinkoffDelta = (сумма покупок * текущую цену рынка) - (Сумма(лот * цену по каждой покупке))
             */
            return DeltaRs.builder()
                    .tinkoffDelta(tinkoffDeltaFinal)
                    .tinkoffDeltaPercent(tinkoffDeltaPercent)
                    .deltaInRubles(doc.getData()
                            .getRow()
                            .stream()
                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                            .map(dv -> Double.valueOf(dv.getLegalClosePrice()))
                            .map(Math::round)
                            .map(n -> Math.round(currentStockPrice) - n)
                            .orElse(0L))
                    .totalPercent(doc.getData()
                            .getRow()
                            .stream()
                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                            .map(dv -> Double.valueOf(dv.getLegalClosePrice()))
                            .map(n -> ((Math.round(currentStockPrice) - n)/n)*100)
                            .orElse(0D))
                    .deltaPeriod(doc.getData()
                            .getRow()
                            .stream()
                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                            .map(r -> {
                                Period period = Period.between(LocalDate.parse(r.getTradeDate()), LocalDate.now());
                                int diffInDays = Math.abs(period.getDays());
                                return TimeUnit.DAYS.toMillis(diffInDays);
                            })
                            .orElse(0L))
                    .build();


        } else {
            return null;
        }
    }

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @param bond
     * @param user
     * @return
     */
    @Override
    public Integer calculateFinalPrice(Bond bond, ArNoteUser user) {
        if (bond.getType() == BondType.SHARE) {

            if (bond.getIsBought()) { // если это ФАКТ
                return bond.getPurchaseList().stream()
                        .map(p -> p.getLot() * p.getPrice())
                        .reduce((double) 0, Double::sum).intValue();
            } else { // если ПЛАН
                return (int) Math.round(((getCurrentQuoteWith15MinuteUpdate(bond.getTicker()
                )).getCurrentPrice()) * getMinimalLot(bond.getTicker(), user));
            }

        } else {
            if (bond.getIsBought()) { // если это ФАКТ
                return bond.getPurchaseList().stream()
                        .map(p -> p.getLot() * p.getPrice())
                        .reduce((double) 0, Double::sum).intValue();
            } else { // если ПЛАН
                return (getBondLot(bond, user, bond.getPurchaseList())) * (getCurrentBondPrice(bond.getTicker()).intValue());
            }

        }
    }

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    @Override
    public ConsolidatedDividendsRs getDividends(Bond bond, ArNoteUser user) {
        return getDivsByTicker(user, bond.getTicker())
                .orElse(ConsolidatedDividendsRs.builder()
                        .dividendList(Collections.emptyList())
                        .percent(0D)
                        .divSum(0D)
                        .build());
    }

    /**
     * Подготовить данные по валюте.
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "currenciesCache", key = "#user.id")
    public String getCurrencyOfShareFromDetailInfo(String ticker, ArNoteUser user) {
        return getDetailInfo(user, ticker)
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> DataBlock.SECURITIES.getCode().equals(data.getId()))
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> getBoardId(ticker).equals(row.getBoardId()))
                                .findFirst()
                                .map(MoexInstrumentDetailRowsRs::getCurrencyId)
                                .map(Currencies::search)
                                .map(Enum::name)
                                .orElse("RUB"))
                        .orElse("RUB"))
                .orElse("RUB");
    }

    /**
     * Достать минимальный лот.
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "minimalLotsCache", key = "#user.id")
    public Integer getMinimalLot(String ticker, ArNoteUser user) {
        return getDetailInfo(user, ticker)
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> DataBlock.SECURITIES.getCode().equals(data.getId()))
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> getBoardId(ticker).equals(row.getBoardId()))
                                .findFirst()
                                .map(share -> Integer.parseInt(share.getLotSize()))
                                .orElse(1))
                        .orElse(1))
                .orElse(1);
    }

    /**
     * Запросить исторические данные по котировкам с биржи.
     *
     * @param ticker
     * @param boardId
     * @return
     */
    @Override
    public MoexDocumentRs getHistory(String ticker, String boardId) {
        return cacheService.getHistory(ticker + boardId)
                .orElseGet(() -> {
                    int start = 0; // начальная страница
                    int step = 100; // шаг перемещения
                    boolean isFinalPage = false; // проверочная переменная, определяющая, что дальше циклить не надо и мы достигли конца истории.
                    MoexDocumentRs resultDoc = new MoexDocumentRs(); // финальный документ с историей, заполненный всеми страницами

                    while (!isFinalPage) {
                        /*
                         * Выкачиваем данные постранично и сохраняем в локальную переменную
                         */
                        log.info("Запрашиваем историю. start = {}", start);
                        MoexDocumentRs localDoc = (MoexDocumentRs) httpClient.getHistory(RestTemplateOperation.GET_DELTA,
                                ticker, boardId, "2000-01-01", LocalDate.now().toString(), start);
                        log.info("Запросили историю. Получили записей: {}", localDoc.getData().getRow().size());

                        if (localDoc.getData() == null ||
                                localDoc.getData().getRow().size() == 0 ||
                                localDoc.getData().getRow().stream().anyMatch(r ->
                                        LocalDate.parse(r.getTradeDate()).getYear() == LocalDate.now().getYear() &&
                                                LocalDate.parse(r.getTradeDate()).getMonthValue() == LocalDate.now().getMonthValue())) {
                            isFinalPage = true;
                            log.warn("Закончили запрос на start = : {}", start);
                        } else {
                            /*
                             * Подливаем строки из локального документа в итоговый.
                             */
                            if (resultDoc.getData() == null) {
                                MoexDataRs dataRs = new MoexDataRs();
                                resultDoc.setData(dataRs);
                            }
                            resultDoc.getData().getRow().addAll(localDoc.getData().getRow());
                            log.info("Получили данные с {} {}", resultDoc.getData().getRow().stream()
                                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                                            .map(d -> (LocalDate.parse(d.getTradeDate())).getMonth()
                                                    .getDisplayName(TextStyle.FULL, new Locale("ru")))
                                            .orElse("-"),
                                    resultDoc.getData().getRow().stream()
                                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                                            .map(d -> (LocalDate.parse(d.getTradeDate())).getYear()).orElse(0));

                            log.info("Получили данные по {} {}", resultDoc.getData().getRow().stream()
                                            .max(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                                            .map(d -> (LocalDate.parse(d.getTradeDate())).getMonth()
                                                    .getDisplayName(TextStyle.FULL, new Locale("ru")))
                                            .orElse("-"),
                                    resultDoc.getData().getRow().stream()
                                            .max(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                                            .map(d -> (LocalDate.parse(d.getTradeDate())).getYear()).orElse(0));

                            log.info("Получили записей: {}", resultDoc.getData().getRow().size());
                        }
                        start = start + step;
                    }
                    cacheService.putHistory(ticker + boardId, resultDoc);
                    return resultDoc;
                });
    }

    @Override
    public MoexDocumentRs getBondsFromMoexForBoardGroup(String boardGroup) {
        return cacheService.getBondsByBoardGroup(boardGroup)
                .orElseGet(() -> {
                    MoexDocumentRs doc = (MoexDocumentRs) httpClient
                            .sendAndMarshall(RestTemplateOperation.GET_BONDS, boardGroup, null);
                    cacheService.putBondsByBoardsGroup(boardGroup, doc);
                    return doc;
                });

    }

    @Override
    public MoexDocumentRs getBondsFromMoex() {

        Iterator<String> it = BOARD_GROUP_LIST.iterator();
        MoexDocumentRs result = new MoexDocumentRs();
        while (it.hasNext()) {
            if (result.getData() == null) {
                result = getBondsFromMoexForBoardGroup(it.next());
            } else {
                result.getData().getRow().addAll((getBondsFromMoexForBoardGroup(it.next())).getData().getRow());
            }
        }
        return result;
    }

    /**
     * Запросить Облигацию по тикеру.
     *
     * @param ticker - тикер
     * @return
     */
    @Override
    public Optional<MoexRowsRs> getBondDataByTicker(String ticker) {

        return getBondsFromMoex()
                .getData()
                .getRow()
                .stream()
                .filter(b -> ticker.equals(b.getSecid()))
                .findFirst();
    }

    /**
     * Определить валюту и курсовой-множитель для рубля.
     *
     * @param currency - валютный идентификатор
     * @return
     */
    @Override
    public Double getCurrencyMultiplier(String currency) {

        if (Currencies.getTransferByCodes(currency) == null) {
            return (1d);
        } else {

            return Optional.ofNullable(httpClient.sendAndMarshall(RestTemplateOperation.GET_CURRENCY_CHANGE_COURSES,
                    null, null))
                    .map(MoexDocumentRs.class::cast)
                    .orElseThrow(() -> new MoexXmlResponseMappingException("курсы валют"))
                    .getData()
                    .getRow()
                    .stream()
                    .filter(curr -> curr.getCurrencyExchangeType().equals(Currencies.getTransferByCodes(currency)))
                    .findFirst()
                    .map(MoexRowsRs::getRate)
                    .map(Double::valueOf)
                    .orElse(Double.valueOf("1"));
        }
    }

    @Override
    public Double getCurrentBondPrice(String ticker) {
        return getBondDataByTicker(ticker)
                .map(p -> (
                        (Double.parseDouble(p.getLotValue()) * Double.parseDouble(p.getPrevLegalClosePrice())) / 100)
                        * getCurrencyMultiplier(p.getCurrencyId())).orElse(0D);

    }

    @Override
    public Currencies getBondCurrency(String ticker) {
        return getBondDataByTicker(ticker)
                .map(MoexRowsRs::getCurrencyId)
                .map(Currencies::search)
                .orElse(Currencies.RUB);

    }

    /**
     * Получить имя облигации.
     *
     * @param ticker
     * @return
     */
    @Override
    public Optional<String> getBondName(String ticker) {
        return getBondDataByTicker(ticker).map(MoexRowsRs::getSecName);
    }

    /**
     * Получить минимальный лот облигации или сколько куплено уже.
     *
     * @return
     */
    @Override
    public Integer getBondLot(Bond bond, ArNoteUser user, List<Purchase> purchaseList) {

        if (!bond.getIsBought()) { // если это план по облигации
            return getBondDataByTicker(bond.getTicker())
                    .map(MoexRowsRs::getLotSize)
                    .map(Integer::parseInt).orElse(0);
        } else { // а если есть реальные покупки по облигации
            /*
             * Считаем сумму покупок (сколько всего купили бумаг то)
             */
            return purchaseList.stream()
                    .map(Purchase::getLot)
                    .reduce(0, Integer::sum);
        }
    }

    @Override
    public ConsolidatedDividendsRs getCoupons(Bond bond, ArNoteUser user) {

        return ConsolidatedDividendsRs.builder()
                .dividendList(prepareCouponList(getBondDataByTicker(bond.getTicker()).orElse(null)))
                .divSum((365 / getBondDataByTicker(bond.getTicker())
                        .map(MoexRowsRs::getCouponPeriod)
                        .map(Double::parseDouble).orElse(1D)) * ((getBondDataByTicker(bond.getTicker())
                        .map(MoexRowsRs::getCouponValue)
                        .map(Double::parseDouble)
                        .map(v -> (int) Math.round(v))
                        .orElse(0))))
                .percent(getBondDataByTicker(bond.getTicker())
                        .map(MoexRowsRs::getCouponPercent)
                        .map(Double::parseDouble)
                        .orElse(0D))
                .build();
    }


    /**
     * Поиск акций по boardId.
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "allshares", key = "#boardId")
    public MoexDocumentRs findSharesByBoardId(String boardId) {
        getAllSharesCount = getAllSharesCount + 1;
        log.info("getAllSharesCount по boardId: {}. Запрос №: {}", boardId, getAllSharesCount);
        MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(RestTemplateOperation.GET_ALL_SHARES, null, boardId);

        return doc;
    }

    /**
     * Выкачать и закэшировать режимы торгов.
     *
     * @return
     */
    @Override
    public List<String> getTradeModes() {

        if (cacheService.getTradeModes() != null && cacheService.getTradeModes().size() > 0) {
            return cacheService.getTradeModes();
        } else {
            MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(
                    RestTemplateOperation.GET_TRADE_MODES, null, null);
            List<String> res = doc.getData().getRow().stream()
                    .filter(r -> "1".equals(r.getIsTraded()))
                    .map(MoexRowsRs::getBoardId)
                    .collect(Collectors.toList());
            cacheService.putTradeModes(res);
            return res;
        }
    }

    /**
     * Подготовить список купонов в формате списка дивидендов.
     *
     * @return
     */
    @Override
    public List<DividendRs> prepareCouponList(MoexRowsRs bondData) {

        List<DividendRs> resultList = new ArrayList<>();

            if (bondData!=null && bondData.getCouponPeriod() != null && isInteger(bondData.getCouponPeriod())){
                int couponsCountPerYear =  Math.toIntExact(365 / Integer.parseInt(bondData.getCouponPeriod()));

                resultList.add(DividendRs.builder()
                        .value(Double.valueOf(bondData.getCouponValue()))
                        .currencyId(Currencies.search(bondData.getCurrencyId()))
                        .registryCloseDate(bondData.getNextCoupon())
                        .build());

                for (int i = 1; i < couponsCountPerYear; i++) {
                    resultList.add(DividendRs.builder()
                            .value(Double.valueOf(bondData.getCouponValue()))
                            .currencyId(Currencies.search(bondData.getCurrencyId()))
                            .registryCloseDate(LocalDate.parse(bondData.getNextCoupon())
                                    .plusDays(Integer.parseInt(bondData.getCouponPeriod())).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .build());
                }
                return resultList;
            } else {
                return null;
            }
    }
}
