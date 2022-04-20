package com.antonromanov.arnote.services.investment.calc.shares.moex;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.cache.enums.CacheDictType;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.CurrentPriceRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.enums.TinkoffDeltaFinalValuesType;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexRowsForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDataRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.DataBlock;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexInstrumentDetailRowsRs;
import com.antonromanov.arnote.model.wish.enums.DeltaMode;
import com.antonromanov.arnote.services.investment.cache.CacheService;
import com.antonromanov.arnote.services.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.services.investment.requestservice.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.utils.ArNoteUtils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Slf4j
public class MoexCalculateServiceImpl implements SharesCalcService {

    @Autowired
    private RequestService httpClient;
    @Autowired
    private CacheService cacheService;

    private Long lastQuote = 0L;
    private Long getAllSharesCount = 0L;


    @Override
    public MoexDocumentRs getCandles(String ticker, LocalDate fromDate, LocalDate tillDate) {
        MoexDocumentRs candles = (MoexDocumentRs) httpClient.getCandles(MoexRestTemplateOperation.GET_CANDLES,
                ticker,
                fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                tillDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 1);

        candles.getData().getRow().forEach(v -> v.setSecid(ticker));

        return candles;


    }

    /**
     * Запросить дивиденды через API биржи, подсчитать сумму и проценты относительно текущей цены акции и вернуть все это.
     *
     * @return
     */
    @Override
    public ConsolidatedDividendsRs getDivsByTicker(String ticker) {

        if (cacheService.checkDict(CacheDictType.DIVS_BY_TICKER, ticker)) {
            return cacheService.getDict(CacheDictType.DIVS_BY_TICKER, ticker);
        } else {
            Optional<ConsolidatedDividendsRs> res = httpClient.sendAndParse(ticker);
            if (res.isPresent()) {
                res.get().calculatePercent(getHistory(ticker, getBoardId(ticker), null));
                cacheService.putToCache(CacheDictType.DIVS_BY_TICKER, ticker, res.get(), ConsolidatedDividendsRs.class);
                return res.get();
            } else {
                return null;
            }
        }
    }

