package com.antonromanov.arnote.domain.finplanning.common.service.globalcache.impl;

import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.ClosedLoanTr;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanListRs;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    public FinPlanListRs getGlobalConsolidatedTable() {
        return globalConsolidatedTable;
    }

    @Override
    public int getCurrentYear() {
        if (curYear == 0) {
            curYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        return curYear;
    }
}
