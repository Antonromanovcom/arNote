package com.antonromanov.arnote.domain.investing.api;

import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.domain.investing.service.consolidated.ConsolidatedDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * API для управления инвестициями.
 */
@CrossOrigin()
@RestController
@RequestMapping("/investing")
@AllArgsConstructor
public class InvestController {

    /*private final UsersRepo usersRepo;
    private final BondsRepo bondsRepo;
    private final CalendarService calendarService;
    private final ReturnsService returnsService;
    private final CommonService commonService;*/
    private final ConsolidatedDataService service;


    /**
     * Консолидированные данные по бумагам.
     *
     * @return ConsolidatedInvestmentDataRs.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/consolidated")
    public ConsolidatedInvestmentDataRs consolidatedBondsInfo(@RequestParam(required = false) String filter,
                                                              @RequestParam(required = false) String sort) {
        return service.getConsolidatedData(filter, sort);
    }

    /**
     * Консолидированные данные по доходности.
     *
     * @param principal - пользователь.
     * @return - ConsolidatedReturnsRs
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/returns")
    public ConsolidatedReturnsRs returnsConsolidated(Principal principal) throws UserNotFoundException {

        log.info("============== CONSOLIDATED RETURNS TABLE ============== ");
        log.info("PRINCIPAL: " + principal.getName());

        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);

        return ConsolidatedReturnsRs.builder()
                .invested(returnsService.getTotalInvestment(user).orElse(0L))
                .bondsReturns(returnsService.getTotalBondsReturns(user).orElse(0L))
                .sharesDelta(returnsService.getSharesDelta(user).map(Double::longValue).orElse(0L))
                .sharesReturns(returnsService.getTotalDivsReturn(user).orElse(0L))
                .sum((returnsService.calculateTotalReturns(user)))
                .targets(Stream.of(new Object[][]{
                        {Targets.ONE_THOUSAND_ROUBLES, returnsService.calculateRequiredInvestments(user, Targets.ONE_THOUSAND_ROUBLES)},
                        {Targets.FIVE_THOUSANDS_ROUBLES, returnsService.calculateRequiredInvestments(user, Targets.FIVE_THOUSANDS_ROUBLES)},
                        {Targets.TEN_THOUSANDS_ROUBLES, returnsService.calculateRequiredInvestments(user, Targets.TEN_THOUSANDS_ROUBLES)},
                        {Targets.THIRTY_THOUSANDS_ROUBLES, returnsService.calculateRequiredInvestments(user, Targets.THIRTY_THOUSANDS_ROUBLES)},
                        {Targets.SIXTY_THOUSANDS_ROUBLES, returnsService.calculateRequiredInvestments(user, Targets.SIXTY_THOUSANDS_ROUBLES)},
                }).collect(Collectors.toMap(data -> (Targets) data[0], data -> (Long) data[1])))
                .build();
    }*/

    /**
     * Тестовый контроллер для отладки дивов. А то какие-то странные цифры стали приходить.
     *
     * @param principal - пользователь.
     * @return - ConsolidatedReturnsRs
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/divs")
    public DivsDetailsRs getDivsDetails(Principal principal) throws UserNotFoundException {

        log.info("============== DIVS DETAILS ============== ");
        log.info("PRINCIPAL: " + principal.getName());

        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        List<DivsDebug> res = returnsService.getDivsDebug(user);


        return DivsDetailsRs.builder()
                .divs(res)
                .sum(res.stream()
                        .map(DivsDebug::getDivs)
                        .map(t -> t.stream()
                                .map(DividendRs::getValue)
                                .reduce((double) 0, Double::sum))
                        .reduce((double) 0, Double::sum))
                .build();
    }*/

    /**
     * Найти инструменты по имени / тикеру или их куску.
     *
     * @param principal - пользак
     * @param keyword   - искомое слово или часть его
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/search")
    public SearchResultsRs findInstrumentByName(Principal principal, @RequestParam @NotNull String keyword) throws UserNotFoundException {
        log.info("============== FIND INSTRUMENT ============== ");
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("keyword: " + keyword);
        return commonService.findInstrument(keyword);
    }*/

    /**
     * Получить текущую цену по тикеру
     *
     * @param principal - пользак
     * @param ticker    - тикер.
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping("/price")
    public CurrentPriceRs getCurrentPriceByTickerAndStockExchange(Principal principal,
                                                                  @RequestParam @NotNull String ticker,
                                                                  @RequestParam @NotNull String stockExchange)
            throws UserNotFoundException {

        log.info("============== GET CURRENT PRICE BY TICKER ============== ");
        ArNoteUser user = getLocalUserFromPrincipal(principal);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + ticker);


        return commonService.getCurrentPriceByTicker(ticker, StockExchange.valueOf(stockExchange), user);
    }*/

    /**
     * Получить текущую цену по тикеру на конкретную дату
     *
     * @param principal    - пользак
     * @param ticker       - тикер.
     * @param purchaseDate - дата покупки.
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/price-by-date")
    public CurrentPriceRs getCurrentPriceByTicker(Principal principal,
                                                  @RequestParam @NotNull String ticker,
                                                  @RequestParam @NotNull String purchaseDate)
            throws UserNotFoundException {

        log.info("============== GET CURRENT PRICE BY TICKER AND PURCHASE DATE ============== ");
        ArNoteUser user = getLocalUserFromPrincipal(principal);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + ticker);
        log.info("purchase date: " + purchaseDate);

        FoundInstrumentRs foundBond = commonService.findInstrument(ticker).getInstruments().stream()
                .filter(fi -> ticker.equals(fi.getTicker()))
                .findFirst().orElseThrow(() -> new BadTickerException(ticker));


        return commonService.getCurrentPriceByTickerAndDate(foundBond, purchaseDate);

    }*/

