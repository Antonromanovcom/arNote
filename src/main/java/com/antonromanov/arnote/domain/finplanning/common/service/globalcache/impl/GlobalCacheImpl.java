package com.antonromanov.arnote.domain.finplanning.common.service.globalcache.impl;

import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.ClosedLoanTr;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.sex.utils.Utils.dateToLocalDate;


@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@AllArgsConstructor
public class GlobalCacheImpl implements GlobalCache {

    // =================== БАЗОВЫЕ ГЛОБАЛЬНЫЕ КОНСТАНТЫ ======================
    private LinkedHashMap<Long, ClosedLoanTr> globalMapOfClosedLoans = new LinkedHashMap<>(); // глобальная мапа выплаченых кредитов
    List<Goal> globalGoalList = new ArrayList<>(); // глобальная мапа расходов
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
}
