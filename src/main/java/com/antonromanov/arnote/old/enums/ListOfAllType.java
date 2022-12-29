package com.antonromanov.arnote.old.enums;

import lombok.Getter;

/**
 * Режим вывода списка всех желаний: все или только приоритетные.
 */

@Getter
public enum ListOfAllType {
    ALL("Все желания", "all"),
    PRIORITY("Только приоритетные желания", "priority"),
    DEFAULT("Предыдущая сортировка пользователя либо ALL", "default");

    private final String description;
    private final String uiValue;

    ListOfAllType(String description, String uiValue) {
        this.description = description;
        this.uiValue = uiValue;
    }
}
