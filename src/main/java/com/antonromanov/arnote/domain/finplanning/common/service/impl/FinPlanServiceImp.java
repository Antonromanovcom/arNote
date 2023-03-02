package com.antonromanov.arnote.domain.finplanning.common.service.impl;

import com.antonromanov.arnote.domain.finplanning.common.dto.rq.GetRemainsDetailInfoRq;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanListRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;
import com.antonromanov.arnote.domain.finplanning.common.enums.CalcValues;
import com.antonromanov.arnote.domain.finplanning.common.enums.HtmlFontColors;
import com.antonromanov.arnote.domain.finplanning.common.mapper.CommonMapperRs;
import com.antonromanov.arnote.domain.finplanning.common.service.FinPlanService;
import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.freeze.entity.Freeze;
import com.antonromanov.arnote.domain.finplanning.freeze.service.FreezeService;
import com.antonromanov.arnote.domain.finplanning.goal.service.GoalsService;
import com.antonromanov.arnote.domain.finplanning.income.entity.Income;
import com.antonromanov.arnote.domain.finplanning.income.service.IncomeService;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.CreditListRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.CreditRs;
import com.antonromanov.arnote.domain.finplanning.loan.service.LoanService;
import com.antonromanov.arnote.domain.salary.service.SalaryService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.old.dto.rs.CurrentIncomeRs;
import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.exceptions.UserNotFoundException;
import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static com.antonromanov.arnote.domain.finplanning.common.enums.BackGroundColors.*;
import static com.antonromanov.arnote.old.utils.ArNoteUtils.getMonthByNumber;
import static com.antonromanov.arnote.old.utils.ArNoteUtils.localDateToDate;

