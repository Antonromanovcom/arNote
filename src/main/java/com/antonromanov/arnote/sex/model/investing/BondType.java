package com.antonromanov.arnote.sex.model.investing;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип бумаги - фонд, акция, облигация
 */

@Getter
public enum BondType {
    SHARE("Акция"), BOND("Облигация"), INDEX("Фонд");

    private  String description;

    BondType(String description) {
        this.description = description;
    }
}
