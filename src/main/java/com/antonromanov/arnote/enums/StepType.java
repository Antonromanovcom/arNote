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

    UP((wish, ms) -> ms.updateWish(wish.setPriorityAndReturnWish(wish.getPriority() + 1))),
    DOWN((wish,  ms) -> wish.getPriority() > 1 ? ms.updateWish(wish.setPriorityAndReturnWish(wish.getPriority() - 1)) : wish);

    private final ArnoteOperation operation;

    @Override
    public Wish move(Wish wish, MainService ms) {
        return operation.move(wish, ms);
    }
}
