package com.antonromanov.arnote.old.services;

import com.antonromanov.arnote.old.exceptions.UserNotFoundException;


/*@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)*/
public class FinPlanServiceImp /*implements FinPlanService*/ { //todo: класс больше 1000 строк! Разделить!

    /*@Autowired
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

    AtomicInteger atomicInt = new AtomicInteger(0);


    // =================== БАЗОВЫЕ ГЛОБАЛЬНЫЕ КОНСТАНТЫ ======================
    private int curYear; // текущий год
    LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> globalBalanceMap = new LinkedHashMap<>(); // глобальная мапа остатков
    LinkedHashMap<Long, ClosedLoanTr> globalMapOfClosedLoans = new LinkedHashMap<>(); // глобальная мапа выплаченых кредитов
    List<Goal> globalGoalList = new ArrayList<>(); // глобальная мапа расходов
    FinPlanListRs globalConsolidatedTable; // глобальная консолидированная мапа чтобы каждый раз не ходить в БД*/
    // =================== ============================ ======================


    /**
     * Подготовить диапазон лет по которым ходим в цикле для заполнения массивов.
     *
     * @param yearsCount - количество лет.
     * @return коллекция с Integer
     */
   /* public List<Integer> getYearsRange(int yearsCount) {
        return IntStream.rangeClosed(1, yearsCount)
                .boxed()
                .collect(Collectors.toList());
    }
*/

    /**
     * Запросить консолидированную таблицу из кэша.
     *
     * @param principal - юзер.
     * @return
     * @throws UserNotFoundException
     */
  /*  @Override
    public FinPlanListRs getFinPlanTableFromCache(Principal principal) throws UserNotFoundException {
        if (globalConsolidatedTable == null) {
            return getFinPlanTableFromDb(principal);
        } else {
            return globalConsolidatedTable;
        }
    }*/

    /**
     * Подсчитать кол-во лет для циклинга по годам.
     *
     * @return
     */
  /*  private int calculateYearsCount(ArNoteUser arNoteUser) {
        curYear = Calendar.getInstance().get(Calendar.YEAR);
        return (startYear == null || startYear < 2000) ?
                curYear - 2019 + 1 :
                (getFinalYear(arNoteUser)) - startYear + 1;
    }
*/
    /**
     * Запросить консолидированную таблицу из БД.
     *
     * @param principal - юзер.
     * @return
     * @throws UserNotFoundException
     */
  /*  @Override
    public FinPlanListRs getFinPlanTableFromDb(Principal principal) throws UserNotFoundException {
        log.info("[SRV] Gettin Consolidated Table From DB...");
        curYear = Calendar.getInstance().get(Calendar.YEAR);
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        globalGoalList = purchaseRepo.findAllByUser(arNoteUser);
        int finalCalculatedYear = getFinalYear(arNoteUser);
        log.info("[SRV] Calculate Loans Table...");
        CalculatedLoansTableTr calculatedLoansTable = getCalculatedLoansTable(getAllCredits(arNoteUser)); // todo: ** 1 **
        log.info("[SRV] Calculate Loans Table - DONE");
        int yearsCount = calculateYearsCount(arNoteUser);
        if (startMonth == null || startMonth < 1 || startMonth > 12 || yearsCount == 1) {
            startMonth = 1;
        }

        List<FinPlanRs> finalList = new ArrayList<>();
        calculateFullRemains(arNoteUser, startMonth, yearsCount, finalCalculatedYear, calculatedLoansTable); // todo: ** 2 ( => 1) **
        log.info("[SRV] Calculate Full Remains Table - DONE");
        getYearsRange(yearsCount).forEach(y -> {

            int startPoint = 1;
            if (y == 1) {
                startPoint = startMonth;
            }
            for (int currMonth = startPoint; currMonth <= 12; currMonth++) {
                int localYear = (finalCalculatedYear) - yearsCount + y;
                CreditListRs credits = getCreditsFiltered(calculatedLoansTable.getCalculatedLoansList(), localYear, currMonth);
                finalList.add(FinPlanRs.builder()
                        .month(getMonthByNumber(currMonth))
                        .monthNumber(currMonth)
                        .year(localYear)
                        .credits(credits)
                        .allCredits(credits.getCredits().stream().map(CreditRs::getAmount).reduce(Integer::sum).orElse(0))
                        .purchasePlan(getPurchasePlan(localYear, currMonth))
                        .remains(prepareFinalBalance(localYear, currMonth))
                        .freeze(isThisFreeze(localYear, currMonth, arNoteUser))
                        .color(getRowBackGroundColor(localYear, currMonth))
                        .fontColor(getFontColor(localYear, currMonth, getPurchasePlan(localYear, currMonth)
                                .getPurchasePlan()
                                .stream()
                                .anyMatch(p -> p.getLoanId() != null)))
                        .borderWidth(currMonth==12? "3px double #8B0000" : "1px solid grey")
                        .build());
            }
            log.info("Year {} ", (finalCalculatedYear) - yearsCount + y);
        });
        globalConsolidatedTable = FinPlanListRs.builder().finPlans(finalList).build();
        return globalConsolidatedTable;
    }*/

