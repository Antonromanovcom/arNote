package com.antonromanov.arnote.model.wish.enums;

import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.BondRs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.Predicate;

/**
 * Режим фильтрации для желаний.
 */
@AllArgsConstructor
@Getter
public enum WishsFilters {
    ALL("Все желания", s-> BondType.valueOf(s.getType())==BondType.SHARE),
    PRIOR("Только приоритетные", s->BondType.valueOf(s.getType())==BondType.BOND),
    NONE("Без фильтрации", null);

    private final String description;
    private final Predicate<BondRs> filter;
}
