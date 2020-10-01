package com.antonromanov.arnote.enums;

import com.antonromanov.arnote.entity.Wish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.Predicate;

/**
 * Типы фильтрации.
 */
@AllArgsConstructor
@Getter
public enum FilterMode {
    ALL(wish -> wish.getWish()!=null),
    PRIORITY(w->w.getPriority()==1),
    DEFAULT(null);

    private final Predicate<Wish> filterPredicate;
}