    /**
     * Рассчитать цвет шрифта.
     *
     * @param currentYear
     * @param currMonth
     * @param isRepayment
     * @return
     */
  /*  private String getFontColor(int currentYear, int currMonth, boolean isRepayment) {
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
    }*/

    /**
     * Определить цвет ряда.
     *
     * @param currentYear
     * @param currMonth
     * @return
     */
   /* private String getRowBackGroundColor(int currentYear, int currMonth) {
        if (LocalDate.now().withDayOfMonth(1).isAfter(LocalDate.of(currentYear, currMonth, 1))) { // дата обрабатываемого месяца раньше чем текущая
            return "#6c796c";
        } else if (LocalDate.now().withDayOfMonth(1).isBefore(LocalDate.of(currentYear, currMonth, 1))) { // дата обрабатываемого месяца позже чем текущая
            return "#9abacd";
        } else { // даты равны
            return "#f5e7c8";
        }
    }*/

  /*  private Integer prepareFinalBalance(int year, int month) {
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
    }*/

    /**
     * Достать все кредиты
     *
     * @return
     */
 /*   private List<Credit> getAllCredits(ArNoteUser user) {
        return creditRepo.getCreditsByUser(user);
    }*/

    /**
     * Высчитываем верхнюю границу периода (год) отображения консолидированной таблицы.
     *
     * @return
     */
   /* private Integer getFinalYear(ArNoteUser user) {
        Integer yearFromApplicationProperties = curYear + finalYear;
        if (getLastCreditDate(getAllCredits(user)).isPresent()) {
            LocalDate creditDate = getLastCreditDate(getAllCredits(user)).get();
            LocalDate lastGoalsDate = getLastGoalsDate(globalGoalList);
            return Stream.of(creditDate.getYear(), lastGoalsDate.getYear(), yearFromApplicationProperties)
                    .max(Integer::compareTo)
                    .orElse(yearFromApplicationProperties);
        } else {
            return yearFromApplicationProperties;
        }
    }*/


    /**
     * Отфильтровать список фризов по году и месяцу.
     *
     * @param year
     * @param month
     * @return
     */
 /*   private Optional<Freeze> filterFreezeListByDate(List<Freeze> allFreezesByUser, int year, int month) {
        return allFreezesByUser.stream().filter(e -> dateToLocalDate(e.getStartDate()).withDayOfMonth(1)
                .isEqual(LocalDate.of(year, month, 1))).findFirst();
    }*/


