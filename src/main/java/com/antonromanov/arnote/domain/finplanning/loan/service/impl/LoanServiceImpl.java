package com.antonromanov.arnote.domain.finplanning.loan.service.impl;

import com.antonromanov.arnote.domain.finplanning.common.enums.CreditDict;
import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.goal.repositoty.GoalsRepo;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.*;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.CalculatedLoansTableTr;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.ClosedLoanTr;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.LoanListTr;
import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.domain.finplanning.loan.mapper.LoanRqMapper;
import com.antonromanov.arnote.domain.finplanning.loan.mapper.LoanRsMapper;
import com.antonromanov.arnote.domain.finplanning.loan.service.LoanService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.LoanByDateRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.LoanTr;
import com.antonromanov.arnote.domain.finplanning.exceptions.AddNewCreditException;
import com.antonromanov.arnote.common.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.domain.finplanning.exceptions.FinPlanningException;
import com.antonromanov.arnote.common.exceptions.UserNotFoundException;
import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import com.antonromanov.arnote.old.repositoty.CreditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.common.exceptions.enums.ErrorCodes.ERR_12;
import static com.antonromanov.arnote.old.utils.ArNoteUtils.getNearestDate;
import static com.antonromanov.arnote.old.utils.Utils.dateToLocalDate;


