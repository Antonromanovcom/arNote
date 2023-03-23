package com.antonromanov.arnote.old.services.investment.calc;

/**
 * Сервис обрабатывающий операции, например, выдачи текущей цены бумаги, общие для разных типов (акция / облигация)
 * и работающий как фабрика.
 */
// @Service
public class CommonService {

 /*   private final BondCalcService bondCalcService;
    private final CalculateFactory calcFactory;
    private final CalendarRepo calendarRepo;

    public CommonService(CalculateFactory calcFactory, BondCalcService bondCalcService, CalendarRepo calendarRepo) {
        this.calcFactory = calcFactory;
        this.bondCalcService = bondCalcService;
        this.calendarRepo = calendarRepo;
    }*/

    /**
     * Посчитать текущую стоимость бумаги.
     *
     * @param bond
     * @return
     */
   /* public Double prepareCurrentPrice(Bond bond) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange()))
                        .getRealTimeQuote(bond.getTicker())
                        .getCurrentPrice()) :
                bondCalcService.getCurrentBondPrice(bond.getTicker());
    }
*/
    /**
     * Получить свечи.
     *
     * @return
     */
    /*public MoexDocumentRs getCandles() {
        return ((calcFactory.getCalculator(StockExchange.MOEX)).getCandles("SBER", LocalDate.now(), LocalDate.now()));
    }*/

    /**
     * Получить валюту бумаги.
     *
     * @param bond
     * @return
     */
    /*public String getCurrency(Bond bond) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getCurrencyOfShare(bond.getTicker())) :
                bondCalcService.getBondCurrency(bond.getTicker()).name();
    }*/

    /**
     * Получить дивы или купоны в формате дивов.
     *
     * @param bond
     * @return
     */
   /* public ConsolidatedDividendsRs getDivsOrCoupons(Bond bond) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getDividends(bond)) :
                bondCalcService.getCoupons(bond);
    }*/


    /**
     * Получить минимальный лот или кол-во купленных бумаг.
     *
     * @param bond
     * @return
     */
   /* public Integer getLot(Bond bond) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange())).getMinimalLot(bond.getTicker(), bond.getUser())) :
                bondCalcService.getBondLot(bond);
    }
*/

    /**
     * Получить финальную цену.
     *
     * @param bond
     * @return
     */
    /*public Integer getFinalPrice(Bond bond) {
        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                ((calcFactory.getCalculator(bond.getStockExchange())).calculateFinalPrice(bond)) :
                bondCalcService.calculateFinalPrice(bond);
    }*/


    /**
     * Получить описание.
     *
     * @param bond
     * @return
     */
   /* public String getDescription(Bond bond) {

        SharesCalcService service = calcFactory.getCalculator(bond.getStockExchange());

        return bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                (service.getInstrumentName(service.getBoardId(bond.getTicker()), bond.getTicker())) :
                (bondCalcService.getBondName(bond.getTicker()).orElse("-"));
    }*/


    /**
     * Посчитать дельту.
     *
     * @param bond
     * @return
     */
   /* public DeltaRs prepareDelta(Bond bond) {
        SharesCalcService service = calcFactory.getCalculator(bond.getStockExchange());
        DeltaMode deltaMode = bond.getUser().getDeltaMode() == null ? DeltaMode.TINKOFF_DELTA : bond.getUser().getDeltaMode();

        DeltaRs localDelta = bond.getType() == BondType.SHARE || bond.getType() == BondType.INDEX ?
                (service.calculateDelta(bond.getTicker(), service.getRealTimeQuote(bond.getTicker()).getCurrentPrice(),
                        bond.getPurchaseList(), deltaMode)) :
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
    }*/


