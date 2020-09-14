package com.antonromanov.arnote.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Режим вывода списка всех желаний.
 */
@AllArgsConstructor
@Getter
public enum ListOfAllType {
    ALL("Все желания", "all"),
    PRIORITY("Только приоритетные желания", "priority");

    private String description;
    private String uiValue;
}
