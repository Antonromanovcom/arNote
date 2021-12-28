package com.antonromanov.arnote.services;

import com.antonromanov.arnote.dto.rq.*;
import com.antonromanov.arnote.dto.rs.*;
import com.antonromanov.arnote.dto.transfer.CalculatedLoansTableTr;
import com.antonromanov.arnote.dto.transfer.LoanListTr;
import com.antonromanov.arnote.dto.transfer.LoanTr;
import com.antonromanov.arnote.entity.common.Salary;
import com.antonromanov.arnote.entity.finplan.Credit;
import com.antonromanov.arnote.entity.finplan.Freeze;
import com.antonromanov.arnote.entity.finplan.Goal;
import com.antonromanov.arnote.entity.finplan.Income;
import com.antonromanov.arnote.exceptions.AddNewCreditException;
import com.antonromanov.arnote.exceptions.FinPlanningException;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.repositoty.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.antonromanov.arnote.utils.ArNoteUtils.getMonthByNumber;
import static com.antonromanov.arnote.utils.ArNoteUtils.localDateToDate;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinPlanServiceImp implements FinPlanService { //todo: класс больше 1000 строк! Разделить!

    @Autowired
    private CreditRepository creditRepo;

    @Autowired
    private UsersRepo users;

    @Autowired
    private GoalsRepo purchaseRepo;

    @Autowired
    private SalaryRepository salaryRepo;

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private FreezeRepo freezeRepo;


    @Value("${finplan.start.month}")
    private Integer startMonth; // месяц с которого начинаем отсчет

    @Value("${finplan.start.year}")
    private Integer startYear; // год с которого начинаем отсчет

    @Value("${finplan.final.year}")
    private Integer finalYear; // + кол-во лет по которым считаем верхнюю границу диапазона отображаемого в консолидированной таблице


    // =================== БАЗОВЫЕ ГЛОБАЛЬНЫЕ КОНСТАНТЫ ======================
    private int curYear; // текущий год
    LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> globalBalanceMap = new LinkedHashMap<>(); // глобальная мапа остатков
    List<Goal> globalGoalList = new ArrayList<>(); // глобальная мапа расходов
    FinPlanListRs globalConsolidatedTable; // глобальная консолидированная мапа чтобы каждый раз не ходить в БД
    // =================== ============================ ======================


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


    /**
     * Запросить консолидированную таблицу из кэша.
     *
     * @param principal - юзер.
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public FinPlanListRs getFinPlanTableFromCache(Principal principal) throws UserNotFoundException {
        if (globalConsolidatedTable == null) {
            return getFinPlanTableFromDb(principal);
        } else {
            return globalConsolidatedTable;
        }
    }

    /**
     * Запросить консолидированную таблицу из БД.
     *
     * @param principal - юзер.
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public FinPlanListRs getFinPlanTableFromDb(Principal principal) throws UserNotFoundException {
        curYear = Calendar.getInstance().get(Calendar.YEAR);
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        globalGoalList = purchaseRepo.findAllByUser(arNoteUser);

        int finalCalculatedYear = getFinalYear(arNoteUser);
        int yearsCount = (startYear == null || startYear < 2000) ?
                curYear - 2019 + 1 :
                (finalCalculatedYear) - startYear + 1;

        if (startMonth == null || startMonth < 1 || startMonth > 12 || yearsCount == 1) {
            startMonth = 1;
        }

        List<FinPlanRs> finalList = new ArrayList<>();
        calculateFullRemains(arNoteUser, startMonth, yearsCount, finalCalculatedYear);
        getYearsRange(yearsCount).forEach(y -> {

            int startPoint = 1;
            if (y == 1) {
                startPoint = startMonth;
            }
            for (int currMonth = startPoint; currMonth <= 12; currMonth++) {
                int localYear = (finalCalculatedYear) - yearsCount + y;
                finalList.add(FinPlanRs.builder()
                        .month(getMonthByNumber(currMonth))
                        .monthNumber(currMonth)
                        .year(localYear)
                        .credits(getCreditsFiltered(getAllCredits(arNoteUser), localYear, currMonth))
                        .purchasePlan(getPurchasePlan(localYear, currMonth))
                        .remains(prepareFinalBalance(localYear, currMonth))
                        .freeze(isThisFreeze(localYear, currMonth, arNoteUser))
                        .color(getRowBackGroundColor(localYear, currMonth))
                        .fontColor(getFontColor(localYear, currMonth, getPurchasePlan(localYear, currMonth)
                                .getPurchasePlan()
                                .stream()
                                .anyMatch(p -> p.getLoanId() != null)))
                        .build());
            }

        });
        globalConsolidatedTable = FinPlanListRs.builder().finPlans(finalList).build();
        return globalConsolidatedTable;
    }

    /**
     * Рассчитать цвет шрифта.
     *
     * @param currentYear
     * @param currMonth
     * @param isRepayment
     * @return
     */
    private String getFontColor(int currentYear, int currMonth, boolean isRepayment) {
        if (isRepayment) {
            return "#7b10b1";
        } else {
            if (LocalDate.now().withDayOfMonth(1).isAfter(LocalDate.of(currentYear, currMonth, 1))) { // дата обрабатываемого месяца раньше чем текущая
                return "#b0bbb0";
            } else if (LocalDate.now().withDayOfMonth(1).isBefore(LocalDate.of(currentYear, currMonth, 1))) { // дата обрабатываемого месяца позже чем текущая
                return "#020008";
            } else { // даты равны
                return "#020008";
            }
        }
    }

    /**
     * Определить цвет ряда.
     *
     * @param currentYear
     * @param currMonth
     * @return
     */
    private String getRowBackGroundColor(int currentYear, int currMonth) {
        if (LocalDate.now().withDayOfMonth(1).isAfter(LocalDate.of(currentYear, currMonth, 1))) { // дата обрабатываемого месяца раньше чем текущая
            return "#6c796c";
        } else if (LocalDate.now().withDayOfMonth(1).isBefore(LocalDate.of(currentYear, currMonth, 1))) { // дата обрабатываемого месяца позже чем текущая
            return "#9abacd";
        } else { // даты равны
            return "#f5e7c8";
        }
    }

    private Integer prepareFinalBalance(int year, int month) {
        if (!globalBalanceMap.isEmpty()) {
            return globalBalanceMap.entrySet().stream()
                    .filter(v -> v.getKey().getYear() == year && v.getKey().getMonthValue() == month)
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .map(FinalBalanceCalculationsRs::getBalance)
                    .orElse(0);
        } else {
            return 0;
        }
    }

    private Boolean isThisFreeze(int year, int month, ArNoteUser user) {
        return (freezeRepo.findFreezeByUserAndMonthAndYear(user, year, month)).isPresent();
    }

    /**
     * Достать все кредиты
     *
     * @return
     */
    private List<Credit> getAllCredits(ArNoteUser user) {
        return creditRepo.getCreditsByUser(user);
    }

    /**
     * Высчитываем верхнюю границу периода (год) отображения консолидированной таблицы.
     *
     * @return
     */
    private Integer getFinalYear(ArNoteUser user) {
        LocalDate creditDate = getLastCreditDate(getAllCredits(user));
        LocalDate lastGoalsDate = getLastGoalsDate(globalGoalList);
        Integer yearFromApplicationProperties = curYear + finalYear;
        return Stream.of(creditDate.getYear(), lastGoalsDate.getYear(), yearFromApplicationProperties)
                .max(Integer::compareTo)
                .orElse(yearFromApplicationProperties);
    }


    /**
     * Считаем остаток по счетам. Расклад по всем месяцам. Заполняем глобальную мапу приходов.
     *
     * @param user                - пользак.
     * @param startMonth          - начальный месяц (из конфигов).
     * @param yearsCount
     * @param finalCalculatedYear
     * @throws UserNotFoundException
     */
    private void calculateFullRemains(ArNoteUser user, int startMonth, int yearsCount, int finalCalculatedYear) {

        globalBalanceMap.clear();
        List<Income> allIncomesByUser = incomeRepo.findAllByUserOrderByIncomeDateAsc(user); // все доходы юзера
        if (allIncomesByUser.size() != 0) {

            getYearsRange(yearsCount).forEach(y -> {

                int startPoint = 1;
                if (y == 1) {
                    startPoint = startMonth;
                }

                for (int currMonth = startPoint; currMonth <= 12; currMonth++) {

                    Optional<Salary> currentSalaryByDate = getClosestSalary((finalCalculatedYear - yearsCount + y), currMonth, user);
                    int monthlySpending = (currentSalaryByDate)
                            .map(Salary::getLivingExpenses)
                            .orElse(0); // средние ежемесячные расходы

                    int finalCurrMonth = currMonth;
                    Optional<Freeze> currentFreeze = freezeRepo.findFreezeByUserAndMonthAndYear(user,
                            (finalCalculatedYear - yearsCount + y),
                            currMonth);

                    List<Income> incomesForCurrentDate = allIncomesByUser.stream()
                            .filter(i -> dateToLocalDate(i.getIncomeDate()).getYear() == (finalCalculatedYear - yearsCount + y)) // фильтруем по году
                            .filter(i -> dateToLocalDate(i.getIncomeDate()).getMonthValue() == finalCurrMonth)
                            .collect(Collectors.toList());
                    int currentIncome = incomesForCurrentDate
                            .stream()
                            .findFirst()
                            .map(Income::getIncome)
                            .orElse(0);

                    int calculatedRemains = getPreviousIncome(currMonth, (finalCalculatedYear - yearsCount + y)) - // предыдущий доход
                            getPreviousExpense(currMonth, (finalCalculatedYear - yearsCount + y)) - // минус предыдущий расход
                            monthlySpending - // минус среднемесячный расход
                            getLoanPaymentsByDate(currMonth, (finalCalculatedYear - yearsCount + y), user) + // минус покрытие кредитов
                            currentIncome; // + ежемесячный доход
                    currentSalaryByDate.map(Salary::getFullSalary); // + зарплата

                    if (globalBalanceMap.isEmpty()) {
                        globalBalanceMap.put(LocalDate.of((finalCalculatedYear - yearsCount + y), currMonth, 1),
                                FinalBalanceCalculationsRs.builder()
                                        .balance(currentFreeze.map(Freeze::getAmount).orElse(calculatedRemains))
                                        .currentIncome(currentIncome)
                                        .loanPayments(getLoanPaymentsByDate(currMonth, (finalCalculatedYear - yearsCount + y), user))
                                        .monthlySpending(monthlySpending)
                                        .currentIncomeDetail(CurrentIncomeRs.builder()
                                                .salary(currentSalaryByDate.map(Salary::getFullSalary).orElse(0))
                                                .incomeList(incomesForCurrentDate.stream().map(i -> IncomeRs.builder()
                                                        .id(i.getId())
                                                        .incomeDate(i.getIncomeDate())
                                                        .incomeDescription(i.getDescription())
                                                        .amount(i.getIncome())
                                                        .isBonus(i.getIsBonus())
                                                        .build())
                                                        .collect(Collectors.toList()))
                                                .build())
                                        .previousExpense(getPreviousExpense(currMonth, (finalCalculatedYear - yearsCount + y)))
                                        .previousIncome(getPreviousIncome(currMonth, (finalCalculatedYear - yearsCount + y)))
                                        .build());
                    } else {

                        globalBalanceMap.put(LocalDate.of((finalCalculatedYear - yearsCount + y), currMonth, 1),
                                FinalBalanceCalculationsRs.builder()
                                        .balance(currentFreeze.map(Freeze::getAmount).orElse(calculatedRemains))
                                        .currentIncomeDetail(CurrentIncomeRs.builder()
                                                .salary(currentSalaryByDate.map(Salary::getFullSalary).orElse(0))
                                                .incomeList(incomesForCurrentDate.stream().map(i -> IncomeRs.builder()
                                                        .id(i.getId())
                                                        .incomeDate(i.getIncomeDate())
                                                        .incomeDescription(i.getDescription())
                                                        .isBonus(i.getIsBonus())
                                                        .amount(i.getIncome())
                                                        .build())
                                                        .collect(Collectors.toList()))
                                                .build())
                                        .currentIncome(currentIncome)
                                        .loanPayments(getLoanPaymentsByDate(currMonth, (finalCalculatedYear - yearsCount + y), user))
                                        .monthlySpending(monthlySpending)
                                        .previousExpense(getPreviousExpense(currMonth, (finalCalculatedYear - yearsCount + y)))
                                        .previousIncome(getPreviousIncome(currMonth, (finalCalculatedYear - yearsCount + y)))
                                        .build());
                    }
                }
            });
        }
    }

    /**
     * Платежи по кредитам по состоянию на конкретную дату.
     *
     * @param curMonth
     * @param curYear
     * @return
     */
    private Integer getLoanPaymentsByDate(int curMonth, int curYear, ArNoteUser user) {

        return getCalculatedLoansTable(getAllCredits(user)).getCalculatedLoansList().stream()
                .map(l -> l.entrySet().stream()
                        .filter(d -> d.getKey().getYear() == curYear && d.getKey().getMonthValue() == curMonth)
                        .map(m -> m.getValue().getLoanList().stream()
                                .findFirst()
                                .map(v -> creditRepo.findById(v.getLoanId())
                                        .map(Credit::getFullPayPerMonth)
                                        .orElse(0)
                                )
                                .orElse(0))
                        .reduce(Integer::sum)
                        .orElse(0))
                .findFirst()
                .orElse(0);
    }

    /**
     * Вытаскиваем предыдущий приход из мапы приходов.
     *
     * @param curMonth - текущий месяц относительного которого происходит запрос
     * @param curYear  - текущий год относительного которого происходит запрос
     * @return
     */
    private Integer getPreviousIncome(int curMonth, int curYear) {

        int calculatedMonth = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getMonthValue();
        int calculatedYear = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getYear();
        return globalBalanceMap.entrySet().stream()
                .filter(ld -> ld.getKey().getYear() == calculatedYear && ld.getKey().getMonthValue() == calculatedMonth)
                .map(Map.Entry::getValue)
                .findFirst()
                .map(FinalBalanceCalculationsRs::getBalance)
                .orElse(0);
    }

    /**
     * Вытаскиваем предыдущую цель / фин-план / расход  из соответствующего хранилища.
     *
     * @param curMonth - текущий месяц относительного которого происходит запрос
     * @param curYear  - текущий год относительного которого происходит запрос
     * @return
     */
    private Integer getPreviousExpense(int curMonth, int curYear) {
        int calculatedMonth = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getMonthValue();
        int calculatedYear = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getYear();
        return globalGoalList.stream()
                .filter(g -> dateToLocalDate(g.getStartDate()).getYear() == calculatedYear &&
                        dateToLocalDate(g.getStartDate()).getMonthValue() == calculatedMonth)
                .map(Goal::getPrice)
                .findFirst()
                .orElse(0);
    }

    /**
     * Вытаскиваем приход из Зарплаты.
     *
     * @param year      - год
     * @param currMonth - месяц
     * @return
     */
    public Integer getDefaultIncomeFromSalary(int year, int currMonth, ArNoteUser user) {
        return salaryRepo.findAllByUserAndMonthAndYear(user, year, currMonth).stream()
                .findFirst()
                .map(Salary::getFullSalary)
                .orElseGet(() -> getClosestSalary(year, currMonth, user)
                        .map(Salary::getFullSalary)
                        .orElseThrow(FinPlanningException::new));
    }

    /**
     * Получить ближайшую к дате ЗП по пользователю.
     *
     * @param year-     год.
     * @param currMonth - месяц.
     * @param user      - пользак.
     * @return
     */
    public Optional<Salary> getClosestSalary(int year, int currMonth, ArNoteUser user) {

        List<Salary> salaryListByUser = salaryRepo.getLastSalaryListByUserDesc(user);
        LocalDateTime resultTime;

        if (salaryListByUser.size() > 0) {
            try {

                LocalDateTime minTimeStamp = salaryListByUser.stream()
                        .min(Comparator.comparing(Salary::getSalaryTimeStamp))
                        .map(Salary::getSalaryTimeStamp)
                        .orElseThrow(FinPlanningException::new);

             /*   NavigableSet<LocalDateTime> dates = salaryListByUser.stream()
                        .map(Salary::getSalaryTimeStamp).distinct()
                        .collect(Collectors.toCollection((Supplier<TreeSet>) TreeSet::new));*/ //todo: Иван Тропин рассказал, что в Сэт можно прописать свой компаратор на этапе инициализации. Нам так не проще ли будет сделать?

               Set<LocalDateTime> dates = new HashSet<>();

                if ((LocalDateTime.of(year, currMonth, 1, 0, 0)).isBefore(minTimeStamp)) {
            //        resultTime = dates.ceiling(LocalDateTime.of(year, currMonth, 1, 0, 0));
                } else {
            //        resultTime = dates.floor(LocalDateTime.of(year, currMonth, 1, 0, 0));
                }

             //   if (resultTime == null) {
                    return Optional.empty();
            //    }
               /* return salaryListByUser.stream()
                        .filter(v -> v.getSalaryTimeStamp().isEqual(resultTime))
                        .findFirst();*/

            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }


    /**
     * Формируем покупки по месяцу.
     *
     * @param year
     * @param month
     * @return
     */
    private ConsolidatedPurchasesRs getPurchasePlan(int year, int month) {

        List<PurchasesRs> purchaseList = globalGoalList.stream()
                .filter(p -> ((dateToLocalDate(p.getStartDate()))
                        .getMonthValue() == month && dateToLocalDate(p.getStartDate()).getYear() == year))
                .map(t -> PurchasesRs.builder()
                        .description(t.getDescription())
                        .price(t.getPrice())
                        .id(t.getId())
                        .loanId(t.getRepayment())
                        .startDate(t.getStartDate())
                        .build())
                .collect(Collectors.toList());

        String longDescription = purchaseList.stream()
                .filter(Objects::nonNull)
                .map(PurchasesRs::getDescription)
                .collect(Collectors.joining(", "));

        Integer goalsCount = purchaseList.size();

        String shortDescription = StringUtils.left(purchaseList.stream()
                .filter(Objects::nonNull)
                .map(PurchasesRs::getDescription)
                .filter(StringUtils::isNoneBlank)
                .map(s -> s.substring(0, (s.length() <= 20 / goalsCount ? s.length() : 20 / goalsCount - 1)) + ".")
                .collect(Collectors.joining(",")), 20) + "...";

        Integer price = purchaseList.stream()
                .map(PurchasesRs::getPrice)
                .reduce(Integer::sum).orElse(0);

        return ConsolidatedPurchasesRs.builder()
                .longDescription(longDescription)
                .price(price)
                .shortDescription(shortDescription)
                .purchasePlan(purchaseList)
                .build();
    }

    /**
     * Перевести Дату в LocalDate.
     *
     * @param entityDate
     * @return
     */
    private LocalDate dateToLocalDate(Date entityDate) { // todo: в утилс

        return new Date(entityDate
                .getTime())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    /**
     * Посчитать дату выплаты самого последнего кредита.
     *
     * @param
     * @param credits
     * @return
     */
    public LocalDate getLastCreditDate(List<Credit> credits) {
        return getCalculatedLoansTable(credits).getCalculatedLoansList().stream()
                .map(v -> v.entrySet().stream()
                        .max(Map.Entry.comparingByKey())
                        .orElseThrow(FinPlanningException::new)
                        .getKey())
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());
    }

    /**
     * Посчитать последнюю дату запланированных покупок.
     *
     * @param
     * @param goalList - список планов
     * @return
     */
    public LocalDate getLastGoalsDate(List<Goal> goalList) {
        return goalList.stream()
                .max(Comparator.comparing(Goal::getStartDate))
                .map(Goal::getStartDate)
                .map(this::dateToLocalDate)
                .orElse(LocalDate.now());
    }


    /**
     * Получить и рассчитать текущие кредиты.
     *
     * @param
     * @param credits
     * @return
     */
    public CalculatedLoansTableTr getCalculatedLoansTable(List<Credit> credits) {

        List<LinkedHashMap<LocalDate, LoanListTr>> resultList = new LinkedList<>();
        LinkedHashMap<LocalDate, LoanListTr> payMap = new LinkedHashMap<>();

        credits.forEach(credit -> {
            LocalDate creditDate = new Date(credit.getStartDate()
                    .getTime())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            int paySum = credit.getStartAmount(); // сколько еще осталось платить

            int currentMonth = 0;
            while (paySum > 0) {
                paySum = paySum - credit.getRealPayPerMonth();
                LocalDate paymentDate = creditDate.withDayOfMonth(1).plusMonths(currentMonth);

                /**
                 * Ищем досрочные "погашалки" кредита
                 */
                Map<Long, Map<LocalDate, Integer>> creditWithRepaymentMap =
                        globalGoalList
                                .stream()
                                .filter(r -> r.getRepayment() != null &&
                                        dateToLocalDate(r.getStartDate()).getYear() == paymentDate.getYear() &&
                                        dateToLocalDate(r.getStartDate()).getMonthValue() == paymentDate.getMonthValue()
                                )
                                .findFirst()
                                .map(c -> {
                                            Map<Long, Map<LocalDate, Integer>> repaymentMap = new HashMap<>();
                                            Map<LocalDate, Integer> dateAndRepayment = new HashMap<>();
                                            dateAndRepayment.put(dateToLocalDate(c.getStartDate()), c.getPrice());
                                            repaymentMap.put(c.getRepayment(), dateAndRepayment);
                                            return repaymentMap;
                                        }
                                )
                                .orElse(Collections.emptyMap());

                if (!creditWithRepaymentMap.isEmpty()) {
                    Integer repaymentByDateAndLoanId = creditWithRepaymentMap.entrySet().stream()
                            .filter(z -> creditRepo
                                    .findById(z.getKey())
                                    .orElseThrow(FinPlanningException::new) != null)
                            .findFirst()
                            .map(Map.Entry::getValue)
                            .map(b -> {

                                LocalDate tempDate = b.entrySet().stream().findFirst()
                                        .orElseThrow(FinPlanningException::new).getKey();
                                Integer repayment = b.entrySet().stream().findFirst()
                                        .orElseThrow(FinPlanningException::new).getValue();

                                if (tempDate.getYear() == paymentDate.getYear() &&
                                        tempDate.getMonthValue() == paymentDate.getMonthValue()) {
                                    return repayment;
                                } else {
                                    return 0;
                                }
                            }).orElse(0);
                    paySum = paySum - repaymentByDateAndLoanId;
                }

                if (paySum < 0) {
                    paySum = 0;
                }


                if (payMap.entrySet().stream()
                        .filter(r -> r.getKey().isEqual(paymentDate)).findFirst().isPresent()) {

                    LoanListTr localLoanList = payMap.entrySet().stream()
                            .filter(r -> r.getKey().isEqual(paymentDate))
                            .findFirst()
                            .get()
                            .getValue();

                    if (localLoanList.getLoanList() == null) {
                        System.out.println("fuck");
                    }

                    localLoanList.getLoanList().add(LoanTr.builder()
                            .amount(paySum)
                            .loanId(credit.getId())
                            //  .fullPayPerMonth(credit.getFullPayPerMonth())
                            // .creditNumber(credit.getCreditNumber())
                            //  .description(credit.getDescription())
                            //  .realPayPerMonth(credit.getRealPayPerMonth())
                            //  .startDate(credit.getStartDate())
                            .build());

                } else {

                    payMap.put(paymentDate, LoanListTr.builder()
                            .loanList(new ArrayList<>(Arrays.asList(LoanTr.builder()
                                    .amount(paySum)
                                    .loanId(credit.getId())
                                    .build())))
                            .build());
                }

                currentMonth++;

                /*
                 * Проверка, если кто-то запихнул какие-то запредельные значения, чтобы не попали в вечный цикл.
                 */
                if (currentMonth > 120) {
                    paySum = 0;
                }
            }
        });

        resultList.add(payMap);
        return CalculatedLoansTableTr.builder()
                .calculatedLoansList(resultList)
                .build();
    }


    /**
     * Получить и рассчитать текущие кредиты отфильтрованные по текущей дате.
     *
     * @param
     * @param credits
     * @return
     */
    public CreditListRs getCreditsFiltered(List<Credit> credits, Integer year, Integer month) {

        List<CreditRs> creditList = getCalculatedLoansTable(credits).getCalculatedLoansList().stream()
                .map(pm -> filterMap(pm, year, month))
                .findFirst()
                .orElse(null);

        return CreditListRs.builder()
                .credit1(getCreditByNumber(creditList, 1))
                .credit2(getCreditByNumber(creditList, 2))
                .credit3(getCreditByNumber(creditList, 3))
                .credit4(getCreditByNumber(creditList, 4))
                .credit5(getCreditByNumber(creditList, 5))
                .credits(creditList)
                .build();
    }

    public Integer getCreditByNumber(List<CreditRs> lt, Integer nm) {
        return lt != null ? listOfCreditsToMap(lt).get(CreditDict.getValByNumber(nm)) : null;
    }

    private Map<CreditDict, Integer> listOfCreditsToMap(List<CreditRs> creditList) {
        Map<CreditDict, Integer> creditsMap = new HashMap<>();
        creditList.forEach(v -> creditsMap.put(CreditDict.getValByNumber(v.getNumber()), v.getAmount()));
        return creditsMap;
    }

    public List<CreditRs> filterMap(LinkedHashMap<LocalDate, LoanListTr> payMap, Integer year, Integer month) { //todo: переписать через стрим
        List<CreditRs> credits = new ArrayList<>();
        payMap.forEach((k, v) -> {
            if (k.getYear() == year && k.getMonthValue() == month) {
                for (LoanTr loan : v.getLoanList()) {
                    Credit lnFound = creditRepo.findById(loan.getLoanId())
                            .orElseThrow(FinPlanningException::new);

                    credits.add(CreditRs.builder()
                            .amount(loan.getAmount())
                            .description(lnFound.getDescription())
                            .id(loan.getLoanId())
                            .number(lnFound.getCreditNumber())
                            .realPayPerMonth(lnFound.getRealPayPerMonth())
                            .fullPayPerMonth(lnFound.getFullPayPerMonth())
                            .startDate(lnFound.getStartDate())
                            .build());
                }
            }
        });
        return credits.stream()
                .sorted(Comparator.comparing(CreditRs::getNumber))
                .collect(Collectors.toList());
    }

    /**
     * Добавить кредит.
     *
     * @param principal
     * @param request
     * @return
     */
    @Override
    public AddCreditRs addCredit(Principal principal, CreditRq request) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        int nextLoanNumber;
        try {
            if (!getAllCredits(arNoteUser).isEmpty()) {
                if (getAllCredits(arNoteUser).stream()
                        .max(Comparator.comparing(Credit::getCreditNumber))
                        .orElseThrow(FinPlanningException::new).getCreditNumber() >= 5) {
                    throw new AddNewCreditException();
                }

                int savedLoanNumber = getAllCredits(arNoteUser).stream()
                        .max(Comparator.comparing(Credit::getCreditNumber))
                        .map(Credit::getCreditNumber).orElse(0);

                nextLoanNumber = savedLoanNumber == 0 ? 1 : savedLoanNumber + 1;
            } else {
                nextLoanNumber = 1;
            }
            creditRepo.save(Credit.$toDbEntity(request, nextLoanNumber, arNoteUser));
            return AddCreditRs.builder()
                    .creditNumber(nextLoanNumber)
                    .creditsCount(getAllCredits(arNoteUser).size())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();
        } catch (AddNewCreditException e) {
            return AddCreditRs.builder()
                    .creditsCount(getAllCredits(arNoteUser).size())
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O4.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O4.getDescription())
                            .build())
                    .build();
        }
    }

    /**
     * Удалить кредит.
     *
     * @param principal
     * @param id
     * @return
     */
    @Override
    public AddCreditRs deleteLoan(Principal principal, Long id) {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Optional<Credit> loan = creditRepo.findCreditByUserAndId(arNoteUser, id);
            if (loan.isPresent()) {
                List<Goal> goalsList = purchaseRepo.findAllByRepaymentAndUser(loan.get().getId(), arNoteUser);
                if (goalsList.size()>0) {
                   purchaseRepo.deleteInBatch(goalsList);
                }
            }
            loan.ifPresent(creditRepo::delete);

            return AddCreditRs.builder()
                    .creditsCount(getAllCredits(arNoteUser).size())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();

        } catch (Exception e) {
            return AddCreditRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    @Override
    public FullLoansListRs getFullLoansList(Principal principal) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        return FullLoansListRs.builder()
                .loansList(getAllCredits(arNoteUser).stream()
                        .map(c -> FullLoanRs.builder()
                                .id(c.getId())
                                .description(c.getDescription())
                                .fullPayPerMonth(c.getFullPayPerMonth())
                                .realPayPerMonth(c.getRealPayPerMonth())
                                .startDate(c.getStartDate())
                                .startAmount(c.getStartAmount())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Редактировать кредит.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public AddCreditRs editLoan(CreditRq payload, Principal principal) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        Optional<Credit> loan = creditRepo.findCreditByUserAndId(arNoteUser, payload.getId());
        loan.ifPresent((a) -> creditRepo.save(Credit.$toDbEntityWithCheck(payload, a, arNoteUser)));
        return AddCreditRs.builder()
                .creditsCount(getAllCredits(arNoteUser).size())
                .status(ResponseStatusRs.builder()
                        .code(200)
                        .status("SUCCESS")
                        .build())
                .build();
    }

    /**
     * Добавить доход.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public SingleOperationRs addIncome(IncomeRq payload, Principal principal) throws UserNotFoundException {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Income newIncome = incomeRepo.saveAndFlush(Income.$toDbEntity(payload, arNoteUser));
            return SingleOperationRs.builder()
                    .id(newIncome.getId())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();

        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    /**
     * Удалить доход.
     *
     * @param principal - юзер
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public SingleOperationRs deleteIncome(Principal principal, IncomesForDeleteRq req) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        req.getIdList().forEach(t -> incomeRepo.findIncomeByUserAndId(arNoteUser, t.getId())
                .ifPresent(income -> incomeRepo.delete(income)));

        return SingleOperationRs.builder()
                .status(ResponseStatusRs.builder()
                        .code(200)
                        .status("SUCCESS")
                        .build())
                .build();
    }

    /**
     * Редактировать доход.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public SingleOperationRs editIncome(IncomeRq payload, Principal principal) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        try {
            int year = dateToLocalDate(payload.getIncomeDate()).getYear();
            int month = dateToLocalDate(payload.getIncomeDate()).getMonthValue();
            Optional<Income> existIncome = incomeRepo.findIncomeByUserAndMonthAndYear(arNoteUser, year, month).stream()
                    .findFirst();
            existIncome.ifPresent(income -> incomeRepo.save(Income.$toDbEntity(payload, arNoteUser)));
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();
        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    /**
     * Получить кредит по ID.
     *
     * @param id
     * @param principal
     * @return
     */
    @Override
    public CreditRs getLoanById(Long id, Principal principal) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        return creditRepo.findCreditByUserAndId(arNoteUser, id).map(v -> CreditRs.builder()
                .id(v.getId())
                .description(v.getDescription())
                .amount(v.getStartAmount())
                .number(v.getCreditNumber())
                .startDate(v.getStartDate())
                .fullPayPerMonth(v.getFullPayPerMonth())
                .realPayPerMonth(v.getRealPayPerMonth())
                .build())
                .orElseThrow(FinPlanningException::new);
    }

    /**
     * Получить детализированный расчет баланса за месяц.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public FinalBalanceCalculationsRs getRemainsDetailInfo(GetRemainsDetailInfoRq payload, Principal principal) throws UserNotFoundException {

        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (globalBalanceMap.isEmpty()) {
            curYear = Calendar.getInstance().get(Calendar.YEAR);
            int finalCalculatedYear = getFinalYear(arNoteUser);
            int yearsCount = (startYear == null || startYear < 2000) ?
                    curYear - 2019 + 1 :
                    (finalCalculatedYear) - startYear + 1;

            if (startMonth == null || startMonth < 1 || startMonth > 12 || yearsCount == 1) {
                startMonth = 1;
            }
            calculateFullRemains(arNoteUser, startMonth, yearsCount, finalCalculatedYear);
        }

        Optional<Freeze> currentFreeze = freezeRepo.findFreezeByUserAndMonthAndYear(arNoteUser, payload.getYear(), payload.getMonth());

        return globalBalanceMap.entrySet().stream()
                .filter(v -> v.getKey().getYear() == payload.getYear() && v.getKey().getMonthValue() == payload.getMonth())
                .peek(t -> t.getValue().setDate(payload.getYear() + " " + getMonthByNumber(payload.getMonth())))
                .peek(d -> d.getValue().setDateInDateFormat(localDateToDate(LocalDate.of(payload.getYear(),
                        payload.getMonth(),
                        1))))
                .peek(f -> f.getValue().setFreeze(currentFreeze.isPresent()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(FinalBalanceCalculationsRs.builder().build());
    }

    @Override
    public SingleOperationRs addGoal(GoalRq payload, Principal principal) throws UserNotFoundException {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Goal newGoal = purchaseRepo.saveAndFlush(Goal.$toDbEntity(payload, arNoteUser));
            return SingleOperationRs.builder()
                    .id(newGoal.getId())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();

        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    /**
     * Получить кредит по дате и пользаку.
     *
     * @param payload
     * @param principal
     * @return
     */
    @Override
    public FullLoansListRs getLoanByDate(LoanByDateRq payload, Principal principal) { // todo: еще раз - все ответы в перспективе заворачиваем в один враппер со статусом. Единая обработка хендлером по всему приложению
        try {

            return FullLoansListRs.builder()
                    .loansList((globalConsolidatedTable.getFinPlans().stream()
                            .filter(f -> f.getYear() == dateToLocalDate(payload.getStartDate()).getYear() &&
                                    f.getMonthNumber() == dateToLocalDate(payload.getStartDate()).getMonthValue())
                            .map(r -> r.getCredits().getCredits()).findFirst().get()).stream()//todo: поправить потом этот гет
                            .map(FullLoanRs::$fromCredit)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return FullLoansListRs.builder()
                    .loansList(Collections.emptyList())
                    .build();
        }

    }


    /**
     * Редактировать цель.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public SingleOperationRs editGoal(GoalRq payload, Principal principal) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        try {
            Optional<Goal> existGoal = purchaseRepo.findGoalByIdAndUser(payload.getId(), arNoteUser);
            existGoal.ifPresent(goal -> purchaseRepo.save(Goal.$toDbEntityWithCheck(payload, goal, arNoteUser)));
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();
        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    @Override
    public AddCreditRs deleteGoal(Principal principal, Long id) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        Optional<Goal> loan = purchaseRepo.findGoalByIdAndUser(id, arNoteUser);
        loan.ifPresent(purchaseRepo::delete);

        return AddCreditRs.builder()
                .creditsCount(getAllCredits(arNoteUser).size())
                .status(ResponseStatusRs.builder()
                        .code(200)
                        .status("SUCCESS")
                        .build())
                .build();
    }

    /**
     * Получить список зарплат по пользаку.
     *
     * @param principal
     * @return
     */
    @Override
    public SalaryListRs getSalariesList(Principal principal) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);

        try {
            return SalaryListRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .salariesList(salaryRepo.getLastSalaryListByUserDesc(arNoteUser).stream()
                            .map(s -> SalaryRs.builder()
                                    .fullSalary(s.getFullSalary())
                                    .id(s.getId())
                                    .livingExpenses(s.getLivingExpenses())
                                    .residualSalary(s.getResidualSalary())
                                    .salaryDate(localDateToDate(s.getSalaryTimeStamp().toLocalDate()))
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return SalaryListRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O2.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O2.getDescription())
                            .build())
                    .build();
        }
    }

    @Override
    public SingleOperationRs editSalary(Principal principal, SalaryRq payload) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        try {
            Optional<Salary> existSalary = salaryRepo.findSalaryById(payload.getId());
            existSalary.ifPresent(salary -> salaryRepo.save(Salary.$toDbEntityWithCheck(payload, salary, arNoteUser)));
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();
        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    @Override
    public SingleOperationRs addSalary(Principal principal, SalaryRq payload) {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Salary newSalary = salaryRepo.saveAndFlush(Salary.$toDbEntity(payload, arNoteUser));
            return SingleOperationRs.builder()
                    .id(newSalary.getId())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();

        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    /**
     * Удалить ЗП по id.
     *
     * @param principal
     * @param id
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public SingleOperationRs deleteSalary(Principal principal, Long id) {

        try {
            Optional<Salary> salary = salaryRepo.findSalaryById(id);
            salary.ifPresent(salaryRepo::delete);

            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();
        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    /**
     * Добавить фриз.
     *
     * @param principal
     * @param request
     * @return
     */
    @Override
    public SingleOperationRs addFreeze(Principal principal, FreezeRq request) {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Optional<Freeze> currentFreeze = freezeRepo.findFreezeByUserAndMonthAndYear(arNoteUser, request.getYear(), request.getMonth());
            if (currentFreeze.isPresent()) {
                throw new RuntimeException(); //todo: а вообще тут надо пробросить свой эксепшн что типа такой фриз уже есть
            }
            Freeze newFreeze = freezeRepo.saveAndFlush(Freeze.$toDbEntity(request, arNoteUser));
            return SingleOperationRs.builder()
                    .id(newFreeze.getId())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();

        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }

    @Override
    public SingleOperationRs deleteFreeze(Principal principal, Long year, Long month) {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Optional<Freeze> freeze = freezeRepo.findFreezeByUserAndMonthAndYear(arNoteUser, year.intValue(), month.intValue());
            freeze.ifPresent(freezeRepo::delete);

            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();
        } catch (Exception e) {
            return SingleOperationRs.builder()
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O6.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O6.getDescription())
                            .build())
                    .build();
        }
    }
}
