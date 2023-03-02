package com.antonromanov.arnote.domain.investing.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип бумаги - фонд, акция, облигация
 */
@AllArgsConstructor
@Getter
public enum BondType {
    SHARE("Акция"), BOND("Облигация"), INDEX("Фонд");

    private final String description;
}
