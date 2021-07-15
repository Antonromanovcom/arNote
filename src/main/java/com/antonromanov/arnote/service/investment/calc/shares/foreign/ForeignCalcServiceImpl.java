package com.antonromanov.arnote.service.investment.calc.shares.foreign;

import com.antonromanov.arnote.exceptions.MoexRequestException;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.domain.investing.dto.common.Bond;
import com.antonromanov.arnote.domain.investing.dto.common.Purchase;
import com.antonromanov.arnote.domain.investing.dto.cache.enums.CacheDictType;
import com.antonromanov.arnote.domain.investing.dto.external.requests.ForeignRequests;
import com.antonromanov.arnote.domain.investing.dto.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.domain.investing.dto.response.CurrentPriceRs;
import com.antonromanov.arnote.domain.investing.dto.response.DeltaRs;
import com.antonromanov.arnote.domain.investing.dto.response.DividendRs;
import com.antonromanov.arnote.domain.investing.dto.response.enums.Currencies;
import com.antonromanov.arnote.domain.investing.dto.response.enums.TinkoffDeltaFinalValuesType;
import com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.AlphavantageSearchListRs;
import com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.AlphavantageSearchRs;
import com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.alphaadvantage.CompanyOverviewRs;
import com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.yahoo.YahooDivRs;
import com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.yahoo.YahooRealTimeQuoteRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexDataRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import com.antonromanov.arnote.service.investment.cache.CacheService;
import com.antonromanov.arnote.service.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.service.investment.requestservice.RequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.utils.ArNoteUtils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Имплементация расчетного сервиса для работы с иностранными бумагами.
 */
public class ForeignCalcServiceImpl implements SharesCalcService {

    @Autowired
    private RequestService httpClient;

    @Autowired
    private CacheService cacheService;

    private final String FOREIGN_KEY_FOR_CACHE = "|FOREIGN";
    private final String ALFA_ADVANTAGE_API_KEY = "3PV5BRWZZZM1T2BA";

    @Override
    public ConsolidatedDividendsRs getDivsByTicker(String ticker) {
        return null;
    }

    @Override
    public Optional<Double> getCurrentQuoteByTicker(String ticker) {
        return Optional.empty();
    }

    /**
     * Запросить текущую цену акции по тикеру. Та, что обновляется раз в 15 минут с торгов.
     *
     * @return
     */
    @Override
    public CurrentPriceRs getRealTimeQuote(String ticker) {

        if (cacheService.checkDict(CacheDictType.REALTIME_QUOTES, ticker)) {
            return cacheService.getDict(CacheDictType.REALTIME_QUOTES, ticker);
        } else {

            YahooRealTimeQuoteRs response = httpClient.sendAndMarshallForeignRequest(ForeignRequests.GET_REALTIME_QUOTE,
                    new LinkedList<>(Collections.singletonList(ticker)), YahooRealTimeQuoteRs.class);

            /*
             * Все ценники все же переводим в рубли.
             */
            String currency = response.getQuoteSummary().getResult().get(0).getPrice().getCurrency();
            Double currentPrice = getCurrencyMultiplier(currency) *
                    response.getQuoteSummary().getResult().get(0).getPrice().getRegularMarketPrice().getRaw();
            Double priceChange = getCurrencyMultiplier(currency) *
                    response.getQuoteSummary().getResult().get(0).getPrice().getRegularMarketChange().getRaw();
            Double priceChangePrt = getCurrencyMultiplier(currency) *
                    response.getQuoteSummary().getResult().get(0).getPrice().getRegularMarketChangePercent().getRaw();

            LocalDate priceDate = parseStringEpochMilDate(String.valueOf(response
                    .getQuoteSummary()
                    .getResult()
                    .get(0)
                    .getPrice()
                    .getRegularMarketTime()));

            CurrentPriceRs result = CurrentPriceRs.builder()
                    .currentPrice(currentPrice)
                    .currency(Currencies.search(currency))
                    .minLot(1)
                    .ticker(ticker)
                    .date(priceDate)
                    .time(parseEpochMilToTime(response
                            .getQuoteSummary()
                            .getResult()
                            .get(0)
                            .getPrice()
                            .getRegularMarketTime()))
                    .lastChange(priceChange)
                    .lastChangePrcnt(priceChangePrt*100)
                    .build();

            cacheService.putToCache(CacheDictType.REALTIME_QUOTES, ticker, result, CurrentPriceRs.class);
            return result;
        }
    }