    /**
     * Считаем остаток по счетам. Расклад по всем месяцам. Заполняем глобальную мапу приходов.
     *
     * @param user                - пользак.
     * @param startMonth          - начальный месяц (из конфигов).
     * @param yearsCount
     * @param finalCalculatedYear
     * @throws UserNotFoundException
     */
  /*  private void calculateFullRemains(ArNoteUser user, int startMonth, int yearsCount, int finalCalculatedYear,
                                      CalculatedLoansTableTr calculatedLoansTable) {

        globalBalanceMap.clear();
        List<Income> allIncomesByUser = incomeRepo.findAllByUserOrderByIncomeDateAsc(user); // все доходы юзера
        List<Salary> salaryListByUser = salaryRepo.getLastSalaryListByUserDesc(user);
        List<Freeze> allFreezesByUser = freezeRepo.findAllByUser(user);
        getYearsRange(yearsCount).forEach(y -> {

            int startPoint = 1;
            if (y == 1) {
                startPoint = startMonth;
            }

            for (int currMonth = startPoint; currMonth <= 12; currMonth++) {
                Optional<Salary> currentSalaryByDate = getClosestSalary(salaryListByUser, (finalCalculatedYear - yearsCount + y), currMonth);
                int monthlySpending = (currentSalaryByDate)
                        .map(Salary::getLivingExpenses)
                        .orElse(0); // средние ежемесячные расходы

                int finalCurrMonth = currMonth;
                Optional<Freeze> currentFreeze = filterFreezeListByDate(allFreezesByUser, (finalCalculatedYear - yearsCount + y),
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

                Integer prevIncome = getPreviousIncome(currMonth, (finalCalculatedYear - yearsCount + y));
                Integer prevExpense = getPreviousExpense(currMonth, (finalCalculatedYear - yearsCount + y));
                Integer loanPaymentsByDate = getLoanPaymentsByDate(calculatedLoansTable.getCalculatedLoansList(),
                        currMonth, (finalCalculatedYear - yearsCount + y));

                int calculatedRemains = prevIncome - // предыдущий доход
                        prevExpense - // минус предыдущий расход
                        monthlySpending - // минус среднемесячный расход
                        loanPaymentsByDate + // минус покрытие кредитов
                        currentIncome + // + ежемесячный доход
                        currentSalaryByDate.map(Salary::getFullSalary).orElse(0); // + зарплата

                if (globalBalanceMap.isEmpty()) {
                    globalBalanceMap.put(LocalDate.of((finalCalculatedYear - yearsCount + y), currMonth, 1),
                            FinalBalanceCalculationsRs.builder()
                                    .balance(currentFreeze.map(Freeze::getAmount).orElse(calculatedRemains))
                                    .currentIncome(currentIncome + currentSalaryByDate.map(Salary::getFullSalary).orElse(0))
                                    .loanPayments(loanPaymentsByDate)
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
                                    .previousExpense(prevExpense)
                                    .previousIncome(prevIncome)
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
                                    .currentIncome(currentIncome + currentSalaryByDate.map(Salary::getFullSalary).orElse(0))
                                    .loanPayments(loanPaymentsByDate)
                                    .monthlySpending(monthlySpending)
                                    .previousExpense(prevExpense)
                                    .previousIncome(prevIncome)
                                    .build());
                }
            }
        });
    }*/

    /**
     * Платежи по кредитам по состоянию на конкретную дату.
     *
     * @param curMonth
     * @param curYear
     * @return
     */
   /* private Integer getLoanPaymentsByDate(List<LinkedHashMap<LocalDate, LoanListTr>> calculatedLoansList, int curMonth,
                                          int curYear) {

        List<Integer> resultSum = new ArrayList<>();

        for (LinkedHashMap<LocalDate, LoanListTr> map : calculatedLoansList) {

            Optional<LoanListTr> loan = map.entrySet().stream()
                    .filter(d -> d.getKey().getYear() == curYear && d.getKey().getMonthValue() == curMonth)
                    .map(Map.Entry::getValue)
                    .findFirst();

            loan.ifPresent(loanListTr -> resultSum.add(loanListTr.getLoanList().stream().map(v -> creditRepo.findById(v.getLoanId())
                    .orElseThrow(RuntimeException::new))
                    .map(Credit::getFullPayPerMonth)
                    .reduce(Integer::sum).orElse(0)));
        }

        return resultSum.stream().reduce(Integer::sum).orElse(0);

    }*/

    /**
     * Вытаскиваем предыдущий приход из мапы приходов.
     *
     * @param curMonth - текущий месяц относительного которого происходит запрос
     * @param curYear  - текущий год относительного которого происходит запрос
     * @return
     */
 /*   private Integer getPreviousIncome(int curMonth, int curYear) {

        int calculatedMonth = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getMonthValue();
        int calculatedYear = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getYear();
        return globalBalanceMap.entrySet().stream()
                .filter(ld -> ld.getKey().getYear() == calculatedYear && ld.getKey().getMonthValue() == calculatedMonth)
                .map(Map.Entry::getValue)
                .findFirst()
                .map(FinalBalanceCalculationsRs::getBalance)
                .orElse(0);
    }*/

