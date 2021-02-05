package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.*;
import com.antonromanov.arnote.model.investing.request.AddInstrumentRq;
import com.antonromanov.arnote.model.investing.response.*;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.temp.SalePointInfo;
import com.antonromanov.arnote.model.temp.SalePointListRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.PurchasesRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.calc.CalculateService;
import com.antonromanov.arnote.service.investment.calc.ReturnsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.antonromanov.arnote.utils.Utils.*;


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
    private final PurchasesRepo purchasesRepo;
    private final CalculateService calculateService;
    private final ReturnsService returnsService;


    public InvestController(UsersRepo usersRepo, CalculateService calculateService, BondsRepo bondsRepo,
                            ReturnsService returnsService, PurchasesRepo purchasesRepo) {
        this.usersRepo = usersRepo;
        this.calculateService = calculateService;
        this.bondsRepo = bondsRepo;
        this.returnsService = returnsService;
        this.purchasesRepo = purchasesRepo;
    }

    /**
     * Консолидированные данные по бумагам.
     *
     * @param principal
     * @return
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

        LocalUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);

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
                user = usersRepo.saveAndFlush(user);
            } else {
                if (user.getInvestingFilterMode() != null && user.getInvestingFilterMode().size() > 0) {
                    user.getInvestingFilterMode().put(InvestingFilterMode.valueOf(filter).getKey(), filter);
                } else {
                    Map<String, String> filterMap = new HashMap<>();
                    filterMap.put(InvestingFilterMode.valueOf(filter).getKey(), filter);
                    user.setInvestingFilterMode(filterMap);
                }
                user = usersRepo.saveAndFlush(user);
            }
        }

        if (sort != null) {
            if (InvestingSortMode.valueOf(sort) == InvestingSortMode.NONE) {
                user.setInvestingSortMode(null);
                user = usersRepo.saveAndFlush(user);
            } else {
                user.setInvestingSortMode(InvestingSortMode.valueOf(sort));
                user = usersRepo.saveAndFlush(user);
            }
        }


        LocalUser finalUser = user;
        log.info("USER FILTER MAP: " + user.getInvestingFilterMode());
        log.info("USER SORT MODE: " + user.getInvestingSortMode());
        return ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(bond -> prepareBondRs(bond, finalUser))
                        .filter(user.getInvestingFilterMode() != null ? complexPredicate(user.getInvestingFilterMode()) :
                                s -> s.getTicker() != null)
                        .sorted(user.getInvestingSortMode() == null ? InvestingSortMode.NONE.getComparator() :
                                user.getInvestingSortMode().getComparator())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Консолидированные данные по доходности.
     *
     * @param principal
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/returns")
    public ConsolidatedReturnsRs returnsConsolidated(Principal principal) throws UserNotFoundException {

        log.info("============== CONSOLIDATED RETURNS TABLE ============== ");
        log.info("PRINCIPAL: " + principal.getName());

        LocalUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);

        return ConsolidatedReturnsRs.builder()
                .invested(returnsService.getTotalInvestment(user).orElse(0L))
                .bondsReturns(returnsService.getTotalBondsReturns(user).orElse(0L))
                .sharesDelta(returnsService.getSharesDelta(user).orElse(0L))
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
        LocalUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("keyword: " + keyword);

        Iterator<String> it = calculateService.getTradeModes().iterator();
        MoexDocumentRs allShares = new MoexDocumentRs();

        while (it.hasNext()) {
            String bid = it.next();
            MoexDocumentRs halfWayResult = calculateService.findSharesByBoardId(bid);
            halfWayResult.getData().getRow().forEach(q -> q.setBoardId(bid));
            if (allShares.getData() == null) {
                allShares = halfWayResult;
            } else {
                allShares.getData().getRow().addAll((halfWayResult).getData().getRow());
            }
        }

        List<MoexRowsRs> foundShares = allShares.getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());


        List<MoexRowsRs> foundBonds = calculateService.getBondsFromMoex().getData().getRow().stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

        SearchResultsRs searchResults = new SearchResultsRs();
        searchResults.setInstruments(prepareInstruments(foundShares, BondType.SHARE));
        searchResults.getInstruments().addAll(prepareInstruments(foundBonds, BondType.BOND));

        return searchResults;
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
    public CurrentPriceRs getCurrentPriceByTicker(Principal principal, @RequestParam @NotNull String ticker) throws UserNotFoundException {

        log.info("============== GET CURRENT PRICE BY TICKER ============== ");
        LocalUser user = getLocalUserFromPrincipal(principal);
        log.info("USER ID: " + user.getId());
        log.info("keyword: " + ticker);

        CurrentPriceRs resp = calculateService.getCurrentQuoteWith15MinuteUpdate(ticker);
        resp.setMinLot(calculateService.getMinimalLot(ticker, user));

        return resp;
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
    public CurrentPriceRs getCurrentPriceByTicker(Principal principal, @RequestParam @NotNull String ticker,
                                                  @RequestParam @NotNull String purchaseDate) throws UserNotFoundException {

        log.info("============== GET CURRENT PRICE BY TICKER AND PURCHASE DATE ============== ");
        LocalUser user = getLocalUserFromPrincipal(principal);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + ticker);
        log.info("purchase date: " + purchaseDate);

        if (LocalDate.parse(purchaseDate).isAfter(LocalDate.now())) {
            purchaseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        final String finalPurchaseDate = purchaseDate;
        return calculateService.getHistory(ticker, calculateService.getBoardId(ticker))
                .getData()
                .getRow()
                .stream()
                .filter(row -> LocalDate.parse(row.getTradeDate()).isEqual(LocalDate.parse(finalPurchaseDate)))
                .findFirst()
                .map(data -> CurrentPriceRs.builder()
                        .currentPrice(Double.valueOf(data.getLegalClosePrice()))
                        .date(LocalDate.parse(finalPurchaseDate))
                        .build())
                .orElse(calculateService.getCurrentQuoteWith15MinuteUpdate(ticker));

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
        LocalUser user = getLocalUserFromPrincipal(principal);
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
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }


    @CrossOrigin(origins = "*")
    @GetMapping("/test-json")
    public BondRs testTest(Principal principal) throws IOException, URISyntaxException {
        File f = getFileFromResource("data/temp.json");
        ObjectMapper mapper = new ObjectMapper();
        SalePointListRs result = mapper.readValue(f, SalePointListRs.class);

        result.getBody().getListOfSalePoint().stream()
                .filter(r->r.getAccountInfo().getId().equals("1-CEJXX"))
                .map(s->s.getSalePointInfo())
                .map(SalePointInfo::getListOfContact)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        result.getBody().getListOfSalePoint().stream()
                .map(s->s.getSalePointInfo())
                .map(SalePointInfo::getListOfContact)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return null;
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
        LocalUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + request.getTicker());
        Bond newOrUpdatedBond;

        if (!request.isPlan() && (request.getLot() != 0 && request.getPrice() != null && request.getPurchaseDate() != null)) {

            Optional<Bond> existingBond = bondsRepo.findBondByUserAndTicker(user, request.getTicker());
            Purchase purchase = new Purchase();
            purchase.setPrice(request.getPrice());
            purchase.setLot(request.getLot());
            purchase.setPurchaseDate(request.getPurchaseDate());

            if (existingBond.isPresent()) {
                existingBond.get().getPurchaseList().add(purchase);
                newOrUpdatedBond = bondsRepo.saveAndFlush(existingBond.get());
            } else {
                Bond b = new Bond();
                b.setTicker(request.getTicker());
                b.setIsBought(true);
                b.setPurchaseList(Collections.singletonList(purchase));
                b.setType(BondType.valueOf(request.getBondType()));
                b.setUser(user);
                b.setStockExchange(StockExchange.MOEX);
                newOrUpdatedBond = bondsRepo.saveAndFlush(b);
            }
        } else {
            Bond b = new Bond();
            b.setTicker(request.getTicker());
            b.setIsBought(false);
            b.setType(BondType.valueOf(request.getBondType()));
            b.setUser(user);
            b.setStockExchange(StockExchange.MOEX);
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
     * Подготовить респонс бумаги.
     *
     * @param bond - данные по бумаге из БД.
     * @return
     */
    private BondRs prepareBondRs(Bond bond, LocalUser user) {

        return BondRs.builder()
                .id(bond.getId())
                .ticker(bond.getTicker())
                .type(bond.getType().name())
                .isBought(bond.getIsBought())
                .stockExchange(bond.getStockExchange().name())
                .currentPrice(prepareCurrentPrice(bond))
                .currency(bond.getType() == BondType.SHARE ? calculateService.getCurrencyOfShareFromDetailInfo(bond.getTicker(), user) :
                        calculateService.getBondCurrency(bond.getTicker()).name()) //todo: сделать фабрику по бирже / типу бумаги
                .dividends(bond.getType() == BondType.SHARE ? calculateService.getDividends(bond, user) :
                        calculateService.getCoupons(bond, user))
                .minLot(bond.getType() == BondType.SHARE ? calculateService.getMinimalLot(bond.getTicker(), user) :
                        calculateService.getBondLot(bond, user, bond.getPurchaseList()))
                .finalPrice(calculateService.calculateFinalPrice(bond, user))
                .delta(prepareDelta(bond)) // todo: а если список продаж в рублях, а определенная текущая цена в другой валюте????
                .description(bond.getType() == BondType.SHARE ? (calculateService.getInstrumentName(calculateService.getBoardId(bond.getTicker()),
                        bond.getTicker()).orElse("-")) :
                        (calculateService.getBondName(bond.getTicker()).orElse("-")))
                .build();
    }

    /**
     * Посчитать текущую цены бумаги.
     *
     * @param bond
     * @return
     */
    private Double prepareCurrentPrice(Bond bond) {
        return bond.getType() == BondType.SHARE ?
                (calculateService.getCurrentQuoteWith15MinuteUpdate(bond.getTicker()).getCurrentPrice()) :
                calculateService.getCurrentBondPrice(bond.getTicker());
    }

    /**
     * Посчитать дельту.
     *
     * @param bond
     * @return
     */
    private DeltaRs prepareDelta(Bond bond) {
        return bond.getType() == BondType.SHARE ?
                (calculateService.calculateDelta(calculateService.getBoardId(bond.getTicker()), bond.getTicker(),
                        calculateService.getCurrentQuoteWith15MinuteUpdate(bond.getTicker()).getCurrentPrice(),
                        bond.getPurchaseList())) :
                DeltaRs.builder()
                        .tinkoffDeltaPercent(0D)
                        .deltaInRubles(0L)
                        .deltaPeriod(0L)
                        .tinkoffDelta(0D)
                        .build();
    }

    /**
     * Достать юзера из Принципала
     *
     * @return LocalUser
     */
    public LocalUser getLocalUserFromPrincipal(Principal principal) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
    }
}
