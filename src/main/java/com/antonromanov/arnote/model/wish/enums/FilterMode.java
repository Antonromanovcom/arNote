package com.antonromanov.arnote.model.wish.enums;

import com.antonromanov.arnote.model.wish.Wish;
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
    NONE("Без фильтрации", wish -> true);

    private final String description;
    private final Predicate<Wish> filterPredicate;
}
