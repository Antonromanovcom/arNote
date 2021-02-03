package com.antonromanov.arnote.model.investing;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Режим сортировки.
 */
@AllArgsConstructor
@Getter
public enum InvestingSortMode {
    TICKER("Сортировка по тикеру", null, "TICKER"),
    NONE("Без сортировки", null, null);

    private final String description;
    private final String comparator;
    private final String type;
}
