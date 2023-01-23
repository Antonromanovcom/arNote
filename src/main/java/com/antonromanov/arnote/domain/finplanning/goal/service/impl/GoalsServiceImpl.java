package com.antonromanov.arnote.domain.finplanning.goal.service.impl;

import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.goal.repositoty.GoalsRepo;
import com.antonromanov.arnote.domain.finplanning.goal.service.GoalsService;
import com.antonromanov.arnote.old.model.ArNoteUser;
import com.antonromanov.arnote.old.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class GoalsServiceImpl implements GoalsService {

    private final GoalsRepo purchaseRepo;


    /**
     * Посчитать последнюю дату запланированных покупок.
     *
     * @param
     * @param goalList - список планов
     * @return
     */
    @Override
    public LocalDate getLastGoalsDate(List<Goal> goalList) {
        return goalList.stream()
                .max(Comparator.comparing(Goal::getStartDate))
                .map(Goal::getStartDate)
                .map(Utils::dateToLocalDate)
                .orElse(LocalDate.now());
    }

    @Override
    public List<Goal> getAllPurchases(ArNoteUser arNoteUser) {
        return purchaseRepo.findAllByUser(arNoteUser);
    }
}