    @Override
    public MoexDocumentRs getCurrentQuoteByBoardId(String boardId) {
        return null;
    }

    @Override
    public Optional<MoexDetailInfoRs> getDetailInfo(String ticker) {
        return Optional.empty();
    }

    @Override
    public String getBoardId(String ticker) {
        return null;
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

            CompanyOverviewRs companyInfo = httpClient.sendAndMarshallForeignRequest(ForeignRequests.GET_INSTRUMENT_NAME,
                    new LinkedList<>(Arrays.asList(ticker, "OVERVIEW", ALFA_ADVANTAGE_API_KEY)), CompanyOverviewRs.class);

            cacheService.putToCache(CacheDictType.INSTRUMENT_NAME, ticker, companyInfo.getName(), String.class);
            return companyInfo.getName();
        }
    }

    /**
     * Запросить и посчитать дельту.
     *
     * @param ticker            - тикер.
     * @param currentStockPrice - текущая рыночная ставка (цена)
     * @param purchaseList      - список покупок (цен) пользователя
     * @return
     */
    @Override
    public DeltaRs calculateDelta(String boardId, String ticker, Double currentStockPrice, List<Purchase> purchaseList) {
        if (!isBlank(ticker) && (currentStockPrice != null && currentStockPrice > 0)) {

            MoexDocumentRs doc = getHistory(ticker, null, null);

            /*
             * Как считаем:
             *
             * deltaInRubles = текущая цена - (цена по самой ранней дате)
             * deltaPeriod = Миллисекунды от (текущая дата - (самая ранняя дата истории))
             * tinkoffDelta = (сумма покупок * текущую цену рынка) - (Сумма(лот * цену по каждой покупке))
             */

            return DeltaRs.builder()
                    .tinkoffDelta(getTcsDeltaValues(purchaseList, currentStockPrice)
                            .get(TinkoffDeltaFinalValuesType.DELTA_FINAL))
                    .tinkoffDeltaPercent(getTcsDeltaValues(purchaseList, currentStockPrice)
                            .get(TinkoffDeltaFinalValuesType.DELTA_PERCENT))
                    .deltaInRubles((doc.getData()
                            .getRow()
                            .stream()
                            .min(Comparator.comparing(n -> LocalDate.parse(n.getTradeDate())))
                            .map(dv -> Double.valueOf(dv.getLegalClosePrice()))
                            .map(Math::round)
                            .map(n -> currentStockPrice - n)
                            .orElse(0D)))
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

    @Override
    public Integer calculateFinalPrice(Bond bond, ArNoteUser user) {
        if (bond.getIsBought()) { // если это ФАКТ
            return bond.getPurchaseList().stream()
                    .map(p -> p.getLot() * p.getPrice())
                    .reduce((double) 0, Double::sum).intValue();
        } else { // если ПЛАН
            return (int) Math.round(((getRealTimeQuote(bond.getTicker()
            )).getCurrentPrice()) * getMinimalLot(bond.getTicker(), user));
        }
    }

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    @Override
    public ConsolidatedDividendsRs getDividends(Bond bond, ArNoteUser user) {

        if (cacheService.checkDict(CacheDictType.DIVS_BY_TICKER, bond.getTicker())) {
            return cacheService.getDict(CacheDictType.DIVS_BY_TICKER, bond.getTicker());
        } else {

            LocalDate nowMinus2yYears = LocalDate.now().minusYears(2).withMonth(1).withDayOfMonth(1);
            LocalDate nowDate = LocalDate.now().withMonth(12).withDayOfMonth(31);
            ZoneId zoneId = ZoneId.systemDefault();
            long nowMinus2YearsInEpochMili = nowMinus2yYears.atStartOfDay(zoneId).toEpochSecond();
            long nowDateInEpochMili = nowDate.atStartOfDay(zoneId).toEpochSecond();

            Optional<String> response = httpClient.sendForeignRequest(ForeignRequests.GET_DIVS,
                    new LinkedList<>(Arrays.asList(bond.getTicker(), String.valueOf(nowMinus2YearsInEpochMili),
                            String.valueOf(nowDateInEpochMili), "1mo", "true", "div%7Csplit")));

            Map<String, Object> divList = response.map(r -> {
                JSONObject obj = new JSONObject(response.get()).getJSONObject("chart");
                JSONArray result = obj.getJSONArray("result");
                return result.getJSONObject(0).getJSONObject("events").getJSONObject("dividends").toMap();
            }).orElseThrow(MoexRequestException::new);

            String currencyInfo = response.map(r -> {
                JSONObject obj = new JSONObject(response.get()).getJSONObject("chart");
                JSONArray result = obj.getJSONArray("result");
                return result.getJSONObject(0).getJSONObject("meta").getString("currency");
            }).orElseThrow(MoexRequestException::new);

            Map<LocalDate, YahooDivRs> divListBasedOnLocalDatesAsKey = divList.entrySet().stream()
                    .map(o -> {
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Double> valueMap = mapper.convertValue(o.getValue(), Map.class);
                        return new AbstractMap
                                .SimpleEntry<LocalDate, YahooDivRs>(parseStringEpochMilDate(o.getKey()),
                                YahooDivRs.builder()
                                        .amount(valueMap.get("amount"))
                                        .date(parseStringEpochMilDate(String.valueOf(valueMap.get("date"))))
                                        .build());
                    })
                    .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

            Map<LocalDate, YahooDivRs> mapSorted = new TreeMap<LocalDate, YahooDivRs>(divListBasedOnLocalDatesAsKey);

            ConsolidatedDividendsRs resultDivs = ConsolidatedDividendsRs.builder()
                    .dividendList(mapSorted.entrySet()
                            .parallelStream()
                            .map(e -> DividendRs.builder()
                                    .registryCloseDate(e.getValue().getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                    .currencyId(Currencies.search(currencyInfo))
                                    .value(e.getValue().getAmount() * getCurrencyMultiplier(currencyInfo))
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            resultDivs.calculateSum();
            resultDivs.calculatePercent(getHistory(bond.getTicker(), null, null));
            cacheService.putToCache(CacheDictType.DIVS_BY_TICKER, bond.getTicker(), resultDivs, ConsolidatedDividendsRs.class);
            return resultDivs;
        }
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
            String cur = getRealTimeQuote(ticker).getCurrency() == null ?
                    Currencies.USD.getCode() :
                    getRealTimeQuote(ticker).getCurrency().getCode();
            cacheService.putToCache(CacheDictType.CURRENCY, ticker, cur, String.class);
            return cur;
        }
    }

    @Override
    public Integer getMinimalLot(String ticker, ArNoteUser user) {
        return 1;
    }

    /**
     * Запросить исторические данные.
     *
     * @return
     */
    @Override
    public MoexDocumentRs getHistory(String ticker, String boardId, LocalDate forDate) {

        if (cacheService.checkDict(CacheDictType.HISTORY, ticker + FOREIGN_KEY_FOR_CACHE)) {
            return cacheService.getDict(CacheDictType.HISTORY, ticker + FOREIGN_KEY_FOR_CACHE);
        } else {

            LocalDate earlyDate = LocalDate.now().minusYears(10).withMonth(1).withDayOfMonth(1);
            ZoneId zoneId = ZoneId.systemDefault();
            long earlyDateEpochMili = earlyDate.atStartOfDay(zoneId).toEpochSecond();
            long nowDateInEpochMili = (LocalDate.now()).atStartOfDay(zoneId).toEpochSecond();

            Optional<String> response = httpClient.sendForeignRequest(ForeignRequests.GET_HISTORY,
                    new LinkedList<>(Arrays.asList(ticker, String.valueOf(earlyDateEpochMili),
                            String.valueOf(nowDateInEpochMili), "1d")));

            LinkedHashMap<LocalDate, Double> mapInForeignFormat = response.map(r -> {
                JSONObject obj = new JSONObject(r).getJSONObject("chart");
                JSONArray internalResult = obj.getJSONArray("result");
                JSONArray timestampArray = internalResult.getJSONObject(0).getJSONArray("timestamp");
                JSONArray quotesArray = internalResult
                        .getJSONObject(0)
                        .getJSONObject("indicators")
                        .getJSONArray("quote")
                        .getJSONObject(0)
                        .getJSONArray("close");

                LinkedHashMap<LocalDate, Double> quotesMap = new LinkedHashMap<>();
                for (int i = 0; i < timestampArray.length(); i++) {
                    quotesMap.put(parseStringEpochMilDate(timestampArray.get(i).toString()), quotesArray.getDouble(i));
                }
                return quotesMap;
            }).orElseThrow(MoexRequestException::new);

            MoexDocumentRs doc = new MoexDocumentRs();
            MoexDataRs data = new MoexDataRs();
            Double curMultiplier = getCurrencyMultiplier(getCurrencyOfShare(ticker));
            data.setRow(mapInForeignFormat.entrySet().stream()
                    .map(es -> {
                        MoexRowsRs row = new MoexRowsRs();
                        row.setSecid(ticker);
                        row.setTradeDate(es.getKey().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        row.setLegalClosePrice(String.valueOf(es.getValue() * curMultiplier));
                        row.setCurrencyId("SUR");
                        return row;
                    })
                    .collect(Collectors.toCollection(ArrayList::new)));
            doc.setData(data);
            cacheService.putToCache(CacheDictType.HISTORY, ticker + FOREIGN_KEY_FOR_CACHE, doc, MoexDocumentRs.class);
            return doc;
        }
    }

    @Override
    public Double getCurrencyMultiplier(String currency) {

        if (cacheService.checkDict(CacheDictType.CURRENCY_MULTIPLIER, currency)) {
            return cacheService.getDict(CacheDictType.CURRENCY_MULTIPLIER, currency);
        } else {

            CommonMoexDoc doc = httpClient.sendAndMarshall(MoexRestTemplateOperation.GET_CURRENCY_CHANGE_COURSES, null, null);

            Double curMult = calculateCurrencyMultiplier(doc, currency);
            cacheService.putToCache(CacheDictType.CURRENCY_MULTIPLIER, currency, curMult, Double.class);
            return curMult;
        }
    }

    @Override
    public MoexDocumentRs findSharesByBoardId(String boardId) {
        return null;
    }

    @Override
    public List<String> getTradeModes() {
        return null;
    }

    /**
     * Найти буржуйскую бумагу по ключевому слову. Возвращает только акции.
     *
     * @param keyword
     * @return
     */
    @Override
    public MoexDocumentRs findInstrumentsByName(String keyword) {
        AlphavantageSearchListRs response = httpClient.sendAndMarshallForeignRequest(ForeignRequests.FIND_INSTRUMENT,
                new LinkedList<>(Arrays.asList(keyword, "SYMBOL_SEARCH", ALFA_ADVANTAGE_API_KEY)), AlphavantageSearchListRs.class);

        List<AlphavantageSearchRs> filteredList = response.getBestMatches().stream()
                .filter(Objects::nonNull)
                .filter(sec -> "Equity".equalsIgnoreCase(sec.getType()))
                .collect(Collectors.toList());

        MoexDocumentRs document = new MoexDocumentRs();
        MoexDataRs documentData = new MoexDataRs();
        ArrayList<MoexRowsRs> rows = filteredList.stream()
                .map(r -> {
                    MoexRowsRs row = new MoexRowsRs();
                    row.setSecid(r.getSymbol());
                    row.setCurrencyId(r.getCurrency());
                    row.setSecName(r.getName());
                    return row;
                })
                .filter(row->Currencies.search(row.getCurrencyId())!=null)
                .collect(Collectors.toCollection(ArrayList::new));

        documentData.setRow(rows);
        document.setData(documentData);
        return document;

    }
}
