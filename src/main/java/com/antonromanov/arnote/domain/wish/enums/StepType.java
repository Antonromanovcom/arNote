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
public enum StepType {

    UP(((wish, ms) -> {
        wish.setPriority(wish.getPriority() + 1);
        return wish;
    })),
    DOWN(((wish, ms) -> {
        if (wish.getPriority() > 1) {
            wish.setPriority(wish.getPriority() - 1);
        }
        return wish;
    }));

    private final ArnoteOperation changePriority;


    public Wish act(Wish wish, WishService ms) {
        return changePriority.move(wish, ms);
    }
}
