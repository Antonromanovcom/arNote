package com.antonromanov.arnote.domain.wish.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы пользовательских настроек (фильтры, сортировки)
 */
@AllArgsConstructor
@Getter
public enum UserSettingType {
    FILTER("filter", FilterMode.class),
    SORT("sort", SortMode.class);

    private final String name;
    private final Class<?> clazz;
}