    /**
     * Найти инструменты по имени / тикеру или их куску.
     *
     * @param keyword - ключ, по которому ищем.
     * @return
     */
  /*  public SearchResultsRs findInstrument(String keyword) {
        SharesCalcService moexService = calcFactory.getCalculator(StockExchange.MOEX);
        SharesCalcService foreignService = calcFactory.getCalculator(StockExchange.SPB);
        Iterator<String> it = moexService.getTradeModes().iterator();
        MoexDocumentRs allShares = new MoexDocumentRs();

        *//*
         * ============= Московская биржа: только российские акции и облигации ====================
         *//*
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
                .filter(s -> !("TQTF".equals(s.getBoardId()) ||
                        "TQTD".equals(s.getBoardId()) ||
                        "TQTE".equals(s.getBoardId()))) // todo: "TQTF" вынести в какие-то енумы или константы !!!!
                .collect(Collectors.toList());

        List<MoexRowsRs> etf = allShares.getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .filter(s -> "TQTF".equals(s.getBoardId())) // todo: "TQTF" вынести в какие-то енумы или константы !!!!
                .collect(Collectors.toList());


        List<MoexRowsRs> foundBonds = bondCalcService.getBonds().getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

        SearchResultsRs searchResults = new SearchResultsRs();
        searchResults.setInstruments(prepareInstruments(foundShares, BondType.SHARE, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(foundBonds, BondType.BOND, StockExchange.MOEX));
        searchResults.getInstruments().addAll(prepareInstruments(etf, BondType.INDEX, StockExchange.MOEX));

        *//*
         * ============= Иностранные акции ====================
         *//*
        MoexDocumentRs foreignDocs = foreignService.findInstrumentsByName(keyword);
        if (foreignDocs.getData() != null) { // null - например, если в сервис не смогли достучаться.
            List<MoexRowsRs> foreignShares = (foreignDocs).getData().getRow();
            searchResults.getInstruments().addAll(prepareInstruments(foreignShares, BondType.SHARE, StockExchange.SPB));
        }

        return searchResults;
    }*/

    /**
     * Получить текущую цену бумаги.
     *
     * @param ticker - тикер бумаги.
     * @param user   - текущий пользак.
     * @return - CurrentPriceRs.
     */
   /* public CurrentPriceRs getCurrentPriceByTicker(String ticker, StockExchange se, ArNoteUser user) {
        SharesCalcService calculator = calcFactory.getCalculator(se);
        CurrentPriceRs resp = calculator.getRealTimeQuote(ticker);
        resp.setMinLot(calculator.getMinimalLot(ticker, user));
        return resp;
    }
*/
  /*  private Boolean checkDate(List<CalendarEntity> calendarEntityList, LocalDate date) { // todo: почему это вообще в этом классе?

        CalendarType type = calendarEntityList.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getDate().toLocalDate().isEqual(date))
                .findFirst()
                .map(CalendarEntity::getType)
                .orElse(CalendarType.WORK);

        return type != CalendarType.WEEKEND;
    }


    public Boolean checkDateIsWorkDay(LocalDate purchaseDate) {

        List<CalendarEntity> calendarEntityList = calendarRepo.findAll();
        List<CalendarEntity> calendarEntityListByYear = calendarEntityList.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getDate().toLocalDate().getYear() == purchaseDate.getYear())
                .collect(Collectors.toList());

        if (calendarEntityListByYear.size() > 1) {
            return checkDate(calendarEntityList, purchaseDate);
        } else {

            Optional<Calendar> cal = getWorkCalendar(purchaseDate.getYear());

            if (cal.isPresent() && cal.get().getDays() != null && cal.get().getDays().size() > 0) {

                List<CalendarEntity> calendarEntities = cal.get().getDays().stream()
                        .map(v -> CalendarEntity.builder()
                                .date(getSqlDateFromXmlCalendar(cal.get().getYear(), v.getDate()))
                                .type(CalendarType.searchByIdType(v.getType()))
                                .build())
                        .collect(Collectors.toList());
                calendarRepo.saveAll(calendarEntities);
                return checkDate(calendarEntityList, purchaseDate);
            } else { // нет записей в БД и не нашли записей в производственном календаре
                return false;
            }
        }
    }
*/

    /**
     * Получить ставку по тикеру и дате.
     *
     * @param
     * @param foundBond
     * @param purchaseDate
     * @return
     */
  /*  public CurrentPriceRs getCurrentPriceByTickerAndDate(FoundInstrumentRs foundBond, String purchaseDate) throws NoTradesForUserDateException {

        SharesCalcService calculator = calcFactory.getCalculator(foundBond.getStockExchange());

        if (LocalDate.parse(purchaseDate).isAfter(LocalDate.now())) {
            purchaseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        final String finalPurchaseDate = purchaseDate;

        if (!checkDateIsWorkDay(LocalDate.parse(purchaseDate))) {
            throw new NoTradesForUserDateException(ErrorCodes.ERR_O7);
        } else {
            MoexDocumentRs history = calculator.getHistory(foundBond.getTicker(), calculator.getBoardId(foundBond.getTicker()), LocalDate.parse(purchaseDate));
            if (history == null || history.getData() == null || history.getData().getRow().size() < 1) {
                throw new NoTradesForUserDateException(ErrorCodes.ERR_O8);
            } else {
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
        }
    }
*/

}
