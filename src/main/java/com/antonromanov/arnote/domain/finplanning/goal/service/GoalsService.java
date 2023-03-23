package com.antonromanov.arnote.domain.finplanning.goal.service;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.finplanning.goal.dto.rq.GoalRq;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import java.time.LocalDate;
import java.util.List;

/**
 * Сервисный слой работы с финансовыми целями.
 */
public interface GoalsService {
    /**
     * Посчитать последнюю дату запланированных покупок.
     *
     * @param
     * @param goalList - список планов
     * @return
     */
    LocalDate getLastGoalsDate(List<Goal> goalList);

    /**
     * Получить все планы / покупки по пользаку.
     *
     * @param arNoteUser
     * @return
     */
    List<Goal> getAllPurchases(ArNoteUser arNoteUser);


    /**
     * Добавить расход / цель.
     *
     * @param payload
     * @return
     */
    SingleOperationRs addGoal(GoalRq payload);

    /**
     * Удалить цель.
     *
     * @param id - id кредита
     * @return
     */
    SingleOperationRs deleteGoal(Long id);

    /**
     * Редактировать расход / цель.
     *
     * @param payload
     * @return
     */
    SingleOperationRs editGoal(GoalRq payload);

}
