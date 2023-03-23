package com.antonromanov.arnote.domain.investing.service.calc;


import com.antonromanov.arnote.domain.investing.dto.request.AddInstrumentRq;
import com.antonromanov.arnote.domain.investing.dto.response.*;
import com.antonromanov.arnote.domain.investing.dto.response.enums.Currencies;
import com.antonromanov.arnote.domain.investing.dto.response.enums.StockExchange;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.domain.investing.entity.Bond;
import com.antonromanov.arnote.domain.investing.dto.common.BondType;
import com.antonromanov.arnote.domain.investing.entity.Purchase;
import com.antonromanov.arnote.domain.investing.exceptions.BadTickerException;
import com.antonromanov.arnote.domain.investing.repository.BondsRepo;
import com.antonromanov.arnote.domain.investing.repository.PurchasesRepo;
import com.antonromanov.arnote.domain.investing.service.calc.bonds.BondCalcService;
import com.antonromanov.arnote.domain.investing.service.calc.shares.SharesCalcService;
import com.antonromanov.arnote.domain.investing.service.calc.shares.common.CalculateFactory;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.old.utils.ArNoteUtils.*;

/**
 * Сервис обрабатывающий операции, например, выдачи текущей цены бумаги, общие для разных типов (акция / облигация)
 * и работающий как фабрика.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CommonService {

    private final BondCalcService bondCalcService;
    private final CalculateFactory calcFactory;
    private final UserService userService;
    private final BondsRepo bondsRepo;
    private final PurchasesRepo purchasesRepo;


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
    public String getCurrency(Bond bond) {
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
    public ConsolidatedDividendsRs getDivsOrCoupons(Bond bond) {
        ArNoteUser user = userService.getUserFromPrincipal();
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getDividends(bond)) :
                bondCalcService.getCoupons(bond, user);
    }


    /**
     * Получить минимальный лот или кол-во купленных бумаг.
     *
     * @param bond
     * @return
     */
    public Integer getLot(Bond bond) {
        ArNoteUser user = userService.getUserFromPrincipal();
        return bond.getType() == BondType.SHARE ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getMinimalLot(bond.getTicker(), user)) :
                bondCalcService.getBondLot(bond,  bond.getPurchaseList());
    }


    /**
     * Получить финальную цену.
     *
     * @param bond
     * @return
     */
    public Integer getFinalPrice(Bond bond) {

        var curPrice = prepareCurrentPrice(bond);
        var minLot = getLot(bond);
        return calculateFinalPrice(bond, curPrice, minLot);
    }


    /**
     * Получить описание.
     *
     * @param bond
     * @return
     */
    public String getDescription(Bond bond) {

        SharesCalcService service = calcFactory.getCalculator(bond.getStockExchange());

        return bond.getType() == BondType.SHARE ?
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

        DeltaRs localDelta =  bond.getType() == BondType.SHARE ?
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
                .collect(Collectors.toList());


        List<MoexRowsRs> foundBonds = bondCalcService.getBonds().getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

    /*
    * ============= Иностранные акции ====================
    */
        List<MoexRowsRs> foreignShares = (foreignService.findInstrumentsByName(keyword)).getData().getRow();

        SearchResultsRs searchResults = new SearchResultsRs();
        searchResults.setInstruments(prepareInstruments(foundShares, BondType.SHARE, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(foundBonds, BondType.BOND, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(foreignShares, BondType.SHARE, StockExchange.SPB));

        return searchResults;
    }

    /**
     * Получить текущую цену бумаги.
     *
     * @param ticker - тикер бумаги.
     * @return - CurrentPriceRs.
     */
    public CurrentPriceRs getCurrentPriceByTicker(String ticker, StockExchange se) {
        ArNoteUser user = userService.getUserFromPrincipal();
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
     * Добавить инструмент.
     *
     * @param request
     * @return
     */
    public BondRs addInstrument(AddInstrumentRq request) {

        Bond newOrUpdatedBond;
        ArNoteUser user = userService.getUserFromPrincipal();

        /**
         * Проверяем что хотя бы один такой инструмент нашелся, иначе кидаем эксепшн.
         */
        FoundInstrumentRs foundInstrument = findInstrument(request.getTicker())
                .getInstruments().stream()
                .filter(fi -> request.getTicker().equals(fi.getTicker()))
                .findFirst().orElseThrow(() -> new BadTickerException(request.getTicker()));

        log.info("Нашли хотя бы 1 инструмент по тикеру: {}", foundInstrument.getTicker());

        if (!request.getIsPlan() && (request.getLot() != 0 && request.getPrice() != null && request.getPurchaseDate() != null)) {

            Optional<Bond> existingBond = bondsRepo.findBondByUserAndTicker(user, request.getTicker());
            Purchase purchase = new Purchase();
            purchase.setPrice(request.getPrice());
            purchase.setLot(request.getLot());
            purchase.setPurchaseDate(request.getPurchaseDate());

            if (existingBond.isPresent()){
                newOrUpdatedBond = addPurchaseToExistingBond(existingBond.get(), purchase);
            } else{
                newOrUpdatedBond = addNewInstrument(request, purchase, user);
            }


        } else {
            Bond b = new Bond();
            b.setTicker(request.getTicker());
            b.setIsBought(false);
            b.setType(BondType.valueOf(request.getBondType()));
            b.setUser(user);
            b.setStockExchange(getInstrumentStockExchange(request.getTicker()));
            newOrUpdatedBond = bondsRepo.saveAndFlush(b);
        }

        return BondRs.builder()
                .ticker(newOrUpdatedBond.getTicker())
                .isBought(newOrUpdatedBond.getIsBought())
                .type(newOrUpdatedBond.getType().name())
                .stockExchange(newOrUpdatedBond.getStockExchange().name())
                .build();
    }

    private Bond addNewInstrument(AddInstrumentRq request, Purchase purchase, ArNoteUser user) {
        Bond b = new Bond();
        b.setTicker(request.getTicker());
        b.setIsBought(true);
        b.setPurchaseList(Arrays.asList(purchase));
        b.setType(BondType.valueOf(request.getBondType()));
        b.setUser(user);
        b.setStockExchange(getInstrumentStockExchange(request.getTicker()));
        return bondsRepo.saveAndFlush(b);
    }


    /**
     * Добавить продажу к уже имеющемуся в БД инструменту.
     *
     * @param bond
     * @param purchase
     * @return
     */
    private Bond addPurchaseToExistingBond(Bond bond, Purchase purchase) {
        bond.setIsBought(!bond.getIsBought());
        bond.getPurchaseList().add(purchase);
        return bondsRepo.saveAndFlush(bond);
     //   return bond;
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
