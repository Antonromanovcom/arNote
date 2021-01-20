package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.calc.CalculateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.stream.Collectors;


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
    private final CalculateServiceImpl calculateService;


    public InvestController(UsersRepo usersRepo, CalculateServiceImpl calculateService, BondsRepo bondsRepo) {
        this.usersRepo = usersRepo;
        this.calculateService = calculateService;
        this.bondsRepo = bondsRepo;
    }

    /**
     * Консолидированные данные по бумагам.
     *
     * @param principal
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/consolidated")
    public ConsolidatedInvestmentDataRs findAll(Principal principal) throws UserNotFoundException {

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
     * Подготовить респонс бумаги.
     *
     * @param bond - данные по бумаге из БД.
     * @return
     */
    private BondRs prepareBondRs(Bond bond, LocalUser user) {
        return BondRs.builder()
                .ticker(bond.getTicker())
                .isBought(bond.getIsBought())
                .stockExchange(bond.getStockExchange()) //todo: сделать поиск и автоматическое определение что за биржа
                .currentPrice(calculateService.getCurrentQuote(user, bond.getTicker()).orElse((double) 0)) //todo: кэш или БД??
                .currency(calculateService.getCurrency(bond, user))
                .dividends(calculateService.getDividends(bond, user))
                .minLot(calculateService.getMinimalLot(bond, user))
                .finalPrice(calculateService.calculateFinalPrice(bond, user))
                .delta(calculateService
                        .getDelta(calculateService.prepareBoardId(bond.getTicker()), bond.getTicker(),
                                calculateService.getCurrentQuote(user, bond.getTicker()).orElse((double) 0),
                                bond.getPurchaseList())) // todo: а если список продаж в рублях, а определнная текущая цена в другой валюте????
                .description(calculateService.getInstrumentName(calculateService.prepareBoardId(bond.getTicker()),
                        bond.getTicker()).orElse("-"))
                .build();
    }
}