    /**
     * Запросить текущую цену (ставку) бумаги.
     *
     * @return
     */
    @Override
    public Optional<Double> getCurrentQuoteByTicker(String ticker) {
        if (!isBlank(ticker)) {
            return Optional.of(getCurrentQuoteByBoardId(getBoardId(ticker)))
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
    public CurrentPriceRs getRealTimeQuote(String ticker) {

        if (LocalTime.now().isBefore(LocalTime.of(10, 30))) {
            return getCurrentQuoteByTicker(ticker)
                    .map(cp -> CurrentPriceRs.builder()
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

            /*
             * В общем решили, что ставки все же стоит кешировать, ибо в противном случае, получаем просто какую-то
             * бомбардировку запросами и все это довольно долго отвечает. Поэтому ставки будем хранить пока час.
             */
            if (cacheService.checkDictWithRetention(CacheDictType.REALTIME_QUOTES_WITH_RETENTION, ticker)) {
                return cacheService.getDictWithRetention(CacheDictType.REALTIME_QUOTES_WITH_RETENTION, ticker);
            } else {


                MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_15_MINUTE_PRICE_UPDATE,
                        ticker, null);

                CurrentPriceRs curPrice = CurrentPriceRs.builder()
                        .ticker(ticker)
                        .currency(Currencies.RUB)
                        .currentPrice(doc.getData().getRow().stream()
                                .filter(r -> getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getLast15MinuteQuote)
                                .map(val -> {
                                    try {
                                        Double.parseDouble(val);
                                        return val;
                                    } catch (Exception e) {
                                        return "0.0";
                                    }
                                })
                                .map(Double::parseDouble).orElse(0D))
                        .date(LocalDate.now())
                        .time(doc.getData().getRow().stream()
                                .filter(r -> getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getUpdateTime)
                                .map(LocalTime::parse).orElse(LocalTime.now()))
                        .lastChange(doc.getData().getRow().stream()
                                .filter(r -> getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getLastChange)
                                .map(Double::parseDouble).orElse(0D))
                        .lastChangePrcnt(doc.getData().getRow().stream()
                                .filter(r -> getBoardId(ticker).equals(r.getTradeMode()))
                                .findFirst()
                                .map(MoexRowsRs::getLastChangePrcnt)
                                .map(Double::parseDouble).orElse(0D))
                        .build();

                if (curPrice.getCurrentPrice() == null || curPrice.getCurrentPrice() == 0) {
                    curPrice.setCurrentPrice(doc.getData().getRow().stream()
                            .filter(r -> getBoardId(ticker).equals(r.getTradeMode()))
                            .findFirst()
                            .map(MoexRowsRs::getLCurrentPrice)
                            .map(val -> {
                                try {
                                    Double.parseDouble(val);
                                    return val;
                                } catch (Exception e) {
                                    return "0.0";
                                }
                            })
                            .map(Double::parseDouble).orElse(0D));
                }

                cacheService.putToCacheWithRetentionTime(CacheDictType.REALTIME_QUOTES_WITH_RETENTION,
                        ticker,
                        curPrice, CurrentPriceRs.class, LocalDateTime.now());

                return curPrice;

            }
        }
    }

    /**
     * Запросить текущую цену бумаги по board_id.
     *
     * @param
     * @return
     */
    @Override
    public MoexDocumentRs getCurrentQuoteByBoardId(String boardId) {

        if (cacheService.checkDict(CacheDictType.LAST_QUOTES_BY_BOARD_ID, boardId)) {
            return cacheService.getDict(CacheDictType.LAST_QUOTES_BY_BOARD_ID, boardId);
        } else {
            lastQuote = lastQuote + 1;
            log.info("Запрос последней ставки по boardId: {}. Запрос №: {}", boardId, lastQuote);
            MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_LAST_QUOTE_MOEX, null, boardId);

            cacheService.putToCache(CacheDictType.LAST_QUOTES_BY_BOARD_ID, boardId, doc, MoexDocumentRs.class);
            return doc;
        }
    }

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     *
     * @return
     */
    @Override
    public Optional<MoexDetailInfoRs> getDetailInfo(String ticker) {
        if (!isBlank(ticker)) {
            return Optional.ofNullable((MoexDetailInfoRs) (httpClient.sendAndMarshall(MoexRestTemplateOperation.
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

        if (cacheService.checkDict(CacheDictType.BOARD_ID_BY_TICKER, ticker)) {
            return cacheService.getDict(CacheDictType.BOARD_ID_BY_TICKER, ticker);
        } else {
            String boardId = ((MoexDocumentForBoardIdRs)
                    (httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_BOARD_ID, ticker, null)))
                    .getData()
                    .getRowList()
                    .stream()
                    .filter(MoexRowsForBoardIdRs::getIsPrimary)
                    .findFirst()
                    .map(MoexRowsForBoardIdRs::getBoardId)
                    .orElse(null);

            cacheService.putToCache(CacheDictType.BOARD_ID_BY_TICKER, ticker, boardId, String.class);
            return boardId;
        }
    }

    /**
     * Запросить имя инструмента.
     *
     * @param ticker - тикер.
     * @return
     */
    @Override
    public String getInstrumentName(String boardId, String ticker) {

        if (cacheService.checkDict(CacheDictType.INSTRUMENT_NAME, ticker)) {
            return cacheService.getDict(CacheDictType.INSTRUMENT_NAME, ticker);
        } else {
            String instrumentName =
                    ((MoexDocumentRs) (httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_INSTRUMENT_NAME, null, boardId)))
                            .getData()
                            .getRow()
                            .stream()
                            .filter(r -> ticker.equals(r.getSecid()))
                            .findFirst()
                            .map(MoexRowsRs::getSecName).orElse("-");

            cacheService.putToCache(CacheDictType.INSTRUMENT_NAME, ticker, instrumentName, String.class);
            return instrumentName;
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
    public DeltaRs calculateDelta(String ticker, Double currentStockPrice, List<Purchase> purchaseList,
                                  DeltaMode deltaMode) { //todo: а в com.antonromanov.arnote.services.investment.calc.shares.foreign.ForeignCalcServiceImpl у этого же метода не аналогичный ли код? Не имеет ли смысла его куда-то вынести?

        String boardId = getBoardId(ticker);
        if (!isBlank(ticker) && !isBlank(boardId) && (currentStockPrice != null && currentStockPrice > 0)) {

            MoexDocumentRs doc = getHistory(ticker, boardId, null);
            MoexDocumentRs candles = getCandles(ticker, LocalDate.now().minusDays(1), LocalDate.now());

            /*
             * Как считаем:
             *
             * deltaInRubles = текущая цена - (цена по самой ранней дате)
             * deltaPeriod = Миллисекунды от (текущая дата - (самая ранняя дата истории))
             * tinkoffDelta = (сумма покупок * текущую цену рынка) - (Сумма(лот * цену по каждой покупке))
             * candleDayDelta = (цена текущая - цена закрытия вчера) * кол-во акций в портфеле
             */

            Double dayDeltaFromCandle = 0.0D;
            Integer instrumentsCount = 0;
            Double dayDeltaFromCandleInPercents = 0.0D;
            Double finalDelta;
            Double finalDeltaInPercents;

            if (purchaseList.size() > 0) {
                dayDeltaFromCandle = getDayDeltaFromCandle(candles);
                instrumentsCount = purchaseList.stream()
                        .map(Purchase::getLot)
                        .reduce((a, b) -> a * b)
                        .orElse(0); // считаем кол-во бумаг в портфеле


                Double getClosePositionForTomorrow = getClosePositionForTomorrow(candles);
                dayDeltaFromCandleInPercents = getClosePositionForTomorrow == 0.0D ? 0.0D :
                        dayDeltaFromCandle / (getClosePositionForTomorrow(candles) * instrumentsCount) * 100; // дневная дельта из свечей в процентах
                log.info("dayDeltaFromCandle for {} = {} / {}%", ticker, dayDeltaFromCandle, dayDeltaFromCandleInPercents);
                log.info("instrumentsCount {} ", instrumentsCount);
            } else {
                log.warn("По бумаге {} нет продаж - не считаем дельту! ", ticker);
            }

            if (DeltaMode.CANDLE_DELTA == deltaMode) {
                finalDelta = dayDeltaFromCandle * instrumentsCount;
                finalDeltaInPercents = dayDeltaFromCandleInPercents;
            } else {
                finalDelta = getTcsDeltaValues(purchaseList, currentStockPrice).get(TinkoffDeltaFinalValuesType.DELTA_FINAL);
                finalDeltaInPercents = getTcsDeltaValues(purchaseList, currentStockPrice).get(TinkoffDeltaFinalValuesType.DELTA_PERCENT);
            }

            return DeltaRs.builder()
                    .tinkoffDelta(finalDelta)
                    .tinkoffDeltaPercent(finalDeltaInPercents)
                    .candleDayDelta(dayDeltaFromCandle * instrumentsCount)
                    .candleDayDeltaPercent(dayDeltaFromCandleInPercents)
                    .deltaInRubles(doc.getData()
                            .getRow()
                            .stream()
                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                            .map(dv -> Double.valueOf(dv.getLegalClosePrice()))
                            .map(Math::round)
                            .map(n -> currentStockPrice - n)
                            .orElse(0D))
                    .candleAllTimeDelta(getAllPeriodDeltaFromCandle(candles, doc, currentStockPrice))
                    .totalPercent(doc.getData()
                            .getRow()
                            .stream()
                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                            .map(dv -> Double.valueOf(dv.getLegalClosePrice()))
                            .map(n -> ((currentStockPrice - n) / n) * 100)
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
     * @return
     */
    @Override
    public Integer calculateFinalPrice(Bond bond) {

        if (bond.getIsBought()) { // если это ФАКТ
            return bond.getPurchaseList().stream()
                    .map(p -> p.getLot() * p.getPrice())
                    .reduce((double) 0, Double::sum).intValue();

        } else { // если ПЛАН
            Double currPrice = getRealTimeQuote(bond.getTicker()).getCurrentPrice();
            Long longFinalPrice = (Math.round((currPrice == null ? Double.NaN : currPrice) * getMinimalLot(bond.getTicker(), bond.getUser())));
            return longFinalPrice.intValue();
        }
    }

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    @Override
    public ConsolidatedDividendsRs getDividends(Bond bond) {
        return Optional.of(getDivsByTicker(bond.getTicker()))
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
    public String getCurrencyOfShare(String ticker) {
        if (cacheService.checkDict(CacheDictType.CURRENCY, ticker)) {
            return cacheService.getDict(CacheDictType.CURRENCY, ticker);
        } else {
            String cur = getDetailInfo(ticker)
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

            cacheService.putToCache(CacheDictType.CURRENCY, ticker, cur, String.class);
            return cur;
        }
    }

    /**
     * Достать минимальный лот.
     *
     * @return
     */
    @Override
    public Integer getMinimalLot(String ticker, ArNoteUser user) {
        String boardId = getBoardId(ticker);
        Integer minLot = getDetailInfo(ticker) //todo: упростить, засунуть в ретурн
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> DataBlock.SECURITIES.getCode().equals(data.getId()))
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> boardId.equals(row.getBoardId()))
                                .findFirst()
                                .map(share -> Integer.parseInt(share.getLotSize()))
                                .orElse(1))
                        .orElse(1))
                .orElse(1);

        return minLot;
    }

    /**
     * Запросить исторические данные по котировкам с биржи.
     *
     * @param ticker  - тикер бумаги.
     * @param boardId - boardId (только для MOEX)
     * @param forDate -на какую  дату запрашиваем.
     * @return - MoexDocumentRs
     */
    @Override
    public MoexDocumentRs getHistory(String ticker, String boardId, LocalDate forDate) {

        if (cacheService.checkDict(CacheDictType.HISTORY, ticker + boardId)) {
            return cacheService.getDict(CacheDictType.HISTORY, ticker + boardId);
        } else {
            int start = 0; // начальная страница
            int step = 100; // шаг перемещения
            boolean isFinalPage = false; // проверочная переменная, определяющая, что дальше циклить не надо и мы достигли конца истории.
            MoexDocumentRs resultDoc = new MoexDocumentRs(); // финальный документ с историей, заполненный всеми страницами

            while (!isFinalPage) {

                LocalDate requestDate;

                if (forDate == null) {
                    requestDate = LocalDate.now().minusYears(10).withMonth(1).withDayOfMonth(1);
                } else {
                    requestDate = forDate;
                }

                String reqDateAsString = requestDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                log.info("Запрашиваем историю на дату: {}", reqDateAsString);
                log.info("Запрашиваем историю. start = {}", start);
                MoexDocumentRs localDoc = (MoexDocumentRs) httpClient.getHistory(MoexRestTemplateOperation.GET_DELTA,
                        ticker, boardId, reqDateAsString, LocalDate.now().toString(), start);
                log.info("Запросили историю. Получили записей: {}", localDoc.getData().getRow().size());

                if (localDoc.getData() == null || localDoc.getData().getRow().size() == 0) {
                    log.warn("Для даты {} страница пустая, идем на следующую!", reqDateAsString);
                    start = start + step;
                } else {
                    isFinalPage = true;
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
            }
            cacheService.putToCache(CacheDictType.HISTORY, ticker + boardId, resultDoc, MoexDocumentRs.class);
            return resultDoc;
        }
    }

    /**
     * Определить валюту и курсовой-множитель для рубля.
     *
     * @param currency - валютный идентификатор
     * @return
     */
    @Override
    public Double getCurrencyMultiplier(String currency) {

        CommonMoexDoc doc = httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_CURRENCY_CHANGE_COURSES,
                null, null);

        return calculateCurrencyMultiplier(doc, currency);
    }

    /**
     * Поиск акций по boardId.
     *
     * @return
     */
    @Override
    public MoexDocumentRs findSharesByBoardId(String boardId) {

        if (cacheService.checkDict(CacheDictType.FIND_SHARES_BY_BOARD_ID, boardId)) {
            return cacheService.getDict(CacheDictType.FIND_SHARES_BY_BOARD_ID, boardId);
        } else {
            getAllSharesCount = getAllSharesCount + 1;
            log.info("getAllSharesCount по boardId: {}. Запрос №: {}", boardId, getAllSharesCount);
            MoexDocumentRs doc = (MoexDocumentRs) httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_ALL_SHARES, null, boardId);
            cacheService.putToCache(CacheDictType.FIND_SHARES_BY_BOARD_ID, boardId, doc, MoexDocumentRs.class);
            return doc;
        }

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
                    MoexRestTemplateOperation.GET_TRADE_MODES, null, null);
            List<String> res = doc.getData().getRow().stream()
                    .filter(r -> "1".equals(r.getIsTraded()))
                    .map(MoexRowsRs::getBoardId)
                    .collect(Collectors.toList());
            cacheService.putTradeModes(res);
            return res;
        }
    }

    /**
     * Найти бумагу по ключевому слову. Возвращает только акции.
     *
     * @param keyword
     * @return
     */
    @Override
    public MoexDocumentRs findInstrumentsByName(String keyword) {
        return null; // не используется для MOEX.
    }
}
