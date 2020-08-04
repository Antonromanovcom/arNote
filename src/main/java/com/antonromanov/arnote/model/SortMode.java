package com.antonromanov.arnote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Режим сортировки.
 */
@AllArgsConstructor
@Getter
public enum SortMode {
    NAME("Сортировка по имени", "name"),
    PRICE_ASC("Сортировка по возрастанию стоимости", "price-asc"),
    PRICE_DESC("Сортировка по убыванию стоимости", "price-desc"),
    ALL("Без сортировки", "all");

    private String description;
    private String uiValue;
}
