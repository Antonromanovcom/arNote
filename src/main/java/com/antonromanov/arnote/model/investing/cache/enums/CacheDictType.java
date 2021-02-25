package com.antonromanov.arnote.model.investing.cache.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип справочников, которые кладем в кэш.
 */
@AllArgsConstructor
@Getter
public enum CacheDictType {
    BOARD_ID_BY_TICKER, LAST_QUOTES_BY_BOARD_ID;
}