    /**
     * Вытаскиваем предыдущую цель / фин-план / расход  из соответствующего хранилища.
     *
     * @param curMonth - текущий месяц относительного которого происходит запрос
     * @param curYear  - текущий год относительного которого происходит запрос
     * @return
     */
   /* private Integer getPreviousExpense(int curMonth, int curYear) {
        int calculatedMonth = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getMonthValue();
        int calculatedYear = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getYear();
        return globalGoalList.stream()
                .filter(g -> dateToLocalDate(g.getStartDate()).getYear() == calculatedYear &&
                        dateToLocalDate(g.getStartDate()).getMonthValue() == calculatedMonth)
                .map(Goal::getPrice)
                .reduce(Integer::sum)
                .orElse(0);
    }*/

    /**
     * Вытаскиваем приход из Зарплаты.
     *
     * @param year      - год
     * @param currMonth - месяц
     * @return
     */
   /* public Integer getDefaultIncomeFromSalary(int year, int currMonth, ArNoteUser user) {
        List<Salary> salaryListByUser = salaryRepo.getLastSalaryListByUserDesc(user);
        return salaryRepo.findAllByUserAndMonthAndYear(user, year, currMonth).stream()
                .findFirst()
                .map(Salary::getFullSalary)
                .orElseGet(() -> getClosestSalary(salaryListByUser, year, currMonth)
                        .map(Salary::getFullSalary)
                        .orElseThrow(FinPlanningException::new));
    }*/

    /**
     * Список в NavigableSet.
     *
     * @param list
     * @param <T>
     * @return
     */
  /*  public static <T> NavigableSet<T> convertToSet(List<T> list) {
        return new TreeSet<>(list);
    }*/

    /**
     * Получить ближайшую к дате ЗП по пользователю.
     *
     * @param year-     год.
     * @param currMonth - месяц.
     * @param
     * @return
     */
  /*  public Optional<Salary> getClosestSalary(List<Salary> salaryListByUser, int year, int currMonth) { //todo: СРОЧНО ПЕРЕПИСАТЬ!

        LocalDateTime resultTime;

        if (salaryListByUser.size() > 0) {
            try {

                LocalDateTime minTimeStamp = salaryListByUser.stream()
                        .min(Comparator.comparing(Salary::getSalaryTimeStamp))
                        .map(Salary::getSalaryTimeStamp)
                        .orElseThrow(FinPlanningException::new);

                NavigableSet<LocalDateTime> dates = convertToSet(salaryListByUser.stream()
                        .map(Salary::getSalaryTimeStamp).collect(Collectors.toList()));

                if ((LocalDateTime.of(year, currMonth, 1, 0, 0)).isBefore(minTimeStamp)) {
                    resultTime = dates.ceiling(LocalDateTime.of(year, currMonth, 1, 0, 0));
                } else {
                    resultTime = dates.floor(LocalDateTime.of(year, currMonth, 1, 0, 0));
                }

                if (resultTime == null) {
                    return Optional.empty();
                }
                return salaryListByUser.stream()
                        .filter(v -> v.getSalaryTimeStamp().isEqual(resultTime))
                        .findFirst();

            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }*/


    /**
     * Формируем покупки по месяцу.
     *
     * @param year
     * @param month
     * @return
     */
   /* private ConsolidatedPurchasesRs getPurchasePlan(int year, int month) {
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

        int goalsCount = purchaseList.size();

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
*/

    /**
     * Посчитать дату выплаты самого последнего кредита.
     *
     * @param
     * @param credits
     * @return
     */
  /*  public Optional<LocalDate> getLastCreditDate(List<Credit> credits) {
        try {
            return getCalculatedLoansTable(credits).getCalculatedLoansList().stream()
                    .map(v -> v.entrySet().stream()
                            .max(Map.Entry.comparingByKey())
                            // .map(r->r.getKey())).max(rr->rr.m)
                            .orElseThrow(FinPlanningException::new)
                            .getKey())
                    .max(LocalDate::compareTo);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }*/

    /**
     * Посчитать последнюю дату запланированных покупок.
     *
     * @param
     * @param goalList - список планов
     * @return
     */
   /* public LocalDate getLastGoalsDate(List<Goal> goalList) {
        return goalList.stream()
                .max(Comparator.comparing(Goal::getStartDate))
                .map(Goal::getStartDate)
                .map(this::dateToLocalDate)
                .orElse(LocalDate.now());
    }*/


