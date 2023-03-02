package com.antonromanov.arnote.domain.finplanning.common.service.globalcache.impl;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.ConsolidatedPurchasesRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanListRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.PurchasesRs;
import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.ClosedLoanTr;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.old.utils.Utils.dateToLocalDate;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GlobalCacheImpl implements GlobalCache {

    // =================== БАЗОВЫЕ ГЛОБАЛЬНЫЕ КОНСТАНТЫ ======================
    private LinkedHashMap<Long, ClosedLoanTr> globalMapOfClosedLoans = new LinkedHashMap<>(); // глобальная мапа выплаченых кредитов
    private List<Goal> globalGoalList = new ArrayList<>(); // глобальная мапа расходов
    private FinPlanListRs globalConsolidatedTable = new FinPlanListRs(); // глобальная консолидированная мапа чтобы каждый раз не ходить в БД
    private int curYear; // текущий год
    private LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> globalBalanceMap = new LinkedHashMap<>(); // глобальная мапа остатков
    // =================== ============================ ======================


    @Override
    public Map<Long, ClosedLoanTr> getClosedLoansForDate(Date startDate) {
        return globalMapOfClosedLoans.entrySet().stream()
                .filter(r -> r.getValue().getCloseDate().withDayOfMonth(1).isBefore(
                        dateToLocalDate(startDate).withDayOfMonth(1)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // достаем уже закрытые кредиты
    }

    @Override
    public Map<Long, ClosedLoanTr> getAllClosedLoansMap() {
        return globalMapOfClosedLoans;
    }

    @Override
    public List<Goal> getGlobalGoalList() {
        return globalGoalList;
    }

    @Override
    public void fillGoals(List<Goal> goals) {
        Collections.copy(globalGoalList, goals);
    }

    @Override
    public Integer getPreviousExpense(int curYear, int curMonth) {
        int calculatedMonth = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getMonthValue();
        int calculatedYear = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getYear();
        return globalGoalList.stream()
                .filter(g -> dateToLocalDate(g.getStartDate()).getYear() == calculatedYear &&
                        dateToLocalDate(g.getStartDate()).getMonthValue() == calculatedMonth)
                .map(Goal::getPrice)
                .reduce(Integer::sum)
                .orElse(0);

    }

    @Override
    public FinPlanListRs getGlobalConsolidatedTable() {
        return globalConsolidatedTable;
    }

    @Override
    public void setGlobalConsolidatedTable(FinPlanListRs table) {
        globalConsolidatedTable = table;
    }

    @Override
    public void clearGlobalBalanceMap() {
        globalBalanceMap.clear();
    }

    @Override
    public LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> getGlobalBalanceMap() {
        return globalBalanceMap;
    }

    @Override
    public void putInGlobalBalanceMap(LocalDate date, FinalBalanceCalculationsRs balanceCalculation) {
        globalBalanceMap.put(date, balanceCalculation);
    }

    @Override
    public Integer prepareFinalBalance(int year, int month) {
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

    @Override
    public int getCurrentYear() {
        if (curYear == 0) {
            curYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        return curYear;
    }

    /**
     * Формируем покупки по месяцу.
     *
     * @param year  - год
     * @param month - месяц
     * @return
     */
    public ConsolidatedPurchasesRs getPurchasePlan(int year, int month) {
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
}
