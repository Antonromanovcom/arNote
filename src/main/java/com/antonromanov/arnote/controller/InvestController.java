package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
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
import java.util.Collections;
import java.util.Optional;
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
    private final CalculateServiceImpl sendingService;


    public InvestController(UsersRepo usersRepo, CalculateServiceImpl sendingService, BondsRepo bondsRepo) {
        this.usersRepo = usersRepo;
        this.sendingService = sendingService;
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
                        .map(bond -> BondRs.builder()
                                .ticker(bond.getTicker())
                                .currentPrice(getCurrentQuote(user, bond.getTicker())) //todo: кэш ??
                                .dividends(sendingService.getDivsByTicker(user, bond.getTicker())
                                        .orElse(ConsolidatedDividendsRs.builder()
                                                .dividendList(Collections.emptyList())
                                                .percent(0)
                                                .divSum(Double.NaN)
                                                .build()))
                                .minLot(bond.getLot())
                                .finalPrice((int)Math.round(bond.getLot()!=null ?
                                        getCurrentQuote(user, bond.getTicker()) * bond.getLot() :
                                        Double.NaN))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Достать текущую ставку.
     *
     * @param user
     * @param ticker
     * @return
     */
    private Double getCurrentQuote(LocalUser user, String ticker) {
        return sendingService.getCurrentQuote(user, ticker).orElse(Double.NaN);
    }
}
