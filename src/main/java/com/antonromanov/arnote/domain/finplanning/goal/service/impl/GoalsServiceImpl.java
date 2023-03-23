package com.antonromanov.arnote.domain.finplanning.goal.service.impl;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.finplanning.goal.dto.rq.GoalRq;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.domain.finplanning.goal.mapper.rq.GoalMapperRq;
import com.antonromanov.arnote.domain.finplanning.goal.repositoty.GoalsRepo;
import com.antonromanov.arnote.domain.finplanning.goal.service.GoalsService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import com.antonromanov.arnote.old.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GoalsServiceImpl implements GoalsService {

    private final GoalsRepo purchaseRepo;
    private final UserService userService;
    private final GoalMapperRq mapper;


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

    @Override
    public SingleOperationRs addGoal(GoalRq payload) {

        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        Goal newGoal = purchaseRepo.saveAndFlush(mapper.map(payload, arNoteUser));
        return SingleOperationRs
                .builder()
                .id(newGoal.getId())
                .build();
    }

    @Override
    public SingleOperationRs deleteGoal(Long id) {

        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        Optional<Goal> loan = purchaseRepo.findGoalByIdAndUser(id, arNoteUser);
        loan.ifPresent(purchaseRepo::delete);

        return SingleOperationRs
                .builder()
                .id(id)
                .build();
    }

    @Override
    public SingleOperationRs editGoal(GoalRq payload) {
        // todo: где проверка прислали id или нет и вообще валидация входного ДТО (Rq) ??
        // todo: где проверка есть эта запись или нет и если нет - выкидывание сообщения об ошибке


        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        Optional<Goal> existGoal = purchaseRepo.findGoalByIdAndUser(payload.getId(), arNoteUser);
        existGoal.ifPresent(goal -> purchaseRepo.save(mapper.map(payload, arNoteUser)));
        return SingleOperationRs
                .builder()
                .id(payload.getId())
                .build();
    }
}