@Service
@Slf4j // в одном месте логгируем ошибку
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserService userService;
    private final CreditRepository creditRepo;
    private final GlobalCache globalCache;
    private final LoanRqMapper rqMapper;
    private final LoanRsMapper rsMapper;
    private GoalsRepo purchaseRepo;


    /**
     * Достать все кредиты
     *
     * @return
     */
    @Override
    public List<Credit> getAllCredits(ArNoteUser user) {
        return creditRepo.getCreditsByUser(user);
    }


    /**
     * Посчитать дату выплаты самого последнего кредита.
     *
     * @param
     * @return
     */
    @Override
    public Optional<LocalDate> getLastCreditDate(ArNoteUser user) {
        try {
            return getCalculatedLoansTable(user).getCalculatedLoansList().stream()
                    .map(v -> v.entrySet().stream()
                            .max(Map.Entry.comparingByKey())
                            .orElseThrow(() -> new FinPlanningException(ERR_12))
                            .getKey())
                    .max(LocalDate::compareTo);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }


    /**
     * Тащим стартовую дату кредита и конвертим в LocalDate.
     *
     * @param credit
     * @return
     */
    private LocalDate getLoanPaymentsStartDate(Credit credit) {
        return new Date(credit.getStartDate()
                .getTime())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Ищем досрочные "погашалки" кредита
     *
     * @param paymentDate
     * @return
     */
    private Map<Long, Map<LocalDate, Integer>> getEarlyRepayments(LocalDate paymentDate) {
        return globalCache.getGlobalGoalList()
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
    }


    /**
     * Применить карту с досрочными погашениями к кредиту.
     *
     * @param credit                 - текущий кредит.
     * @param creditWithRepaymentMap - карта с досрочными погашениями.
     * @param paySum
     * @param paymentDate
     */
    private int applyEarlyRepayments(Credit credit, Map<Long, Map<LocalDate, Integer>> creditWithRepaymentMap,
                                     int paySum, LocalDate paymentDate) {
        if (!creditWithRepaymentMap.isEmpty()) {
            Integer repaymentByDateAndLoanId = creditWithRepaymentMap.entrySet().stream()
                    .filter(z -> creditRepo // todo: заменить на предикат, вынести в отдельный метод  фильтровать список а не лазить в репу каждый раз.
                            .findById(z.getKey())
                            .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_O6)) != null &&
                            credit.getId().equals(z.getKey()))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .map(b -> {

                        LocalDate tempDate = b.entrySet().stream().findFirst()
                                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_O6)).getKey();
                        Integer repayment = b.entrySet().stream().findFirst()
                                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_O6)).getValue();

                        return tempDate.getYear() == paymentDate.getYear() &&
                                tempDate.getMonthValue() == paymentDate.getMonthValue() ? repayment : 0;

                    })
                    .orElse(0);
            paySum = paySum - repaymentByDateAndLoanId;
        }
        return paySum;
    }

    private boolean isPaymentDayInMap(LocalDate paymentDate, LinkedHashMap<LocalDate, LoanListTr> payMap) {
        return (payMap.entrySet().stream().anyMatch(r -> r.getKey().isEqual(paymentDate)));
    }

    private LoanListTr getLoanListByDate(LocalDate paymentDate, LinkedHashMap<LocalDate, LoanListTr> payMap) {
        return payMap.entrySet().stream()
                .filter(r -> r.getKey().isEqual(paymentDate))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_O6));

    }

    private void addLoansInExistingList(LoanListTr localLoanList, int paySum, Credit credit) {
        localLoanList.getLoanList().add(LoanTr.builder()
                .amount(paySum)
                .loanId(credit.getId())
                .build());
    }

    private void putNewLoanListInMap(LinkedHashMap<LocalDate, LoanListTr> payMap, LocalDate paymentDate, int paySum,
                                     Credit credit) {
        payMap.put(paymentDate, LoanListTr.builder()
                .loanList(new ArrayList<>(Arrays.asList(LoanTr.builder()
                        .amount(paySum)
                        .loanId(credit.getId())
                        .build())))
                .build());
    }

    private void putNewClosedLoanToCache(LocalDate paymentDate, Credit credit) {
        globalCache.getAllClosedLoansMap().put(credit.getId(), ClosedLoanTr.builder()
                .startDate(dateToLocalDate(credit.getStartDate()))
                .closeDate(paymentDate)
                .loanNumber(credit.getCreditNumber())
                .build());

    }

    private void fillPaymentsMap(Credit credit, LinkedHashMap<LocalDate, LoanListTr> payMap) {
        LocalDate creditDate = getLoanPaymentsStartDate(credit);
        int paySum = credit.getStartAmount(); // сколько еще денег осталось платить
        int currentMonth = 0;

        while (paySum > 0) {
            paySum = paySum - credit.getRealPayPerMonth();
            LocalDate paymentDate = creditDate.withDayOfMonth(1).plusMonths(currentMonth);
            Map<Long, Map<LocalDate, Integer>> creditWithRepaymentMap = getEarlyRepayments(paymentDate); // ищем досрочные погашения
            paySum = applyEarlyRepayments(credit, creditWithRepaymentMap, paySum, paymentDate); // применяем к текущему кредиту

            if (paySum < 1) {
                putNewClosedLoanToCache(paymentDate, credit);
                paySum = 0;
            }

            if (isPaymentDayInMap(paymentDate, payMap)) {
                addLoansInExistingList(getLoanListByDate(paymentDate, payMap), paySum, credit);
            } else {
                putNewLoanListInMap(payMap, paymentDate, paySum, credit);
            }

            currentMonth++;

            // Проверка, если кто-то запихнул какие-то запредельные значения, чтобы не попали в вечный цикл.
            if (currentMonth > 120) {
                paySum = 0;
            }
        }
    }


    /**
     * Достать из БД и рассчитать текущие кредиты. Берем платеж, берем досрочные погашения и строим виртуальную
     * таблицу закрытия кредитов и платежей.
     *
     * @param
     * @return
     */
    @Override
    public CalculatedLoansTableTr getCalculatedLoansTable(ArNoteUser user) {

        List<LinkedHashMap<LocalDate, LoanListTr>> resultList = new LinkedList<>(); // Конечный ответ: список мап. Каждая мапа дата + данные по кредиту
        LinkedHashMap<LocalDate, LoanListTr> payMap = new LinkedHashMap<>(); // сама мапа - дата + данные по кредиту.

        // Бегаем по всем переданным кредитам.
        (getAllCredits(user)).forEach(credit -> fillPaymentsMap(credit, payMap));

        resultList.add(payMap);
        return CalculatedLoansTableTr.builder()
                .calculatedLoansList(resultList)
                .build();
    }


    /**
     * Получить и рассчитать текущие кредиты отфильтрованные по текущей дате.
     *
     * @param
     * @return
     */
    public CreditListRs getCreditsFiltered(Integer year, Integer month) {
        ArNoteUser user = userService.getUserFromPrincipal();
        List<LinkedHashMap<LocalDate, LoanListTr>> calculatedLoansList = getCalculatedLoansTable(user).getCalculatedLoansList();
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
                            .orElseThrow(()->new FinPlanningException(ERR_12));

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
     * Проверяем, что можно добавить новый кредит и если можно - возвращаем новый номер.
     * Если "-1" - значит какая-то ошибка или добавить нельзя.
     *
     * @return
     */
    public int checkForNewLoanAddingAndGetNewNumber(ArNoteUser arNoteUser, Date startDate) {
        int savedLoanNumber;
        List<Credit> allCreditsList = getAllCredits(arNoteUser);
        try {
            if (!allCreditsList.isEmpty()) { // если кредиты вообще есть
                var maxCreditSlot = allCreditsList.stream() // берем все кредиты
                        .max(Comparator.comparing(Credit::getCreditNumber)); // ищем тупо свободный слот вообще (данная ситуация возможна, если добавлено мало кредитов - то есть на старте работы с приложением)
                if (maxCreditSlot.orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_O6)).getCreditNumber() >= 5) { // заняты все слоты ?

                    Map<Long, ClosedLoanTr> closedLoansForNow = globalCache.getClosedLoansForDate(startDate); // достаем уже закрытые кредиты

                    if (closedLoansForNow.size() > 0) { // если нашли закрытые кредиты на момент startDate создаваемого кредита

                        Map<Long, LocalDate> mapForSearchClosestDate = closedLoansForNow.entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().getCloseDate()));

                        return closedLoansForNow.entrySet().stream() // берем ближайший из найденных
                                .filter(q -> q.getKey().equals(getNearestDate(mapForSearchClosestDate, dateToLocalDate(startDate))))
                                .map(w -> w.getValue().getLoanNumber()).findFirst().orElseThrow(RuntimeException::new);
                    } else {
                        throw new AddNewCreditException();
                    }
                } else {
                    savedLoanNumber = maxCreditSlot.map(Credit::getCreditNumber).orElse(0);
                    return savedLoanNumber + 1;
                }
            } else {
                return 1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Добавить кредит.
     *
     * @param request
     * @return
     */
    @Override
    public OperateCreditRs addLoan(CreditRq request) throws UserNotFoundException, BadIncomeParameter {
        ArNoteUser user = userService.getUserFromPrincipal();
        int nextLoanNumber;
        if (request.getSlotNumber() == null) {
            nextLoanNumber = checkForNewLoanAddingAndGetNewNumber(user, request.getStartDate());
        } else {
            nextLoanNumber = request.getSlotNumber();
        }

        if (nextLoanNumber > 0) {
            creditRepo.save(rqMapper.map(request, nextLoanNumber, user));
            int allCredit = getAllCredits(user).size();
            return rsMapper.map(nextLoanNumber, allCredit);
        } else {
            throw new BadIncomeParameter(ErrorCodes.ERR_O4);
        }
    }

    /**
     * Удалить кредит.
     *
     * @param id
     * @return
     */
    @Override
    public OperateCreditRs deleteLoan(Long id) {
        try {
            ArNoteUser arNoteUser = userService.getUserFromPrincipal();
            Optional<Credit> loan = creditRepo.findCreditByUserAndId(arNoteUser, id);
            if (loan.isPresent()) {
                List<Goal> goalsList = purchaseRepo.findAllByRepaymentAndUser(loan.get().getId(), arNoteUser);
                if (goalsList.size() > 0) {
                    purchaseRepo.deleteInBatch(goalsList);
                }
            }
            loan.ifPresent(creditRepo::delete);
            return rsMapper.map(null, getAllCredits(arNoteUser).size());
        } catch (Exception e) {
            throw new FinPlanningException(ErrorCodes.ERR_13);
        }
    }

    @Override
    public FullLoansListRs getFullLoansList() {
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        return FullLoansListRs.builder()
                .loansList(getAllCredits(arNoteUser).stream()
                        .map(rsMapper::mapCreditToFullLoanRs)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Редактировать кредит.
     *
     * @param payload
     * @return
     */
    @Override
    public OperateCreditRs editLoan(CreditRq payload) { // todo: добавить проверку номера слота
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        Optional<Credit> loan = creditRepo.findCreditByUserAndId(arNoteUser, payload.getId());

        if (loan.isPresent()) {
            creditRepo.save(rqMapper.map(payload, payload.getSlotNumber(), arNoteUser));
            return OperateCreditRs.builder()
                    .creditsCount(getAllCredits(arNoteUser).size())
                    .creditNumber(payload.getSlotNumber())
                    .build();
        } else {
            throw new FinPlanningException(ErrorCodes.ERR_10);
        }
    }


    /**
     * Получить кредит по ID.
     *
     * @param id
     * @return
     */
    @Override
    public CreditRs getLoanById(Long id) {
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        return creditRepo.findCreditByUserAndId(arNoteUser, id).map(v -> CreditRs.builder()
                        .id(v.getId())
                        .description(v.getDescription())
                        .amount(v.getStartAmount())
                        .number(v.getCreditNumber())
                        .startDate(v.getStartDate())
                        .fullPayPerMonth(v.getFullPayPerMonth())
                        .realPayPerMonth(v.getRealPayPerMonth())
                        .build())
                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_15));
    }


    /**
     * Получить кредит по дате и пользаку.
     *
     * @param payload
     * @return
     */
    @Override
    public FullLoansListRs getLoanByDate(LoanByDateRq payload) { //todo: к этому методу надо вернуться, обрабатывать нормально ошибк и убрать .get(). Ошибки выбрасывать в блоке "status"
        try {

            List<List<CreditRs>> rs = globalCache.getGlobalConsolidatedTable().getFinPlans().stream()
                    .filter(f -> f.getYear() == dateToLocalDate(payload.getStartDate()).getYear() &&
                            f.getMonthNumber() == dateToLocalDate(payload.getStartDate()).getMonthValue())
                    .map(r -> r.getCreditsList().getCredits())
                    .collect(Collectors.toList()); //todo: мы эту переменую rs не используем. Почему?

            return FullLoansListRs.builder()
                    .loansList((globalCache.getGlobalConsolidatedTable().getFinPlans().stream()
                            .filter(f -> f.getYear() == dateToLocalDate(payload.getStartDate()).getYear() &&
                                    f.getMonthNumber() == dateToLocalDate(payload.getStartDate()).getMonthValue())
                            .map(r -> r.getCreditsList().getCredits())
                            .findFirst().get())
                            .stream()//todo: поправить потом этот гет
                            .map(rsMapper::mapCreditRsToFullLoanRs)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            log.error("Произошла ошибка при получении кредитов по дате: {}", e.getMessage());
            return FullLoansListRs.builder()
                    .loansList(Collections.emptyList())
                    .build();
        }
    }

    /**
     * Получить свободные слоты по кредитам.
     *
     * @param payload
     * @return
     */
    @Override
    public FreeLoanSlotsRs getLoansSlots(String payload) throws UserNotFoundException {
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        List<Credit> allCreditList = getAllCredits(arNoteUser);

        if (!allCreditList.isEmpty()) { // если кредиты вообще есть

            Credit loanWithMaxFreeSlot = allCreditList.stream() // берем все кредиты
                    .max(Comparator.comparing(Credit::getCreditNumber)).orElse(Credit.builder()
                            .creditNumber(5)
                            .build()); // ищем тупо свободный слот вообще (данная ситуация возможна если добавлено мало кредитов - то есть на старте работы с приложением)

            if (loanWithMaxFreeSlot.getCreditNumber() < 5) {
                return FreeLoanSlotsRs.builder()
                        .allLoansCount(getAllCredits(arNoteUser).size())
                        .openSlots(Collections.singletonList(loanWithMaxFreeSlot.getCreditNumber() + 1))
                        .build();
            }


            if (globalCache.getAllClosedLoansMap().isEmpty()) {
                getCalculatedLoansTable(arNoteUser); // todo: возвращаемое значение не используем. Тут точно не void?
            }
            if (globalCache.getAllClosedLoansMap().isEmpty()) {
                return FreeLoanSlotsRs.builder()
                        .allLoansCount(getAllCredits(arNoteUser).size())
                        .build();
            }

            /**
             * Тут мы собрали просто все закрытые относительно заданной даты кредиты. Но может быть кейс, что до
             * заданной даты кредит закончился и сразу после него стартанул другой. Поэтому сначала нам надо откинуть кредиты,
             * которые закончились раньше заданной даты и сразу после них, опять же раньше заданной даты стартанули
             * другие кредиты.
             */
            Map<Long, ClosedLoanTr> filteredMap = new HashMap<>();
            LocalDate localDate = LocalDate.parse(payload);
            for (Map.Entry<Long, ClosedLoanTr> item : globalCache.getAllClosedLoansMap().entrySet()) {
                if (item.getValue().getCloseDate().withDayOfMonth(1).isBefore(localDate.withDayOfMonth(1))) {
                    /**
                     * Если кредит закончился (выплачен) ранее запрашиваемой даты, ищем, нет ли другого кредита
                     * начавшегося ранее запрашиваемой даты в этом же слоте.
                     */
                    List<Credit> creditsStartedAfterClosedForUserDate = allCreditList.stream()
                            .filter(s -> s.getCreditNumber().equals(item.getValue().getLoanNumber())) // ищем в данном слоте
                            .filter(l -> !l.getId().equals(item.getKey())) // откидываем данный кредит
                            .filter(sd -> dateToLocalDate(sd.getStartDate()).isAfter(item.getValue().getCloseDate()) &&
                                    dateToLocalDate(sd.getStartDate()).isBefore(localDate.withDayOfMonth(1)))
                            .collect(Collectors.toList());

                    if (creditsStartedAfterClosedForUserDate.size() == 0) {
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
    }

    @Override
    public Integer getLoanPaymentsByDate(int curYear, int curMonth) {

        List<Integer> resultSum = new ArrayList<>();
        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        List<LinkedHashMap<LocalDate, LoanListTr>> calculatedLoansList = getCalculatedLoansTable(arNoteUser).getCalculatedLoansList();

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
    }
}
