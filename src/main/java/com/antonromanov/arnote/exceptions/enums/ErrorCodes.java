package com.antonromanov.arnote.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Коды ошибок (в будущем - под рефакторинг)
 */
@Getter
@AllArgsConstructor
public enum ErrorCodes {
    ERR_O1("ERR-01", "У пользователя еще нет желаний", null), //todo: uiCode точно нужен????
    ERR_O2("ERR-02", "Ошибка загрузки зарплат или их просто нет", 5004),
    ERR_O3("ERR-03", "У пользователя еще нет ценных бумаг", null),
    ERR_O4("ERR-04", "Максимальное число возможных кредитов - 5!", 5001),
    ERR_O5("ERR-05", "Доходность в этом месяце уже заведена. Отредактируйте ее", 5002),
    ERR_O6("ERR-06", "SQL ошибка", 5003),
    ERR_O7("ERR-07", "На выходной день или праздник нет торгов!", 5005),
    ERR_O8("ERR-08", "Нет данных по торгам на эту дату!", 5006);

    private final String uiCode; //код, который потребляет фронт на текущий момент - потом переделаем
    private final String description; // Описание пока чисто для меня
    private final Integer digitalCode; // числовой код

}
