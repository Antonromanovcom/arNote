package com.antonromanov.arnote.domain.investing.dto.cache.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип справочников, которые кладем в кэш.
 */
@AllArgsConstructor
@Getter
public enum CacheDictType {
    BOARD_ID_BY_TICKER, LAST_QUOTES_BY_BOARD_ID, DIVS_BY_TICKER, BONDS_BY_BOARD_ID, HISTORY, TRADE_MODES, CURRENCY,
    REALTIME_QUOTES, INSTRUMENT_NAME, CURRENCY_MULTIPLIER, FIND_SHARES_BY_BOARD_ID, REALTIME_QUOTES_WITH_RETENTION;
}
