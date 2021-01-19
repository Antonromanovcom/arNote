package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.MoexXmlResponseMappingException;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.model.investing.response.StockExchange;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexInstrumentDetailRowsRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.calc.CalculateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
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
                .currentPrice(getCurrentQuote(user, bond.getTicker())) //todo: кэш или БД??
                .currency(getCurrency(bond, user))
                .dividends(getDividends(bond, user))
                .minLot(getMinimalLot(bond, user))
                .finalPrice(calculateFinalPrice(bond, user))
                .delta(sendingService.getDelta(getBoardId(bond.getTicker()), bond.getTicker(), getCurrentQuote(user, bond.getTicker()), bond.getPurchaseList())) // todo: а если список продаж в рублях, а определнная текущая цена в другой валюте????
                .description(sendingService.getInstrumentName(getBoardId(bond.getTicker()),bond.getTicker()).orElse("-"))
                .build();
    }

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @return
     */
    private Integer calculateFinalPrice(Bond bond, LocalUser user) { //todo: все подобные методы надо вынести в сервисы / хендлеры
        return (int) Math.round(bond.getLot() != null ?
                getCurrentQuote(user, bond.getTicker()) * bond.getLot() :
                Double.NaN);
    }

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    private ConsolidatedDividendsRs getDividends(Bond bond, LocalUser user) {
        return sendingService.getDivsByTicker(user, bond.getTicker())
                .orElse(ConsolidatedDividendsRs.builder()
                        .dividendList(Collections.emptyList())
                        .percent(0)
                        .divSum(Double.NaN)
                        .build());
    }


    /**
     * Подготовить минимальному лоту.
     *
     * @return
     */
    private Integer getMinimalLot(Bond bond, LocalUser user) {
        return sendingService.getDetailInfo(user, bond.getTicker())
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> "securities".equals(data.getId())) // todo: запихать в енум или константу или переделать весь запрос и исключить этот блок
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> getBoardId(bond.getTicker()).equals(row.getBoardId())) // todo: научиться брать откуда-то
                                .findFirst()
                                .map(share -> Integer.parseInt(share.getLotSize()))
                                .orElseThrow(() -> new MoexXmlResponseMappingException("row с нужным board_id")))
                        .orElseThrow(() -> new MoexXmlResponseMappingException("блок data с нужным id")))
                .orElse(1);
    }

    /**
     * Подготовить данные по валюте.
     *
     * @return
     */
    private String getCurrency(Bond bond, LocalUser user) {
        return sendingService.getDetailInfo(user, bond.getTicker())
                .map(detailInfo -> detailInfo.getDataList().stream()
                        .filter(data -> "securities".equals(data.getId())) // todo: запихать в енум или константу или переделать весь запрос и исключить этот блок
                        .findFirst()
                        .map(sc -> sc.getRowsList().stream()
                                .filter(row -> getBoardId(bond.getTicker()).equals(row.getBoardId())) // todo: научиться брать откуда-то
                                .findFirst()
                                .map(MoexInstrumentDetailRowsRs::getCurrencyId)
                                .orElseThrow(() -> new MoexXmlResponseMappingException("row с нужным board_id")))
                        .orElseThrow(() -> new MoexXmlResponseMappingException("блок data с нужным id"))) // todo: он не должен падать при невозможности прочитать по хорошему
                .orElse("-");
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

    /**
     * Достать board_id.
     *
     * @param ticker - тикер-бумаги.
     * @return
     */
    private String getBoardId(String ticker) {
        return sendingService.getBoardId(ticker).orElse("TQBR");
    }
}
