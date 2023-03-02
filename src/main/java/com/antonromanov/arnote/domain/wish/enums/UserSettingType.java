package com.antonromanov.arnote.domain.wish.enums;

import com.antonromanov.arnote.domain.investing.dto.common.InvestingFilterMode;
import com.antonromanov.arnote.old.model.investing.InvestingSortMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы пользовательских настроек (фильтры, сортировки)
 */
@AllArgsConstructor
@Getter
public enum UserSettingType {
    FILTER("filter", FilterMode.class),
    SORT("sort", SortMode.class),
    INVEST_FILTER("invest-filter", InvestingFilterMode.class),
    INVEST_SORT("invest-sort",InvestingSortMode .class);

    private final String name;
    private final Class<?> clazz;
}
