package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.CurrentPriceRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.SearchResultsRs;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.service.investment.calc.bonds.BondCalcService;
import com.antonromanov.arnote.service.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.service.investment.calc.shares.common.CalculateFactory;
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
     * @param bond
     * @return
     */
   public Double prepareCurrentPrice(Bond bond){
           return bond.getType() == BondType.SHARE ?
                   ((calcFactory.getCalculator(bond.getStockExchange()))
                           .getRealTimeQuote(bond.getTicker())
                           .getCurrentPrice()) :
                   bondCalcService.getCurrentBondPrice(bond.getTicker());
    }

    /**
     * Получить валюту бумаги.
     * @param bond
     * @return
     */
    public String getCurrency(Bond bond, ArNoteUser user){
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getCurrencyOfShare(bond.getTicker(), user)) :
                bondCalcService.getBondCurrency(bond.getTicker()).name();
    }

    /**
     * Получить дивы или купоны в формате дивов.
     * @param bond
     * @return
     */
    public ConsolidatedDividendsRs getDivsOrCoupons(Bond bond, ArNoteUser user){
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getDividends(bond, user)) :
                 bondCalcService.getCoupons(bond, user);
    }


    /**
     * Получить минимальный лот или кол-во купленных бумаг.
     * @param bond
     * @return
     */
    public Integer getLot(Bond bond, ArNoteUser user){
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getMinimalLot(bond.getTicker(), user)) :
                bondCalcService.getBondLot(bond, user, bond.getPurchaseList());
    }


    /**
     * Получить финальную цену.
     * @param bond
     * @return
     */
    public Integer getFinalPrice(Bond bond, ArNoteUser user){
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).calculateFinalPrice(bond, user)) :
                bondCalcService.calculateFinalPrice(bond, user);
    }


    /**
     * Получить описание.
     * @param bond
     * @return
     */
    public String getDescription(Bond bond){

        SharesCalcService service = calcFactory.getCalculator(bond.getStockExchange());

        return bond.getType() == BondType.SHARE ?
                (service.getInstrumentName(service.getBoardId(bond.getTicker()),
                        bond.getTicker()).orElse("-")) :
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
        return bond.getType() == BondType.SHARE ?
                (service.calculateDelta(service.getBoardId(bond.getTicker()), bond.getTicker(),
                        service.getRealTimeQuote(bond.getTicker()).getCurrentPrice(),
                        bond.getPurchaseList())) :
                DeltaRs.builder()
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
                .collect(Collectors.toList());


        List<MoexRowsRs> foundBonds = bondCalcService.getBondsFromMoex().getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

        /*
         * ============= Иностранные акции ====================
         */
        List<MoexRowsRs> foreignShares = (foreignService.getForeignInstrumentsByName(keyword)).getData().getRow();

        SearchResultsRs searchResults = new SearchResultsRs();
        searchResults.setInstruments(prepareInstruments(foundShares, BondType.SHARE, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(foundBonds, BondType.BOND, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(foreignShares, BondType.SHARE, StockExchange.SPB));

        return searchResults;
    }

    /**
     * Получить текущую цену бумаги.
     *
     * @param bond
     * @param user
     * @return
     */
    public CurrentPriceRs getCurrentPriceByTicker(Bond bond, ArNoteUser user) {
        SharesCalcService calculator = calcFactory.getCalculator(bond.getStockExchange());
        CurrentPriceRs resp = calculator.getRealTimeQuote(bond.getTicker());
        resp.setMinLot(calculator.getMinimalLot(bond.getTicker(), user));
        return resp;
    }

    /**
     * Получить ставку по тикеру и дате.
     * @param
     * @param purchaseDate
     * @return
     */
    public CurrentPriceRs getCurrentPriceByTickerAndDate(Bond bond, String purchaseDate) {

        SharesCalcService calculator = calcFactory.getCalculator(bond.getStockExchange());

        if (LocalDate.parse(purchaseDate).isAfter(LocalDate.now())) {
            purchaseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        final String finalPurchaseDate = purchaseDate;
        return calculator.getHistory(bond.getTicker(), calculator.getBoardId(bond.getTicker()))
                .getData()
                .getRow()
                .stream()
                .filter(row -> LocalDate.parse(row.getTradeDate()).isEqual(LocalDate.parse(finalPurchaseDate)))
                .findFirst()
                .map(data -> CurrentPriceRs.builder()
                        .currentPrice(Double.valueOf(data.getLegalClosePrice()))
                        .date(LocalDate.parse(finalPurchaseDate))
                        .build())
                .orElse(calculator.getRealTimeQuote(bond.getTicker()));
    }
}
