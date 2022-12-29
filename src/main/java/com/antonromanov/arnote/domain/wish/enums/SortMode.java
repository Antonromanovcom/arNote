package com.antonromanov.arnote.domain.wish.enums;

import com.antonromanov.arnote.domain.wish.entity.Wish;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Режим сортировки.
 */
@AllArgsConstructor
@Getter
public enum SortMode {
    NAME("Сортировка по имени", "name", Comparator.comparing(Wish::getWishName)),
    PRICE_ASC("Сортировка по возрастанию стоимости", "price-asc", Comparator.comparing(Wish::getPrice)),
    PRICE_DESC("Сортировка по убыванию стоимости", "price-desc", Comparator.comparing(Wish::getPrice).reversed()),
    PRIOR("Сортировка приоритету", "prior-asc", Comparator.comparing(Wish::getPriority)),
    ALL("Без сортировки", "all", Comparator.comparing(Wish::getId));

    private final String description;
    private final String uiValue;
    private final Comparator<Wish> wishComparator;

    public static SortMode searchByUiValue(String uiValue){
        return Arrays.stream(SortMode.values())
                .filter(e->e.getUiValue().equals(uiValue))
                .findFirst()
                .orElse(SortMode.ALL);
    }

}
