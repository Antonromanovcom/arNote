package com.antonromanov.arnote.common.exceptions.enums;

import lombok.Getter;

/**
 * Коды ошибок (в будущем - под рефакторинг)
 */
@Getter
public enum ErrorCodes {
    ERR_O1("ERR-01", "У пользователя еще нет желаний", null), //todo: uiCode точно нужен????
    ERR_O2("ERR-02", "Ошибка загрузки зарплат или их просто нет", 5004),
    ERR_O3("ERR-03", "У пользователя еще нет ценных бумаг", null),
    ERR_O4("ERR-04", "Максимальное число возможных кредитов - 5!", 5001),
    ERR_O5("ERR-05", "Доходность в этом месяце уже заведена. Отредактируйте ее", 5002),
    ERR_O6("ERR-06", "SQL ошибка", 5003),
    ERR_O7("ERR-07", "На выходной день или праздник нет торгов!", 5005),
    ERR_O8("ERR-08", "Нет данных по торгам на эту дату!", 5006),
    ERR_O9("ERR-09", "Пользователь не найден", 5006),
    ERR_10("ERR-10", "Неверные входные данные", 5007),
    ERR_11("ERR-11", "Логин занят!", 5008),
    ERR_12("ERR-12", "Ошибка построения консолидированной таблицы фин-планирования!", 5009),
    ERR_13("ERR-13", "Ошибка удаления кредита", 5010),
    ERR_14("ERR-14", "Ошибка получения консолидированной таблицы!", 5011),
    ERR_15("ERR-15", "Ошибка получения данных по кредиту!", 5012),
    ERR_16("ERR-16", "Фриз в этом месяце уже есть!", 5013);

    private final String uiCode; //код, который потребляет фронт на текущий момент - потом переделаем
    private final String description; // Описание пока чисто для меня
    private final Integer digitalCode; // числовой код

    ErrorCodes(String uiCode, String description, Integer digitalCode) {
        this.uiCode = uiCode;
        this.description = description;
        this.digitalCode = digitalCode;
    }
}
