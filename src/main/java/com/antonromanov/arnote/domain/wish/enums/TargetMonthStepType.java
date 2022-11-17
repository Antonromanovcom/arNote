package com.antonromanov.arnote.domain.wish.enums;

import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.sex.model.wish.Wish;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы одношагового перемещения.
 */
@AllArgsConstructor
@Getter
public enum TargetMonthStepType {

    UP((wish, ms) -> {

                    int maxPrior = ms.getMaxPriority(wish.getUser()) - 1;
                    if (maxPrior != 0) {
                        if (wish.getPriorityGroup() < maxPrior + 1) {
                            wish.setPriorityGroup(wish.getPriorityGroup() + 1);
                        }
                    }
                    return wish;
    }),
    DOWN(((wish,  ms) -> {
                int maxPrior = (ms.getMaxPriority(wish.getUser())) - 1;
                if (maxPrior == 0) {
                    wish.setPriorityGroup(1);
                } else {
                    if (wish.getPriorityGroup() == null) {
                        wish.setPriorityGroup(maxPrior);
                    } else if (wish.getPriorityGroup() > 1) {
                        wish.setPriorityGroup(wish.getPriorityGroup() - 1);
                    }
                }
                return wish;
            }));

    private final ArnoteOperation changePriority;


    public Wish act(Wish wish, WishService ms) {
        return changePriority.move(wish, ms);
    }
}