    /**
     * Получить и рассчитать текущие кредиты.
     *
     * @param
     * @param credits
     * @return
     */
   /* public CalculatedLoansTableTr getCalculatedLoansTable(List<Credit> credits) {

        List<LinkedHashMap<LocalDate, LoanListTr>> resultList = new LinkedList<>(); // конечный ответ: список мап. Каждая мапа дата + данные по кредиту
        LinkedHashMap<LocalDate, LoanListTr> payMap = new LinkedHashMap<>(); // сама мапа - дата + данные по кредиту.

        *//**
         * Бегаем по всем переданным кредитам.
         *//*
        credits.forEach(credit -> {
            LocalDate creditDate = new Date(credit.getStartDate()
                    .getTime())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(); // тащим стартовую дату кредита и конвертим в LocalDate

            int paySum = credit.getStartAmount(); // сколько еще денег осталось платить

            int currentMonth = 0;
            while (paySum > 0) {
                paySum = paySum - credit.getRealPayPerMonth();
                LocalDate paymentDate = creditDate.withDayOfMonth(1).plusMonths(currentMonth);*/

                /**
                 * Ищем досрочные "погашалки" кредита
                 */
         /*       Map<Long, Map<LocalDate, Integer>> creditWithRepaymentMap =
                        globalGoalList
                                .stream()
                                .filter(r -> r.getRepayment() != null &&
                                        dateToLocalDate(r.getStartDate()).getYear() == paymentDate.getYear() &&
                                        dateToLocalDate(r.getStartDate()).getMonthValue() == paymentDate.getMonthValue()
                                )
                                .map(c -> {
                                            Map<Long, Map<LocalDate, Integer>> repaymentMap = new HashMap<>();
                                            Map<LocalDate, Integer> dateAndRepayment = new HashMap<>();
                                            dateAndRepayment.put(dateToLocalDate(c.getStartDate()), c.getPrice());
                                            repaymentMap.put(c.getRepayment(), dateAndRepayment);
                                            return repaymentMap;
                                        }
                                ).flatMap(m -> m.entrySet().stream())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));

                if (!creditWithRepaymentMap.isEmpty()) {
                    Integer repaymentByDateAndLoanId = creditWithRepaymentMap.entrySet().stream()
                            .filter(z -> creditRepo
                                    .findById(z.getKey())
                                    .orElseThrow(FinPlanningException::new) != null &&
                                    credit.getId().equals(z.getKey()))

                            .findFirst()
                            .map(Map.Entry::getValue)
                            .map(b -> {

                                LocalDate tempDate = b.entrySet().stream().findFirst()
                                        .orElseThrow(FinPlanningException::new).getKey();
                                Integer repayment = b.entrySet().stream().findFirst()
                                        .orElseThrow(FinPlanningException::new).getValue();

                                return tempDate.getYear() == paymentDate.getYear() &&
                                        tempDate.getMonthValue() == paymentDate.getMonthValue() ? repayment : 0;

                            })
                            //.reduce(Integer::sum)
                            .orElse(0);
                    paySum = paySum - repaymentByDateAndLoanId;
                }

                if (paySum < 0) {
                    paySum = 0;
                }

                if (paySum == 0) {
                    globalMapOfClosedLoans.put(credit.getId(), ClosedLoanTr.builder()
                            .startDate(dateToLocalDate(credit.getStartDate()))
                            .closeDate(paymentDate)
                            .loanNumber(credit.getCreditNumber())
                            .build());
                }


                if (payMap.entrySet().stream().anyMatch(r -> r.getKey().isEqual(paymentDate))) {

                    LoanListTr localLoanList = payMap.entrySet().stream()
                            .filter(r -> r.getKey().isEqual(paymentDate))
                            .findFirst()
                            .get() // todo: почему просто get? Без обработки! Переделать!
                            .getValue();

                    localLoanList.getLoanList().add(LoanTr.builder()
                            .amount(paySum)
                            .loanId(credit.getId())
                            .build());

                } else {

                    payMap.put(paymentDate, LoanListTr.builder()
                            .loanList(new ArrayList<>(Arrays.asList(LoanTr.builder()
                                    .amount(paySum)
                                    .loanId(credit.getId())
                                    .build())))
                            .build());
                }

                currentMonth++;*/

                /*
                 * Проверка, если кто-то запихнул какие-то запредельные значения, чтобы не попали в вечный цикл.
                 */
          /*      if (currentMonth > 120) {
                    paySum = 0;
                }
            }
        });

        resultList.add(payMap);
        return CalculatedLoansTableTr.builder()
                .calculatedLoansList(resultList)
                .build();
    }*/


