package com.antonromanov.arnote.model.investing.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Енум для возвращения рассчетных значений дельты по Тиньковским лекалам одной Мапой.
 */
@AllArgsConstructor
@Getter
public enum TinkoffDeltaFinalValuesType {
    DELTA_FINAL, DELTA_PERCENT;
}
