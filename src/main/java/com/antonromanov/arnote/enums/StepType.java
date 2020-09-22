package com.antonromanov.arnote.enums;

import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.service.MainService;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы одношагового перемещения.
 */
@AllArgsConstructor
@Getter
public enum StepType implements ArnoteOperation{

    UP(((wish, ms) -> ms.updateWish(wish.setPriorityAndReturnWish(wish.getPriority() + 1))),
            ((wish, ms) -> {
                int maxPrior = (ms.getMaxPriority(wish.getUser())) - 1;
                if (maxPrior != 0) {
                    if (wish.getPriorityGroup() < maxPrior + 1) {
                        wish.setPriorityGroup(wish.getPriorityGroup() + 1);
                    }
                    return ms.updateWish(wish);
                }
                return wish;
            })),
    DOWN(((wish,  ms) -> wish.getPriority() > 1 ? ms.updateWish(wish.setPriorityAndReturnWish(wish.getPriority() - 1)) : wish),
            ((wish,  ms) -> {
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
                return ms.updateWish(wish);
            }));

    private final ArnoteOperation changePriority;
    private final ArnoteOperation changeMonthOrder;

    @Override
    public Wish move(Wish wish, MainService ms) {
        return changePriority.move(wish, ms);
    }
}
