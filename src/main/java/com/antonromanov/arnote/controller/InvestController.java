package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.ConsolidatedReturnsRs;
import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.calc.CalculateService;
import com.antonromanov.arnote.service.investment.calc.ReturnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
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
    private final BondsRepo bondsRepo;
    private final CalculateService calculateService;
    private final ReturnsService returnsService;


    public InvestController(UsersRepo usersRepo, CalculateService calculateService, BondsRepo bondsRepo, ReturnsService returnsService) {
        this.usersRepo = usersRepo;
        this.calculateService = calculateService;
        this.bondsRepo = bondsRepo;
        this.returnsService = returnsService;
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

      // calculateService.getBondsFromMoexForBoardGroup("58");
       MoexRowsRs row = calculateService.getBondsFromMoex()
               .getData()
               .getRow()
               .stream()
               .filter(b->"SU26224RMFS4".equals(b.getSecid()))
       .findFirst().get();

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
                .stockExchange(bond.getStockExchange()) //todo: сделать поиск и автоматическое определение что за биржа
                .currentPrice(bond.getType()== BondType.SHARE ? (calculateService.getCurrentQuoteByTicker(user, bond.getTicker()).orElse((double) 0)) : null) //todo: кэш или БД??
                .currency(bond.getType()== BondType.SHARE ? calculateService.getCurrency(bond, user) : null)
                .dividends(bond.getType()== BondType.SHARE ? calculateService.getDividends(bond, user) : null)
                .minLot(bond.getType()== BondType.SHARE ? calculateService.getMinimalLot(bond, user): null)
                .finalPrice(bond.getType()== BondType.SHARE ? (calculateService.calculateFinalPrice(bond, user)) : null)
                .delta(bond.getType()== BondType.SHARE ? (calculateService
                        .calculateDelta(calculateService.prepareBoardId(bond.getTicker()), bond.getTicker(),
                                calculateService.getCurrentQuoteByTicker(user, bond.getTicker()).orElse((double) 0),
                                bond.getPurchaseList())) : null) // todo: а если список продаж в рублях, а определнная текущая цена в другой валюте????
                .description(bond.getType()== BondType.SHARE ? (calculateService.getInstrumentName(calculateService.prepareBoardId(bond.getTicker()),
                        bond.getTicker()).orElse("-")): null)
                .build();
    }
}
