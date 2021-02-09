package com.antonromanov.arnote.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Коды ошибок (в будущем - под рефакторинг)
 */
@Getter
@AllArgsConstructor
public enum ErrorCodes {
    ERR_O1("ERR-01", "У пользователя еще нет желаний"),
    ERR_O2("ERR-02", "У пользователя еще нет ни одной записи о зарплате"),
    ERR_O3("ERR-03", "У пользователя еще нет ценных бумаг");

    private final String uiCode; //код, который потребляет фронт на текущий момент - потом переделаем
    private final String description; // Описание пока чисто для меня

}
