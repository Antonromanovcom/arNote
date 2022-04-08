package com.antonromanov.arnote.services.investment.calc;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.*;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.services.investment.calc.bonds.BondCalcService;
import com.antonromanov.arnote.services.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.services.investment.calc.shares.common.CalculateFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.utils.ArNoteUtils.filterByKeyword;
import static com.antonromanov.arnote.utils.ArNoteUtils.prepareInstruments;

/**
 * Сервис обрабатывающий операции, например, выдачи текущей цены бумаги, общие для разных типов (акция / облигация)
 * и работающий как фабрика.
 */
@Service
public class CommonService {

    private final BondCalcService bondCalcService;
    private final CalculateFactory calcFactory;

    public CommonService(CalculateFactory calcFactory, BondCalcService bondCalcService) {
        this.calcFactory = calcFactory;
        this.bondCalcService = bondCalcService;
    }

    /**
     * Посчитать текущую стоимость бумаги.
     *
     * @param bond
     * @return
     */
    public Double prepareCurrentPrice(Bond bond) {
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange()))
                        .getRealTimeQuote(bond.getTicker())
                        .getCurrentPrice()) :
                bondCalcService.getCurrentBondPrice(bond.getTicker());
    }

    /**
     * Получить валюту бумаги.
     *
     * @param bond
     * @return
     */
    public String getCurrency(Bond bond, ArNoteUser user) {
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getCurrencyOfShare(bond.getTicker())) :
                bondCalcService.getBondCurrency(bond.getTicker()).name();
    }

    /**
     * Получить дивы или купоны в формате дивов.
     *
     * @param bond
     * @return
     */
    public ConsolidatedDividendsRs getDivsOrCoupons(Bond bond, ArNoteUser user) {
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getDividends(bond, user)) :
                bondCalcService.getCoupons(bond, user);
    }


    /**
     * Получить минимальный лот или кол-во купленных бумаг.
     *
     * @param bond
     * @return
     */
    public Integer getLot(Bond bond, ArNoteUser user) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getMinimalLot(bond.getTicker(), user)) :
                bondCalcService.getBondLot(bond, user, bond.getPurchaseList());
    }


    /**
     * Получить финальную цену.
     *
     * @param bond
     * @return
     */
    public Integer getFinalPrice(Bond bond, ArNoteUser user) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange())).calculateFinalPrice(bond, user)) :
                bondCalcService.calculateFinalPrice(bond, user);
    }


    /**
     * Получить описание.
     *
     * @param bond
     * @return
     */
    public String getDescription(Bond bond) {

        SharesCalcService service = calcFactory.getCalculator(bond.getStockExchange());

        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                (service.getInstrumentName(service.getBoardId(bond.getTicker()), bond.getTicker())) :
                (bondCalcService.getBondName(bond.getTicker()).orElse("-"));
    }


    /**
     * Посчитать дельту.
     *
     * @param bond
     * @return
     */
    public DeltaRs prepareDelta(Bond bond) {
        SharesCalcService service = calcFactory.getCalculator(bond.getStockExchange());

        DeltaRs localDelta = bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                (service.calculateDelta(service.getBoardId(bond.getTicker()), bond.getTicker(),
                        service.getRealTimeQuote(bond.getTicker()).getCurrentPrice(),
                        bond.getPurchaseList())) :
                DeltaRs.builder()
                        .tinkoffDeltaPercent(0D)
                        .deltaInRubles(0D)
                        .deltaPeriod(0L)
                        .tinkoffDelta(0D)
                        .build();

        return localDelta != null ? localDelta : DeltaRs.builder()
                .tinkoffDeltaPercent(0D)
                .deltaInRubles(0D)
                .deltaPeriod(0L)
                .tinkoffDelta(0D)
                .build();
    }


    /**
     * Найти инструменты по имени / тикеру или их куску.
     *
     * @param keyword - ключ, по которому ищем.
     * @return
     */
    public SearchResultsRs findInstrument(String keyword) {
        SharesCalcService moexService = calcFactory.getCalculator(StockExchange.MOEX);
        SharesCalcService foreignService = calcFactory.getCalculator(StockExchange.SPB);
        Iterator<String> it = moexService.getTradeModes().iterator();
        MoexDocumentRs allShares = new MoexDocumentRs();

        /*
         * ============= Московская биржа: только российские акции и облигации ====================
         */
        while (it.hasNext()) {
            String boardId = it.next();
            MoexDocumentRs halfWayResult = moexService.findSharesByBoardId(boardId);
            halfWayResult.getData().getRow().forEach(q -> q.setBoardId(boardId));
            if (allShares.getData() == null) {
                allShares = halfWayResult;
            } else {
                allShares.getData().getRow().addAll((halfWayResult).getData().getRow());
            }
        }

        List<MoexRowsRs> foundShares = allShares.getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .filter(s->!("TQTF".equals(s.getBoardId())  ||
                        "TQTD".equals(s.getBoardId()) ||
                        "TQTE".equals(s.getBoardId()))) // todo: "TQTF" вынести в какие-то енумы или константы !!!!
                .collect(Collectors.toList());

        List<MoexRowsRs> etf = allShares.getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .filter(s->"TQTF".equals(s.getBoardId())) // todo: "TQTF" вынести в какие-то енумы или константы !!!!
                .collect(Collectors.toList());


        List<MoexRowsRs> foundBonds = bondCalcService.getBonds().getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

        SearchResultsRs searchResults = new SearchResultsRs();
        searchResults.setInstruments(prepareInstruments(foundShares, BondType.SHARE, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(foundBonds, BondType.BOND, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(etf, BondType.INDEX, StockExchange.MOEX));

        /*
         * ============= Иностранные акции ====================
         */
        MoexDocumentRs foreignDocs = foreignService.findInstrumentsByName(keyword);
        if (foreignDocs.getData() != null) { // null - например, если в сервис не смогли достучаться.
            List<MoexRowsRs> foreignShares = (foreignDocs).getData().getRow();
            searchResults.getInstruments().addAll(prepareInstruments(foreignShares, BondType.SHARE, StockExchange.SPB));
        }

        return searchResults;
    }

    /**
     * Получить текущую цену бумаги.
     *
     * @param ticker - тикер бумаги.
     * @param user   - текущий пользак.
     * @return - CurrentPriceRs.
     */
    public CurrentPriceRs getCurrentPriceByTicker(String ticker, StockExchange se, ArNoteUser user) {
        SharesCalcService calculator = calcFactory.getCalculator(se);
        CurrentPriceRs resp = calculator.getRealTimeQuote(ticker);
        resp.setMinLot(calculator.getMinimalLot(ticker, user));
        return resp;
    }

    /**
     * Получить ставку по тикеру и дате.
     *
     * @param
     * @param foundBond
     * @param purchaseDate
     * @return
     */
    public CurrentPriceRs getCurrentPriceByTickerAndDate(FoundInstrumentRs foundBond, String purchaseDate) {

        SharesCalcService calculator = calcFactory.getCalculator(foundBond.getStockExchange());

        if (LocalDate.parse(purchaseDate).isAfter(LocalDate.now())) {
            purchaseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        final String finalPurchaseDate = purchaseDate;
        return calculator.getHistory(foundBond.getTicker(), calculator.getBoardId(foundBond.getTicker()), LocalDate.parse(purchaseDate))
                .getData()
                .getRow()
                .stream()
                .filter(row -> LocalDate.parse(row.getTradeDate()).isEqual(LocalDate.parse(finalPurchaseDate)))
                .findFirst()
                .map(data -> CurrentPriceRs.builder()
                        .currentPrice(Double.valueOf(data.getLegalClosePrice()))
                        .date(LocalDate.parse(finalPurchaseDate))
                        .currency(Currencies.search(data.getCurrencyId()))
                        .ticker(data.getSecid())
                        .build())
                .orElse(calculator.getRealTimeQuote(foundBond.getTicker()));
    }

    /**
     * Достаем Биржу по тикеру.
     *
     * @param ticker
     * @return
     */
    public StockExchange getInstrumentStockExchange(String ticker) {
        return findInstrument(ticker).getInstruments().stream()
                .filter(i -> ticker.equals(i.getTicker()))
                .findFirst()
                .map(FoundInstrumentRs::getStockExchange)
                .orElse(StockExchange.MOEX); //todo: спорный момент. Тут по хорошему надо эксепшн бросать.
    }
}
