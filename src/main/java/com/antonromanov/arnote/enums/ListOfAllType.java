package com.antonromanov.arnote.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Режим вывода списка всех желаний: все или только приоритетные.
 */
@AllArgsConstructor
@Getter
public enum ListOfAllType {
    ALL("Все желания", "all"),
    PRIORITY("Только приоритетные желания", "priority"),
    DEFAULT("Предыдущая сортировка пользователя либо ALL", "default");

    private final String description;
    private final String uiValue;
}
