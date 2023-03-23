package com.antonromanov.arnote.domain.investing.api;

import com.antonromanov.arnote.domain.investing.dto.request.AddInstrumentRq;
import com.antonromanov.arnote.domain.investing.dto.response.*;
import com.antonromanov.arnote.domain.investing.dto.response.enums.StockExchange;
import com.antonromanov.arnote.domain.investing.service.calc.CommonService;
import com.antonromanov.arnote.domain.investing.service.consolidated.ConsolidatedDataService;
import com.antonromanov.arnote.common.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.domain.investing.exceptions.BadTickerException;
import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
    private final CalendarService calendarService;*/
    private final CommonService commonService;
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
     * @return - ConsolidatedReturnsRs
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/returns")
    public ConsolidatedReturnsRs returnsConsolidated() {
        return service.getSummaryIncomeData();
    }

    /**
     * Найти инструменты по имени / тикеру или их куску.
     *
     * @param keyword - искомое слово или часть его
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/search")
    public SearchResultsRs findInstrumentByName(@RequestParam @NotNull String keyword) {
        return commonService.findInstrument(keyword);
    }

    /**
     * Получить текущую цену по тикеру
     *
     * @param ticker - тикер.
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/price")
    public CurrentPriceRs getCurrentPriceByTickerAndStockExchange(@RequestParam @NotNull String ticker,
                                                                  @RequestParam @NotNull String stockExchange) {

        return commonService.getCurrentPriceByTicker(ticker, StockExchange.valueOf(stockExchange));
    }

    /**
     * Получить текущую цену по тикеру на конкретную дату
     *
     * @param ticker       - тикер.
     * @param purchaseDate - дата покупки.
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/price-by-date")
    public CurrentPriceRs getCurrentPriceByTicker(@RequestParam String ticker,
                                                  @RequestParam String purchaseDate) {

        if (isBlank(ticker) || isBlank(purchaseDate)) throw new  BadIncomeParameter(ErrorCodes.ERR_10);
        FoundInstrumentRs foundBond = commonService.findInstrument(ticker).getInstruments().stream()
                .filter(fi -> ticker.equals(fi.getTicker()))
                .findFirst().orElseThrow(() -> new BadTickerException(ticker));

        return commonService.getCurrentPriceByTickerAndDate(foundBond, purchaseDate);

    }

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
     * @param request   - реквест, содержащий даты и тикер.
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public BondRs addInstrument(@RequestBody AddInstrumentRq request) {
        return commonService.addInstrument(request);
    }

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