    /**
     * Удалить бумагу
     *
     * @param principal - пользак
     * @param ticker    - тикер удаляемой бумаги
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @DeleteMapping()
    public ConsolidatedInvestmentDataRs deleteInstrument(Principal principal, @RequestParam @NotNull String ticker) throws UserNotFoundException {

        log.info("============== DELETE PAPER ============== ");
        ArNoteUser user = getLocalUserFromPrincipal(principal);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + ticker);

        Optional<Bond> bond = bondsRepo.findBondByUserAndTicker(user, ticker);
        bond.ifPresent(bondsRepo::delete);

        return ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(b -> prepareBondRs(b, user))
                        .collect(Collectors.toList()))
                .build();
    }*/

  /*  @CrossOrigin(origins = "*")
    @GetMapping("/calendar")
    public CalendarRs getCalendar(Principal principal) throws UserNotFoundException {
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        return calendarService.getCalendar(ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(bond -> prepareBondRs(bond, user))
                        .collect(Collectors.toList()))
                .build());
    }*/

    /**
     * Добавить бумагу (с покупкой или в качестве плана).
     *
     * @param principal - пользак
     * @param request   - реквест, содержащий даты и тикер.
     * @return
     */
 /*   @CrossOrigin(origins = "*")
    @PostMapping()
    public BondRs addInstrument(Principal principal, @RequestBody AddInstrumentRq request) throws UserNotFoundException {

        log.info("============== ADD INSTRUMENT ============== ");
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + request.getTicker());
        Bond newOrUpdatedBond;*/

    /**
     * Проверяем что хотя бы один такой инструмент нашелся, иначе кидаем эксепшн.
     */
      /*  FoundInstrumentRs foundInstrument = commonService.findInstrument(request.getTicker())
                .getInstruments().stream()
                .filter(fi -> request.getTicker().equals(fi.getTicker()))
                .findFirst().orElseThrow(() -> new BadTickerException(request.getTicker()));

        log.info("Нашли хотя бы 1 инструмент по тикеру: {}", foundInstrument.getTicker());



        if (!request.isPlan() && (request.getLot() != 0 && request.getPrice() != null && request.getPurchaseDate() != null)) {

            Optional<Bond> existingBond = bondsRepo.findBondByUserAndTicker(user, request.getTicker());
            Purchase purchase = new Purchase();
            purchase.setPrice(request.getPrice());
            purchase.setLot(request.getLot());
            purchase.setPurchaseDate(request.getPurchaseDate());

            if (existingBond.isPresent()) {
                if (!existingBond.get().getIsBought()) {
                    existingBond.get().setIsBought(true);
                }
                existingBond.get().getPurchaseList().add(purchase);
                newOrUpdatedBond = bondsRepo.saveAndFlush(existingBond.get());
            } else {
                Bond b = new Bond();
                b.setTicker(request.getTicker());
                b.setIsBought(true);
                b.setPurchaseList(Collections.singletonList(purchase));
                b.setType(BondType.valueOf(request.getBondType()));
                b.setUser(user);
                b.setStockExchange(commonService.getInstrumentStockExchange(request.getTicker()));
                newOrUpdatedBond = bondsRepo.saveAndFlush(b);
            }
        } else {
            Bond b = new Bond();
            b.setTicker(request.getTicker());
            b.setIsBought(false);
            b.setType(BondType.valueOf(request.getBondType()));
            b.setUser(user);
            b.setStockExchange(commonService.getInstrumentStockExchange(request.getTicker()));
            newOrUpdatedBond = bondsRepo.saveAndFlush(b);
        }

        return BondRs.builder()
                .ticker(newOrUpdatedBond.getTicker())
                .isBought(newOrUpdatedBond.getIsBought())
                .type(newOrUpdatedBond.getType().name())
                .stockExchange(newOrUpdatedBond.getStockExchange().name())
                .build();
    }*/

    /**
     * Подготовить респонс бумаги.
     *
     * @param bond - данные по бумаге из БД.
     * @return
     */
    /*private BondRs prepareBondRs(Bond bond, ArNoteUser user) {

        return BondRs.builder()
                .id(bond.getId())
                .ticker(bond.getTicker())
                .type(bond.getType().name())
                .isBought(bond.getIsBought())
                .stockExchange(bond.getStockExchange().name())
                .currentPrice(commonService.prepareCurrentPrice(bond))
                .currency(commonService.getCurrency(bond, user))
                .dividends(commonService.getDivsOrCoupons(bond, user))
                .minLot(commonService.getLot(bond, user))
                .finalPrice(commonService.getFinalPrice(bond, user))
                .delta(commonService.prepareDelta(bond))
                .description(commonService.getDescription(bond))
                .build();
    }*/

    /**
     * Достать юзера из Принципала
     *
     * @return LocalUser
     */
  /*  public ArNoteUser getLocalUserFromPrincipal(Principal principal) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
    }*/
}
