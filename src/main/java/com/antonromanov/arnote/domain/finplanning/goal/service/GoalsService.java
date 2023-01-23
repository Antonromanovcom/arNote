package com.antonromanov.arnote.domain.finplanning.goal.service;

import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.old.model.ArNoteUser;
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
     * @param arNoteUser
     * @return
     */
    List<Goal> getAllPurchases(ArNoteUser arNoteUser);
}
