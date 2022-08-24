package com.antonromanov.arnote.sex.model.wish.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Режимы дельты. Варианта два:
 *
 * 1) tinkoffDelta = (сумма покупок * текущую цену рынка) - (Сумма(лот * цену по каждой покупке))
 * 2) candleDayDelta = (цена текущая - цена закрытия вчера) * кол-во акций в портфеле
 *
 */
@AllArgsConstructor
@Getter
public enum DeltaMode {
    TINKOFF_DELTA, CANDLE_DELTA;
}
