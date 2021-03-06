package com.antonromanov.arnote.model.wish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Comparator;

/**
 * Режим сортировки.
 */
@AllArgsConstructor
@Getter
public enum SortMode {
    NAME("Сортировка по имени", "name", Comparator.comparing(Wish::getWish)),
    PRICE_ASC("Сортировка по возрастанию стоимости", "price-asc", Comparator.comparing(Wish::getPrice)),
    PRICE_DESC("Сортировка по убыванию стоимости", "price-desc", Comparator.comparing(Wish::getPrice).reversed()),
    PRIOR("Сортировка приоритету", "prior-asc", Comparator.comparing(Wish::getPriority)),
    ALL("Без сортировки", "all", Comparator.comparing(Wish::getId));

    private final String description;
    private final String uiValue;
    private final Comparator<Wish> compareInstrument;
}