    /**
     * Получить и рассчитать текущие кредиты отфильтрованные по текущей дате.
     *
     * @param
     * @return
     */
   /* public CreditListRs getCreditsFiltered(List<LinkedHashMap<LocalDate, LoanListTr>> calculatedLoansList, Integer year, Integer month) {

        List<CreditRs> creditList = calculatedLoansList.stream()
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
    }*/

    /**
     * Проверяем, что можно добавить новый кредит и если можно - возвращаем новый номер.
     * Если "-1" - значит какая-то ошибка или добавить нельзя.
     *
     * @return
     */
   /* public int checkForNewLoanAddingAndGetNewNumber(ArNoteUser arNoteUser, Date startDate) {
        int savedLoanNumber;

        try {
            if (!getAllCredits(arNoteUser).isEmpty()) { // если кредиты вообще есть
                if (getAllCredits(arNoteUser).stream() // берем все кредиты
                        .max(Comparator.comparing(Credit::getCreditNumber)) // ищем тупо свободный слот вообще (данная ситуация возможна если добавлено мало кредитов - то есть на старте работы с приложением)
                        .orElseThrow(FinPlanningException::new).getCreditNumber() >= 5) { // заняты все слоты ?

                    Map<Long, ClosedLoanTr> closedLoansForNow = globalMapOfClosedLoans.entrySet().stream()
                            .filter(r -> r.getValue().getCloseDate().withDayOfMonth(1).isBefore(
                                    dateToLocalDate(startDate).withDayOfMonth(1)))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // достаем уже закрытые кредиты

                    if (closedLoansForNow.size() > 0) { // если нашли закрытые кредиты на момент startDate создаваемого кредита

                        Map<Long, LocalDate> mapForSearchClosestDate = closedLoansForNow.entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().getCloseDate()));

                        savedLoanNumber = closedLoansForNow.entrySet().stream() // берем ближайший из найденных
                                .filter(q -> q.getKey().equals(getNearestDate(mapForSearchClosestDate, dateToLocalDate(startDate))))
                                .map(w -> w.getValue().getLoanNumber()).findFirst().orElseThrow(RuntimeException::new);

                        return savedLoanNumber;
                    } else {
                        throw new AddNewCreditException();
                    }
                } else {
                    savedLoanNumber = getAllCredits(arNoteUser).stream()
                            .max(Comparator.comparing(Credit::getCreditNumber))
                            .map(Credit::getCreditNumber).orElse(0);

                    return savedLoanNumber == 0 ? 1 : savedLoanNumber + 1;
                }
            } else {
                return 1;
            }
        } catch (Exception e) {
            return -1;
        }
    }*/


    /**
     * Добавить кредит.
     *
     * @param principal
     * @param request
     * @return
     */
   /* @Override
    public AddCreditRs addCredit(Principal principal, CreditRq request) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        int nextLoanNumber;
        if (request.getSlotNumber() == null) {
            nextLoanNumber = checkForNewLoanAddingAndGetNewNumber(arNoteUser, request.getStartDate());
        } else {
            nextLoanNumber = request.getSlotNumber();
        }

        if (nextLoanNumber > 0) {
            creditRepo.save(Credit.$toDbEntity(request, nextLoanNumber, arNoteUser));
            return AddCreditRs.builder()
                    .creditNumber(nextLoanNumber)
                    .creditsCount(getAllCredits(arNoteUser).size())
                    .status(ResponseStatusRs.builder()
                            .code(200)
                            .status("SUCCESS")
                            .build())
                    .build();

        } else {
            return AddCreditRs.builder()
                    .creditsCount(getAllCredits(arNoteUser).size())
                    .status(ResponseStatusRs.builder()
                            .code(ErrorCodes.ERR_O4.getDigitalCode())
                            .status("FAIL")
                            .description(ErrorCodes.ERR_O4.getDescription())
                            .build())
                    .build();
        }
    }*/

    /**
     * Удалить кредит.
     *
     * @param principal
     * @param id
     * @return
     */
    /*@Override
    public AddCreditRs deleteLoan(Principal principal, Long id) {
        try {
            ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            Optional<Credit> loan = creditRepo.findCreditByUserAndId(arNoteUser, id);
            if (loan.isPresent()) {
                List<Goal> goalsList = purchaseRepo.findAllByRepaymentAndUser(loan.get().getId(), arNoteUser);
                if (goalsList.size() > 0) {
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
    }*/

