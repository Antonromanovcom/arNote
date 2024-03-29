package com.antonromanov.arnote.controller;


import com.antonromanov.arnote.exceptions.BadTickerException;
import com.antonromanov.arnote.exceptions.NoTradesForUserDateException;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.*;
import com.antonromanov.arnote.model.investing.request.AddInstrumentRq;
import com.antonromanov.arnote.model.investing.request.DeltaToggleRq;
import com.antonromanov.arnote.model.investing.response.*;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.wish.enums.DeltaMode;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.CalendarRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.services.investment.calc.CommonService;
import com.antonromanov.arnote.services.investment.calendar.CalendarService;
import com.antonromanov.arnote.services.investment.returns.ReturnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.antonromanov.arnote.utils.ArNoteUtils.*;


/**
 * API для управления инвестициями.
 */
@CrossOrigin()
@RestController
@RequestMapping("/investing")
@Slf4j
public class InvestController {

    private final UsersRepo usersRepo;
    private final BondsRepo bondsRepo;
    private final CalendarService calendarService;
    private final ReturnsService returnsService;
    private final CommonService commonService;
    private final CalendarRepo calendarRepo;


    public InvestController(UsersRepo usersRepo, BondsRepo bondsRepo, ReturnsService returnsService,
                            CalendarService calendarService, CommonService commonService, CalendarRepo calendarRepo) {
        this.usersRepo = usersRepo;
        this.bondsRepo = bondsRepo;
        this.returnsService = returnsService;
        this.calendarService = calendarService;
        this.commonService = commonService;
        this.calendarRepo = calendarRepo;
    }

    /**
     * Консолидированные данные по бумагам.
     *
     * @param principal - пользователь.
     * @return ConsolidatedInvestmentDataRs.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/consolidated")
    public ConsolidatedInvestmentDataRs consolidatedBondsInfo(Principal principal,
                                                              @RequestParam(required = false) String filter,
                                                              @RequestParam(required = false) String sort)
            throws UserNotFoundException {

        log.info("============== CONSOLIDATED INVESTMENT TABLE ============== ");
        log.info("PRINCIPAL: " + principal.getName());
        log.info("FILTER: " + filter);
        log.info("SORT: " + sort);

        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);

        /*
         * Логика такая:
         *
         * - если фильтр приходит не пустой - задаем и сохраняем новый фильтр.
         * - если фильтр приходит не пустой, но он NONE, просто удаляем мапу с фильтрами из записи пользака.
         * - если filter пришел пустой - выдаем то, что есть с той фильтрацией, что сохранена.
         *
         */
        if (filter != null) {
            if (InvestingFilterMode.valueOf(filter) == InvestingFilterMode.NONE) {
                user.setInvestingFilterMode(null);
            } else {
                if (user.getInvestingFilterMode() != null && user.getInvestingFilterMode().size() > 0) {
                    user.getInvestingFilterMode().put(InvestingFilterMode.valueOf(filter).getKey(), filter);
                } else {
                    Map<String, String> filterMap = new HashMap<>();
                    filterMap.put(InvestingFilterMode.valueOf(filter).getKey(), filter);
                    user.setInvestingFilterMode(filterMap);
                }
            }
            user = usersRepo.saveAndFlush(user);
        }

        if (sort != null) {
            if (InvestingSortMode.valueOf(sort) == InvestingSortMode.NONE) {
                user.setInvestingSortMode(null);
            } else {
                user.setInvestingSortMode(InvestingSortMode.valueOf(sort));
            }
            user = usersRepo.saveAndFlush(user);
        }


        ArNoteUser finalUser = user;
        log.info("USER FILTER MAP: " + user.getInvestingFilterMode());
        log.info("USER SORT MODE: " + user.getInvestingSortMode());