@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class FinPlanServiceImp implements FinPlanService {

    private final UserService userService;
    private final GlobalCache globalCache;
    private final LoanService loanService;
    private final GoalsService goalsService;
    private final SalaryService salaryService;
    private final FreezeService freezeService;
    private final IncomeService incomeService;
    private final CommonMapperRs mapper;

    @Value("${finplan.final.year}")
    private Integer finalYear; // + кол-во лет по которым считаем верхнюю границу диапазона отображаемого в консолидированной таблице

    @Value("${finplan.start.month}")
    private Integer startMonth; // месяц с которого начинаем отсчет

    @Value("${finplan.start.year}")
    private Integer startYear; // год с которого начинаем отсчет

    private final String BORDER_WIDTH_3 = "3px double #8B0000";
    private final String BORDER_WIDTH_1 = "1px solid grey";


    /**
     * Подготовить диапазон лет по которым ходим в цикле для заполнения массивов.
     *
     * @param yearsCount - количество лет.
     * @return коллекция с Integer
     */
    public List<Integer> getYearsRange(int yearsCount) {
        return IntStream.rangeClosed(1, yearsCount)
                .boxed()
                .collect(Collectors.toList());
    }

    private void checkStartMonthValue(int yearsCount) {
        if (startMonth == null || startMonth < 1 || startMonth > 12 || yearsCount == 1) {
            startMonth = 1;
        }
    }


    /**
     * Запросить консолидированную таблицу из кэша.
     *
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public FinPlanListRs getFinPlanTableFromCache() throws UserNotFoundException {
        if (globalCache.getGlobalConsolidatedTable().getFinPlans() == null) {
            return getFinPlanTableFromDb();
        } else {
            return globalCache.getGlobalConsolidatedTable();
        }
    }

    /**
     * Подсчитать кол-во лет для циклинга по годам.
     *
     * @return
     */
    private int calculateYearsCount(ArNoteUser arNoteUser) {
        int curYear = globalCache.getCurrentYear();
        return (startYear == null || startYear < 2000) ? //todo: в константы?
                curYear - 2019 + 1 : //todo: в константы?
                (getFinalYear(arNoteUser)) - startYear + 1;
    }


    /**
     * Запросить консолидированную таблицу из БД.
     *
     * @return
     */
    @Override
    public FinPlanListRs getFinPlanTableFromDb() {
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        globalCache.fillGoals(goalsService.getAllPurchases(arNoteUser));
        int finalCalculatedYear = getFinalYear(arNoteUser);
        loanService.getCalculatedLoansTable(arNoteUser); // todo: ** 1 ** // todo: может сделать void? Мы же кладем в кеш! Почему бы не доставать оттуда?
        int yearsCount = calculateYearsCount(arNoteUser);
        checkStartMonthValue(yearsCount);

        calculateFullRemains(startMonth, yearsCount, finalCalculatedYear); // todo: ** 2 ( => 1) ** todo: как вариант это все можно запустить в 12 потоках или подумать над рекурсив таскс. Надо дмать в общем
        FinPlanListRs table = runThroughTheYearsAndFillFinPlanList(yearsCount, finalCalculatedYear);
        globalCache.setGlobalConsolidatedTable(table);
        return table;
    }


    private FinPlanListRs runThroughTheYearsAndFillFinPlanList(int yearsCount, int finalCalculatedYear) {
        List<FinPlanRs> finalList = new ArrayList<>();
        getYearsRange(yearsCount).forEach(y -> {

            int startPoint = (y == 1) ? startMonth : 1;

            for (int currMonth = startPoint; currMonth <= 12; currMonth++) {
                int localYear = (finalCalculatedYear) - yearsCount + y;
                CreditListRs credits = loanService.getCreditsFiltered(localYear, currMonth);
                finalList.add(FinPlanRs.builder()
                        .month(getMonthByNumber(currMonth))
                        .monthNumber(currMonth)
                        .year(localYear)
                        .creditsList(credits)
                        .allCredits(credits.getCredits().stream().map(CreditRs::getAmount).reduce(Integer::sum).orElse(0))
                        .purchasePlan(globalCache.getPurchasePlan(localYear, currMonth))
                        .remains(globalCache.prepareFinalBalance(localYear, currMonth))
                        .freeze(freezeService.isThisFreeze(localYear, currMonth))
                        .color(getRowBackGroundColor(localYear, currMonth))
                        .fontColor(HtmlFontColors.calculateColor(
                                globalCache.getPurchasePlan(localYear, currMonth).getPurchasePlan()
                                        .stream()
                                        .anyMatch(p -> p.getLoanId() != null)))
                        .borderWidth(currMonth == 12 ? BORDER_WIDTH_3 : BORDER_WIDTH_1)
                        .build());
            }
        });

        return FinPlanListRs.builder().finPlans(finalList).build();
    }

    /**
     * Проверка условия: дата обрабатываемого месяца раньше чем текущая.
     *
     * @param currentYear
     * @param currMonth
     * @return - boolean
     */
    private Boolean dateEarlierThanNow(int currentYear, int currMonth) {
        return (LocalDate.now().withDayOfMonth(1).isAfter(LocalDate.of(currentYear, currMonth, 1)));
    }

    /**
     * Проверка условия: дата обрабатываемого месяца позже чем текущая.
     *
     * @param currentYear
     * @param currMonth
     * @return - boolean
     */
    private Boolean dateLaterThanNow(int currentYear, int currMonth) {
        return (LocalDate.now().withDayOfMonth(1).isBefore(LocalDate.of(currentYear, currMonth, 1)));
    }

    /**
     * Определить цвет ряда.
     *
     * @param currentYear
     * @param currMonth
     * @return
     */
    private String getRowBackGroundColor(int currentYear, int currMonth) {
        if (dateEarlierThanNow(currentYear, currMonth)) { // дата обрабатываемого месяца раньше чем текущая
            return EARLIER_MONTH_HTML_COLOR.getHexValue();
        } else if (dateLaterThanNow(currentYear, currMonth)) { // дата обрабатываемого месяца позже чем текущая
            return FUTURE_MONTH_HTML_COLOR.getHexValue();
        } else { // даты равны
            return CURRENT_MONTH_HTML_COLOR.getHexValue();
        }
    }

    /**
     * Высчитываем верхнюю границу периода (год) отображения консолидированной таблицы.
     *
     * @return
     */
    private Integer getFinalYear(ArNoteUser user) {
        Integer yearFromApplicationProperties = globalCache.getCurrentYear() + finalYear;

        if (loanService.getLastCreditDate(user).isPresent()) {
            LocalDate creditDate = loanService.getLastCreditDate(user).get();
            LocalDate lastGoalsDate = goalsService.getLastGoalsDate(globalCache.getGlobalGoalList());
            return Stream.of(creditDate.getYear(), lastGoalsDate.getYear(), yearFromApplicationProperties)
                    .max(Integer::compareTo)
                    .orElse(yearFromApplicationProperties);
        } else {
            return yearFromApplicationProperties;
        }
    }

    /**
     * Подсчитать остатки.
     *
     * @param valuesMap
     * @return
     */
    private Integer calcRemains(EnumMap<CalcValues, Integer> valuesMap) {
        return valuesMap.get(CalcValues.PREV_INCOME) - // предыдущий доход
                valuesMap.get(CalcValues.PREV_EXPENSE) - // минус предыдущий расход
                valuesMap.get(CalcValues.MONTHLY_SPENDING) - // минус среднемесячный расход
                valuesMap.get(CalcValues.LOAN_PAYMENTS_BY_DATE) + // минус покрытие кредитов
                valuesMap.get(CalcValues.CURRENT_INCOME) + // + ежемесячный доход
                valuesMap.get(CalcValues.CURRENT_FULL_SALARY_BY_DATE); // + зарплата
    }

    /**
     * Заполнить мапу значений.
     *
     * @return
     */
    private EnumMap<CalcValues, Integer> calculateValueMap(int year, int month) {

        EnumMap<CalcValues, Integer> valuesMap = new EnumMap<>(CalcValues.class);
        valuesMap.put(CalcValues.MONTHLY_SPENDING, salaryService.getMonthlySpending(year, month));
        valuesMap.put(CalcValues.CURRENT_INCOME, incomeService.getCurrentIncome(year, month));
        valuesMap.put(CalcValues.PREV_INCOME, incomeService.getPreviousIncome(year, month,
                globalCache.getGlobalBalanceMap()));
        valuesMap.put(CalcValues.PREV_EXPENSE, globalCache.getPreviousExpense(year, month));
        valuesMap.put(CalcValues.LOAN_PAYMENTS_BY_DATE, loanService.getLoanPaymentsByDate(year, month));
        valuesMap.put(CalcValues.CURRENT_FULL_SALARY_BY_DATE, salaryService.getClosestSalary(year, month)
                .map(Salary::getFullSalary).orElse(0));
        valuesMap.put(CalcValues.CALCULATED_REMAINS, calcRemains(valuesMap));
        valuesMap.put(CalcValues.BALANCE, (freezeService.filterFreezeListByDate(year, month))
                .map(Freeze::getAmount).orElse(valuesMap.get(CalcValues.CALCULATED_REMAINS)));

        return valuesMap;
    }


    /**
     * Считаем остаток по счетам. Расклад по всем месяцам. Заполняем глобальную мапу приходов.
     *
     * @param startMonth          - начальный месяц (из конфигов).
     * @param yearsCount
     * @param finalCalculatedYear
     */
    private void calculateFullRemains(int startMonth, int yearsCount, int finalCalculatedYear) {

        globalCache.clearGlobalBalanceMap();
        getYearsRange(yearsCount).forEach(y -> {

            int startPoint = (y == 1) ? startMonth : 1;

            for (int currMonth = startPoint; currMonth <= 12; currMonth++) {

                int calculatedYear = finalCalculatedYear - yearsCount + y;
                List<Income> incomesForCurrentDate = incomeService.incomesForCurrentDate(calculatedYear, currMonth);
                Integer salary = salaryService.getClosestSalary(calculatedYear, currMonth).map(Salary::getFullSalary).orElse(0);
                CurrentIncomeRs curIncome = mapper.mapCurrentIncome(salary, incomesForCurrentDate);
                globalCache.putInGlobalBalanceMap(LocalDate.of(calculatedYear, currMonth, 1),
                        mapper.mapMapToFinalCalculations(calculateValueMap(calculatedYear, currMonth), curIncome));
            }
        });
    }

    /**
     * Получить детализированный расчет баланса за месяц.
     *
     * @param payload
     * @return
     */
    @Override
    public FinalBalanceCalculationsRs getRemainsDetailInfo(GetRemainsDetailInfoRq payload) {

        ArNoteUser arNoteUser = userService.getUserFromPrincipal();

        if (globalCache.getGlobalBalanceMap().isEmpty()) {
            int finalCalculatedYear = getFinalYear(arNoteUser);
            int yearsCount = calculateYearsCount(arNoteUser);
            checkStartMonthValue(calculateYearsCount(arNoteUser));
            calculateFullRemains(startMonth, yearsCount, finalCalculatedYear);
        }

        Optional<Freeze> currentFreeze = freezeService.filterFreezeListByDate(payload.getYear(), payload.getMonth());

        if (globalCache.getGlobalBalanceMap().size() < 1) { // если пользак только начал и у него ничего не заполнено
            return getFinalBalanceInCaseGlobalBalanceMapIsEmpty(payload.getYear(), payload.getMonth());
        } else {
            return fillGlobalBalanceMapForRemainsCalculations(payload.getYear(), payload.getMonth(), currentFreeze.isPresent());
        }
    }

    /**
     * Вызывается при детализированном расчете баланса за месяц если GlobalBalanceMap пустой.
     *
     * @return
     */
    private FinalBalanceCalculationsRs getFinalBalanceInCaseGlobalBalanceMapIsEmpty(int year, int month) {
        String dateInStringFormat = year + " " + getMonthByNumber(month);
        Date date = localDateToDate(LocalDate.of(year, month, 1));
        return mapper.fillEmptyCalculations(dateInStringFormat, date);
    }

    private FinalBalanceCalculationsRs fillGlobalBalanceMapForRemainsCalculations(int year, int month, boolean freezeIsPresent) {
        var globalBalanceMap = globalCache.getGlobalBalanceMap();
        return globalBalanceMap.entrySet().stream()
                .filter(v -> v.getKey().getYear() == year && v.getKey().getMonthValue() == month)
                .peek(ee -> processMapEntry(ee, year, month, freezeIsPresent))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(getFinalBalanceInCaseGlobalBalanceMapIsEmpty(year, month));
    }

    private void processMapEntry(Map.Entry<LocalDate, FinalBalanceCalculationsRs> entry, int year, int month, boolean freezeIsPresent) {
        entry.getValue().setDate(year + " " + getMonthByNumber(month));
        entry.getValue().setDateInDateFormat(localDateToDate(LocalDate.of(year, month, 1)));
        entry.getValue().setFreeze(freezeIsPresent);
        entry.getValue().setEmptyCalculations(false);
    }
}
