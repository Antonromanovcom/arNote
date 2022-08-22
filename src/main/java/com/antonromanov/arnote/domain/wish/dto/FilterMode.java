package com.antonromanov.arnote.domain.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.Predicate;

/**
 * Режим фильтрации для желаний.
 */
@AllArgsConstructor
@Getter
public enum FilterMode {
    ALL("Все желания", wish -> wish.getWish()!=null),
    PRIOR("Только приоритетные", w->w.getPriority()==1),
    NONE("Без фильтрации", wish -> wish.getWish()!=null); //todo: удалить потом

    private final String description;
    private final Predicate<Wish> filterPredicate;
}