   /* @Override
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
    }*/

    /**
     * Редактировать кредит.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
   /* @Override
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
    }*/

    /**
     * Добавить доход.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
   /* @Override
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
    }*/

    /**
     * Удалить доход.
     *
     * @param principal - юзер
     * @return
     * @throws UserNotFoundException
     */
  /*  @Override
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
    }*/

    /**
     * Редактировать доход.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
   /* @Override
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
    }*/

    /**
     * Получить кредит по ID.
     *
     * @param id
     * @param principal
     * @return
     */
   /* @Override
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
    }*/

    /**
     * Получить детализированный расчет баланса за месяц.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
  /*  @Override
    public FinalBalanceCalculationsRs getRemainsDetailInfo(GetRemainsDetailInfoRq payload, Principal principal) throws UserNotFoundException {

        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (globalBalanceMap.isEmpty()) {
            curYear = Calendar.getInstance().get(Calendar.YEAR);
            int finalCalculatedYear = getFinalYear(arNoteUser);
            int yearsCount = calculateYearsCount(arNoteUser);

            if (startMonth == null || startMonth < 1 || startMonth > 12 || yearsCount == 1) {
                startMonth = 1;
            }
            CalculatedLoansTableTr calculatedLoansTable = getCalculatedLoansTable(getAllCredits(arNoteUser));
            calculateFullRemains(arNoteUser, startMonth, yearsCount, finalCalculatedYear, calculatedLoansTable);
        }

        Optional<Freeze> currentFreeze = freezeRepo.findFreezeByUserAndMonthAndYear(arNoteUser, payload.getYear(), payload.getMonth());

        if (globalBalanceMap.size() < 1) { // если пользак только начал и у него ничего не заполнено
            return FinalBalanceCalculationsRs.builder()
                    .currentIncomeDetail(CurrentIncomeRs.builder().build())
                    .emptyCalculations(true)
                    .date(payload.getYear() + " " + getMonthByNumber(payload.getMonth()))
                    .dateInDateFormat(localDateToDate(LocalDate.of(payload.getYear(),
                            payload.getMonth(),
                            1)))
                    .build();
        } else {
            return globalBalanceMap.entrySet().stream()
                    .filter(v -> v.getKey().getYear() == payload.getYear() && v.getKey().getMonthValue() == payload.getMonth())
                    .peek(t -> t.getValue().setDate(payload.getYear() + " " + getMonthByNumber(payload.getMonth())))
                    .peek(d -> d.getValue().setDateInDateFormat(localDateToDate(LocalDate.of(payload.getYear(),
                            payload.getMonth(),
                            1))))
                    .peek(f -> f.getValue().setFreeze(currentFreeze.isPresent()))
                    .peek(f -> f.getValue().setEmptyCalculations(false))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElse(FinalBalanceCalculationsRs.builder()
                            .date(payload.getYear() + " " + getMonthByNumber(payload.getMonth()))
                            .dateInDateFormat(localDateToDate(LocalDate.of(payload.getYear(),
                                    payload.getMonth(),
                                    1)))
                            .currentIncomeDetail(CurrentIncomeRs.builder().build())
                            .emptyCalculations(true)
                            .build());
        }
    }*/

  /*  @Override
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
*/
    /**
     * Получить кредит по дате и пользаку.
     *
     * @param payload
     * @param principal
     * @return
     */
   /* @Override
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
            log.error("Произошла ошибка при получении кредитов по дате: {}", e.getMessage());
            return FullLoansListRs.builder()
                    .loansList(Collections.emptyList())
                    .build();
        }

    }*/


    /**
     * Редактировать цель.
     *
     * @param payload
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    /*@Override
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
                        .status("SUCCESS") // todo: SUCCESS - в константы или енумы
                        .build())
                .build();
    }*/

    /**
     * Получить список зарплат по пользаку.
     *
     * @param principal
     * @return
     */
   /* @Override
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
    }*/

    /**
     * Удалить ЗП по id.
     *
     * @param principal
     * @param id
     * @return
     * @throws UserNotFoundException
     */
    /*@Override
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
    }*/

    /**
     * Добавить фриз.
     *
     * @param principal
     * @param request
     * @return
     */
  /*  @Override
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
    }*/

