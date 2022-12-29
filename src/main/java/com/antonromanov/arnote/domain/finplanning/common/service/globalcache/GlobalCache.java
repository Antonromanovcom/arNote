package com.antonromanov.arnote.domain.finplanning.common.service.globalcache;

import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.ClosedLoanTr;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GlobalCache {
   // ======================= Кэш закрытых кредитов ========================== //
    Map<Long, ClosedLoanTr> getClosedLoansForDate(Date startDate); // получить список закрытых кредитов по состоянию на конкретную дату
    Map<Long, ClosedLoanTr> getAllClosedLoansMap(); // получить весь список закрытых кредитов

    // ======================= Кэш целей / покупок ========================== //
    List<Goal> getGlobalGoalList(); // глобальная мапа расходов
}