        return ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(bond -> prepareBondRs(bond))
                        .filter(user.getInvestingFilterMode() != null ? complexPredicate(user.getInvestingFilterMode()) :
                                s -> s.getTicker() != null)
                        .sorted(user.getInvestingSortMode() == null ? InvestingSortMode.NONE.getComparator() :
                                user.getInvestingSortMode().getComparator())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Свечи. Временный контроллер
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/candles")
    public MoexDocumentRs candles(Principal principal,
                                  @RequestParam(required = false) String filter,
                                  @RequestParam(required = false) String sort)
            throws UserNotFoundException { // todo: удалить потом этот контроллер

        log.info("============== CANDLES ============== ");
        log.info("PRINCIPAL: " + principal.getName());

        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);


        return commonService.getCandles();
    }

    /**
     * Консолидированные данные по доходности.
     *
     * @param principal - пользователь.
     * @return - ConsolidatedReturnsRs
     */
    @CrossOrigin(origins = "*")
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
    }

    /**
     * Тестовый контроллер для отладки дивов. А то какие-то странные цифры стали приходить.
     *
     * @param principal - пользователь.
     * @return - ConsolidatedReturnsRs
     */
    @CrossOrigin(origins = "*")
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
    }

    /**
     * Найти инструменты по имени / тикеру или их куску.
     *
     * @param principal - пользак
     * @param keyword   - искомое слово или часть его
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/search")
    public SearchResultsRs findInstrumentByName(Principal principal, @RequestParam @NotNull String keyword) throws UserNotFoundException {
        log.info("============== FIND INSTRUMENT ============== ");
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("keyword: " + keyword);
        return commonService.findInstrument(keyword);
    }

    /**
     * Получить текущую цену по тикеру
     *
     * @param principal - пользак
     * @param ticker    - тикер.
     * @return
     */
    @CrossOrigin(origins = "*")
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
    }

    /**
     * Получить текущую цену по тикеру на конкретную дату
     *
     * @param principal    - пользак
     * @param ticker       - тикер.
     * @param purchaseDate - дата покупки.
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/price-by-date")
    public CurrentPriceWithStatusRs getCurrentPriceByTickerAndDate(Principal principal,
                                                                   @RequestParam @NotNull String ticker,
                                                                   @RequestParam @NotNull String purchaseDate) throws UserNotFoundException {

        log.info("============== GET CURRENT PRICE BY TICKER AND PURCHASE DATE ============== ");
        ArNoteUser user = getLocalUserFromPrincipal(principal);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + ticker);
        log.info("purchase date: " + purchaseDate);

        FoundInstrumentRs foundBond = commonService.findInstrument(ticker).getInstruments().stream()
                .filter(fi -> ticker.equals(fi.getTicker()))
                .findFirst().orElseThrow(() -> new BadTickerException(ticker));

        try {
            CurrentPriceRs temp = commonService.getCurrentPriceByTickerAndDate(foundBond, purchaseDate);
            return CurrentPriceWithStatusRs.builder()
                    .currentPrice(temp.getCurrentPrice())
                    .currency(temp.getCurrency())
                    .date(temp.getDate())
                    .ticker(temp.getTicker())
                    .status("OK") // todo: заменить на константу или енум
                    .build();
        } catch (NoTradesForUserDateException e) { // todo: переделать!
            return CurrentPriceWithStatusRs.builder()
                    .status(e.getCode().getUiCode())
                    .build();
        }
    }

    /**
     * Удалить бумагу
     *
     * @param principal - пользак
     * @param ticker    - тикер удаляемой бумаги
     * @return
     */
    @CrossOrigin(origins = "*")
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
                        .map(this::prepareBondRs)
                        .collect(Collectors.toList()))
                .build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/calendar")
    public CalendarRs getCalendar(Principal principal) throws UserNotFoundException {
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        return calendarService.getCalendar(ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(this::prepareBondRs)
                        .collect(Collectors.toList()))
                .build());
    }

    /**
     * Добавить бумагу (с покупкой или в качестве плана).
     *
     * @param principal - пользак
     * @param request   - реквест, содержащий даты и тикер.
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public BondRs addInstrument(Principal principal, @RequestBody AddInstrumentRq request) throws UserNotFoundException {

        log.info("============== ADD INSTRUMENT ============== ");
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + request.getTicker());
        Bond newOrUpdatedBond;

        /*
         * Проверяем что хотя бы один такой инструмент нашелся, иначе кидаем эксепшн.
         */
        FoundInstrumentRs foundInstrument = commonService.findInstrument(request.getTicker())
                .getInstruments().stream()
                .filter(fi -> request.getTicker().equals(fi.getTicker()))
                .findFirst().orElseThrow(() -> new BadTickerException(request.getTicker()));

        /*
         * 1) Покупка фактическая (не ПЛАН)
         * 2) Кол-во купленных бумаг > 0
         * 3) Стоимость не пустая
         * 4) Дата покупки не пустая
         */
        if (!request.isPlan() && (request.getLot() != 0 && request.getPrice() != null && request.getPurchaseDate() != null)) {

            Optional<Bond> existingBond = bondsRepo.findBondByUserAndTicker(user, request.getTicker());

            Purchase purchase = new Purchase();
            purchase.setPrice(request.getPrice());
            purchase.setLot(request.getLot());
            purchase.setPurchaseDate(request.getPurchaseDate());

            /*
             * Если есть покупки по бумаге - добавляем в имеющуюся продажи
             */
            if (existingBond.isPresent()) {
                /*
                 * Если бумага уже была и она была в планах - переводим в статус ФАКТ.
                 */
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
    }

    /**
     * Поменять режим подсчета Дельты.
     *
     * @param principal - пользак
     * @param request   - реквест, содержащий даты и тикер.
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/toggledelta") //todo: все эти тогглы и фильтры надо пихать в отдельный контроллер по хорошему
    public ArNoteUser toggleDeltaMode(Principal principal, @RequestBody DeltaToggleRq request) throws UserNotFoundException { // todo: ошибку надо кинуть на фронт если deltaType придет пустой

        log.info("============== TOGGLE DELTA CALCULATION  ============== ");
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("mode: " + request.getDeltaType());

        user.setDeltaMode(DeltaMode.valueOf(request.getDeltaType()));
        user = usersRepo.saveAndFlush(user);
        return user;
    }

    /**
     * Получить настройки пользователя // todo: возможно сделать отдельный контроллер по пользаку и все это запихать туда
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/usersettings") //todo: все эти тогглы и фильтры надо пихать в отдельный контроллер по хорошему
    public ArNoteUser getUserSettings(Principal principal) throws UserNotFoundException {

        log.info("============== GET USER SETTINGS  ============== ");
        ArNoteUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER: " + user.getLogin());
        return user;
    }

    /**
     * Подготовить респонс бумаги.
     *
     * @param bond - данные по бумаге из БД.
     * @return
     */
    private BondRs prepareBondRs(Bond bond) { // todo: почему это вообще в контроллере?

        return BondRs.builder()
                .id(bond.getId())
                .ticker(bond.getTicker())
                .type(bond.getType().name())
                .isBought(bond.getIsBought())
                .stockExchange(bond.getStockExchange().name())
                .currentPrice(commonService.prepareCurrentPrice(bond))
                .currency(commonService.getCurrency(bond))
                .dividends(commonService.getDivsOrCoupons(bond))
                .minLot(commonService.getLot(bond))
                .finalPrice(commonService.getFinalPrice(bond))
                .delta(commonService.prepareDelta(bond))
                .description(commonService.getDescription(bond))
                .build();
    }

    /**
     * Достать юзера из Принципала
     *
     * @return LocalUser
     */
    public ArNoteUser getLocalUserFromPrincipal(Principal principal) throws UserNotFoundException { // todo: это можно запихать в аспект?
        return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
    }
}