    /**
     * Получить свободные слоты по кредитам.
     *
     * @param principal
     * @param payload
     * @return
     */
   /* @Override
    public FreeLoanSlotsRs getLoansSlots(Principal principal, LoanByDateRq payload) throws UserNotFoundException {
        ArNoteUser arNoteUser = users.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        List<Credit> allCreditList = getAllCredits(arNoteUser);
        if (!allCreditList.isEmpty()) { // если кредиты вообще есть

            Credit loanWithMaxFreeSlot = allCreditList.stream() // берем все кредиты
                    .max(Comparator.comparing(Credit::getCreditNumber)).orElse(Credit.builder()
                            .creditNumber(5)
                            .build()); // ищем тупо свободный слот вообще (данная ситуация возможна если добавлено мало кредитов - то есть на старте работы с приложением)

            if (loanWithMaxFreeSlot.getCreditNumber() < 5) {
                return FreeLoanSlotsRs.builder()
                        .allLoansCount(getAllCredits(arNoteUser).size())
                        .openSlots(Collections.singletonList(loanWithMaxFreeSlot.getCreditNumber()))
                        .build();
            }
            if (globalMapOfClosedLoans.size() < 1) {
                getCalculatedLoansTable(allCreditList);
            }
            if (globalMapOfClosedLoans.size() < 1) {
                return FreeLoanSlotsRs.builder()
                        .allLoansCount(getAllCredits(arNoteUser).size())
                        .build();
            }*/

            /**
             * Тут мы собрали просто все закрытые относительно заданной даты кредиты. Но может быть кейс, что до
             * заданной даты кредит закончился и сразу после него стартанул другой. Поэтому сначала нам надо откинуть кредиты,
             * которые закончились раньше заданной даты и сразу после них, опять же раньше заданной даты стартанули
             * другие кредиты.
             */

         /*   Map<Long, ClosedLoanTr> filteredMap = new HashMap<>();

            for (Map.Entry<Long, ClosedLoanTr> item : globalMapOfClosedLoans.entrySet()) {
                if (item.getValue().getCloseDate().withDayOfMonth(1).isBefore(
                        dateToLocalDate(payload.getStartDate()).withDayOfMonth(1))) {*/
                    /**
                     * Если кредит закончился (выплачен) ранее запрашиваемой даты, ищем, нет ли другого кредита
                     * начавшегося ранее запрашиваемой даты в этом же слоте.
                     */
            /*        List<Credit> creditsStartedAfterClosedForUserDate = allCreditList.stream()
                            .filter(s -> s.getCreditNumber().equals(item.getValue().getLoanNumber())) // ищем в данном слоте
                            .filter(l -> !l.getId().equals(item.getKey())) // откидываем данный кредит
                            .filter(sd->dateToLocalDate(sd.getStartDate()).isAfter(item.getValue().getCloseDate()) &&
                                    dateToLocalDate(sd.getStartDate())
                                            .isBefore(dateToLocalDate(payload.getStartDate()).withDayOfMonth(1)))
                            .collect(Collectors.toList());

                    if (creditsStartedAfterClosedForUserDate.size()==0){
                        filteredMap.put(item.getKey(), item.getValue());
                    }
                }
            }


            Map<Long, ClosedLoanTr> closedLoansForNow = filteredMap.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // достаем уже закрытые кредиты


            if (closedLoansForNow.size() > 0) { // если нашли закрытые кредиты на момент startDate создаваемого кредита

                return FreeLoanSlotsRs.builder()
                        .allLoansCount(getAllCredits(arNoteUser).size())
                        .openSlots(closedLoansForNow.values().stream()
                                .map(ClosedLoanTr::getLoanNumber)
                                .collect(Collectors.toList()))
                        .build();
            } else {
                return FreeLoanSlotsRs.builder()
                        .allLoansCount(getAllCredits(arNoteUser).size())
                        .build();
            }
        }

        return FreeLoanSlotsRs.builder()
                .allLoansCount(getAllCredits(arNoteUser).size())
                .openSlots(Arrays.asList(1, 2, 3, 4, 5))
                .build();
    }*/

    /**
     * Стартовать вычисления консолидированной таблицы.
     *
     * @param principal
     * @return
     */
 /*   @Override
    public void startCalculation(Principal principal) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->{
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        atomicInt.updateAndGet(n -> n + 2);

                    };

                    executor.submit(task);
                });

        executor.shutdown();
    }

    @Override
    public Integer getThreadStatus() {
        return atomicInt.get();
    }*/


}
