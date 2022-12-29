package com.antonromanov.arnote.old.model.investing;

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
