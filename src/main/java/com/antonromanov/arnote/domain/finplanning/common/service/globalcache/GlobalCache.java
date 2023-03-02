package com.antonromanov.arnote.domain.finplanning.common.service.globalcache;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.ConsolidatedPurchasesRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanListRs;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.ClosedLoanTr;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface GlobalCache {

    // ======================= Кэш закрытых кредитов ========================== //
    Map<Long, ClosedLoanTr> getClosedLoansForDate(Date startDate); // получить список закрытых кредитов по состоянию на конкретную дату
    Map<Long, ClosedLoanTr> getAllClosedLoansMap(); // получить весь список закрытых кредитов



    // ======================= Кэш целей / покупок ========================== //
    List<Goal> getGlobalGoalList(); // глобальная мапа расходов
    void fillGoals(List<Goal> goals); // заполнить из БД список целей/покупок
    ConsolidatedPurchasesRs getPurchasePlan(int year, int month); // Формируем покупки по месяцу.
    Integer getPreviousExpense(int curYear, int curMonth); // Вытаскиваем предыдущую цель / фин-план / расход  из соответствующего хранилища.



    // ============ глобальная консолидированная мапа чтобы каждый раз не ходить в БД ============ //
    FinPlanListRs getGlobalConsolidatedTable();
    void setGlobalConsolidatedTable(FinPlanListRs table);

    // ============ глобальная мапа остатков ============ //
    void clearGlobalBalanceMap(); // очистить мапу
    LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> getGlobalBalanceMap();
    void putInGlobalBalanceMap(LocalDate date, FinalBalanceCalculationsRs balanceCalculation);
     Integer prepareFinalBalance(int year, int month);

    /**
     * Достать текущий год
     *
     * @return
     */
    int getCurrentYear();
}
