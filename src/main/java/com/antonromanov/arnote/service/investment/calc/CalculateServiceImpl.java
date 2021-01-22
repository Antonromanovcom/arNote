package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.exceptions.MoexXmlResponseMappingException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.enums.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexRowsForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.DataBlock;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexInstrumentDetailRowsRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.service.investment.http.client.ArNoteHttpClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CalculateServiceImpl implements CalculateService {

    private final ArNoteHttpClient httpClient;
    private final BondsRepo repo;
    private final List<String> BOARD_GROUP_LIST = Arrays.asList("58", "193", "7", "67", "207");

    public CalculateServiceImpl(ArNoteHttpClient httpClient, BondsRepo repo) {
        this.httpClient = httpClient;
        this.repo = repo;
    }


    /**
     * Запросить дивиденды через API биржи, подсчитать сумму проценты относительно текущей цены акции и вернуть все это.
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    @Cacheable(cacheNames = "divsByTicker", key = "#user.id")
    public Optional<ConsolidatedDividendsRs> getDivsByTicker(LocalUser user, String ticker) {
        if (!isBlank(ticker)) {
            Optional<ConsolidatedDividendsRs> res = httpClient.sendAndParse(ticker);

            if (res.isPresent()) {
                res.get().calculatePercent(repo.findBondByUserAndTicker(user, ticker)
                        .map(Bond::getPrice)
                        .orElse((double) 0));
                return res;
            }
        }
        return Optional.empty();
    }

    /**
     * Запросить текущую цену (ставку) бумаги.
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    public Optional<Double> getCurrentQuoteByTicker(LocalUser user, String ticker) {
        if (!isBlank(ticker)) {

            String boardId = getBoardId(ticker).orElse("TQBR");

            return (getCurrentQuoteByBoardId(boardId))
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

    @Override
    @Cacheable(cacheNames = "lastQuoteCache", key = "#boardId")
    public Optional<MoexDocumentRs> getCurrentQuoteByBoardId(String boardId) {

        return Optional.ofNullable(httpClient.sendAndMarshall(RestTemplateOperation.GET_LAST_QUOTE_MOEX, null, boardId))
                .map(MoexDocumentRs.class::cast);
    }

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    @Cacheable(cacheNames = "detailInfo", key = "#user.id")
    public Optional<MoexDetailInfoRs> getDetailInfo(LocalUser user, String ticker) {
        if (!isBlank(ticker)) {
            return Optional.ofNullable((MoexDetailInfoRs) (httpClient.sendAndMarshall(RestTemplateOperation.
                    GET_INSTRUMENT_DETAIL_INFO, ticker, null)));
        } else {
            return Optional.empty();
        }
    }


    @Override
    @Cacheable(cacheNames = "boardIds", key = "#ticker")
    public Optional<String> getBoardId(String ticker) {
        if (!isBlank(ticker)) {

            return ((MoexDocumentForBoardIdRs)
                    (httpClient.sendAndMarshall(RestTemplateOperation.GET_BOARD_ID, ticker, null)))
                    .getData()
                    .getRowList()
                    .stream()
                    .filter(MoexRowsForBoardIdRs::getIsPrimary)
                    .findFirst()
                    .map(MoexRowsForBoardIdRs::getBoardId);
        } else {
            return Optional.empty();
        }
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

                /**
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
    public Integer calculateFinalPrice(Bond bond, LocalUser user) {
        return (int) Math.round(bond.getLot() != null ?
                ((getCurrentQuoteByTicker(user, bond.getTicker())).orElse((double) 0)) * bond.getLot() :
                (double) 0);
    }

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    @Override
    public ConsolidatedDividendsRs getDividends(Bond bond, LocalUser user) {
        return getDivsByTicker(user, bond.getTicker())
                .orElse(ConsolidatedDividendsRs.builder()
                        .dividendList(Collections.emptyList())
                        .percent(0)
                        .divSum(Double.NaN)
                        .build());
    }

    /**
     * Достать board_id.
     *
     * @param ticker - тикер-бумаги.
     * @return
     */
    @Override
    public String prepareBoardId(String ticker) {
        return getBoardId(ticker).orElse("TQBR");
    }

    /**
     * Подготовить данные по валюте.
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "currenciesCache", key = "#user.id")
    public String getCurrency(Bond bond, LocalUser user) {
        return getDetailInfo(user, bond.getTicker())
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> DataBlock.SECURITIES.getCode().equals(data.getId()))
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> prepareBoardId(bond.getTicker()).equals(row.getBoardId()))
                                .findFirst()
                                .map(MoexInstrumentDetailRowsRs::getCurrencyId)
                                .orElse("-"))
                        .orElse("-"))
                .orElse("-");
    }

    /**
     * Достать минимальный лот.
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "minimalLotsCache", key = "#user.id")
    public Integer getMinimalLot(Bond bond, LocalUser user) {
        return getDetailInfo(user, bond.getTicker())
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> DataBlock.SECURITIES.getCode().equals(data.getId()))
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> prepareBoardId(bond.getTicker()).equals(row.getBoardId()))
                                .findFirst()
                                .map(share -> Integer.parseInt(share.getLotSize()))
                                .orElse(1))
                        .orElse(1))
                .orElse(1);
    }

    @Override
    @Cacheable(cacheNames = "history", key = "#boardId + #ticker")
    public MoexDocumentRs getHistory(String ticker, String boardId) {
        return Optional.ofNullable(httpClient.sendAndMarshall(RestTemplateOperation.GET_DELTA, ticker, boardId))
                .map(MoexDocumentRs.class::cast)
                .orElseThrow(() -> new MoexXmlResponseMappingException("дельту изменения цены"));
    }

    @Override
    public MoexDocumentRs getBondsFromMoexForBoardGroup(String boardGroup) {

        return Optional.ofNullable(httpClient.sendAndMarshall(RestTemplateOperation.GET_BONDS, boardGroup, null))
                .map(MoexDocumentRs.class::cast)
                .orElseThrow(() -> new MoexXmlResponseMappingException("сводные данные по облигации"));
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
}
