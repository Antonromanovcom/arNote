package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.request.AddInstrumentRq;
import com.antonromanov.arnote.model.investing.response.*;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.PurchasesRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.calc.CalculateService;
import com.antonromanov.arnote.service.investment.calc.ReturnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * API для управления инвестициями.
 */
@CrossOrigin()
@RestController
@RequestMapping("/investing")
@Slf4j
public class InvestController {

    private final UsersRepo usersRepo;
    private final PurchasesRepo purchasesRepo;
    private final BondsRepo bondsRepo;
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
    public ConsolidatedInvestmentDataRs consolidatedBondsInfo(Principal principal) throws UserNotFoundException {

        log.info("============== CONSOLIDATED INVESTMENT TABLE ============== ");
        log.info("PRINCIPAL: " + principal.getName());


        LocalUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        return ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(bond -> prepareBondRs(bond, user))
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
                .bondsReturns(0L) // todo: облигации у меня пока вообще не реализованы никак. Нужно научиться определять тип бумаги
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

        List<MoexRowsRs> findedShares = allShares.getData().getRow().stream()
                .filter(s -> (s.getSecName().toLowerCase().contains(keyword.toLowerCase()) || s.getSecid().toLowerCase().contains(keyword)))
                .collect(Collectors.toList());


        List<MoexRowsRs> findedBonds = calculateService.getBondsFromMoex().getData().getRow().stream()
                .filter(s -> (s.getSecName().toLowerCase().contains(keyword.toLowerCase()) || s.getSecid().toLowerCase().contains(keyword)))
                .collect(Collectors.toList());

        SearchResultsRs searchResults = new SearchResultsRs();
        searchResults.setInstruments(findedShares.stream()
                .map(r -> FoundInstrumentRs.builder()
                        .ticker(r.getSecid())
                        .currencies(Currencies.search(r.getCurrencyId()))
                        .description(r.getSecName())
                        .stockExchange(StockExchange.MOEX)
                        .type(BondType.SHARE)
                        .build()).collect(Collectors.toList()));

        searchResults.getInstruments().addAll(findedBonds.stream()
                .map(r -> FoundInstrumentRs.builder()
                        .ticker(r.getSecid())
                        .currencies(Currencies.search(r.getCurrencyId()))
                        .description(r.getSecName())
                        .stockExchange(StockExchange.MOEX)
                        .type(BondType.BOND)
                        .build()).collect(Collectors.toList()));

        return searchResults;
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
    public String addInstrument(Principal principal, @RequestBody AddInstrumentRq request) throws UserNotFoundException {

        log.info("============== ADD INSTRUMENT ============== ");
        LocalUser user = usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        log.info("USER ID: " + user.getId());
        log.info("ticker: " + request.getTicker());

        if (!request.isPlan() && (request.getLot() != 0 && request.getPrice() != null && request.getPurchaseDate() != null)) {

            Optional<Bond> existingBond = bondsRepo.findBondByUserAndTicker(user, request.getTicker());
            Purchase purchase = new Purchase();
            purchase.setPrice(request.getPrice());
            purchase.setLot(request.getLot());
            purchase.setPurchaseDate(request.getPurchaseDate());

            if (existingBond.isPresent()) {
                existingBond.get().getPurchaseList().add(purchase);
                bondsRepo.save(existingBond.get());
            } else {
                Bond b = new Bond();
                b.setTicker(request.getTicker());
                b.setIsBought(true);
                b.setPurchaseList(Arrays.asList(purchase));
                b.setType(BondType.valueOf(request.getBondType()));
                b.setUser(user);
                b.setStockExchange(StockExchange.MOEX);
                bondsRepo.save(b);

            }
        } else {
            Bond b = new Bond();
            b.setTicker(request.getTicker());
            b.setIsBought(false);
            b.setType(BondType.valueOf(request.getBondType()));
            b.setUser(user);
            b.setStockExchange(StockExchange.MOEX);
            bondsRepo.save(b);
        }

        return "1";
    }

    /**
     * Подготовить респонс бумаги.
     *
     * @param bond - данные по бумаге из БД.
     * @return
     */
    private BondRs prepareBondRs(Bond bond, LocalUser user) {
        return BondRs.builder()
                .ticker(bond.getTicker())
                .type(bond.getType().name())
                .isBought(bond.getIsBought())
                .stockExchange(bond.getStockExchange().name())
                .currentPrice(prepareCurrentPrice(bond))
                .currency(bond.getType() == BondType.SHARE ? calculateService.getCurrencyOfShareFromDetailInfo(bond, user) :
                        calculateService.getBondCurrency(bond.getTicker()).name())
                .dividends(bond.getType() == BondType.SHARE ? calculateService.getDividends(bond, user) :
                        calculateService.getCoupons(bond, user))
                .minLot(bond.getType() == BondType.SHARE ? calculateService.getMinimalLot(bond, user) :
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
                (calculateService.getCurrentQuoteByTicker(bond.getTicker(),
                        calculateService.getBoardId(bond.getTicker())).orElse((double) 0)) :
                calculateService.getCurrentBondPrice(bond.getTicker());
    }

    /**
     * Посчитать дельту.
     *
     * @param bond
     * @return
     */
    private DeltaRs prepareDelta(Bond bond) {
        return bond.getType() == BondType.SHARE ? (calculateService
                .calculateDelta(calculateService.getBoardId(bond.getTicker()), bond.getTicker(),
                        calculateService.getCurrentQuoteByTicker(bond.getTicker(),
                                calculateService.getBoardId(bond.getTicker())).orElse((double) 0),
                        bond.getPurchaseList())) :
                DeltaRs.builder()
                        .tinkoffDeltaPercent(0D)
                        .deltaInRubles(0L)
                        .deltaPeriod(0L)
                        .tinkoffDelta(0D)
                        .build();
    }
}
