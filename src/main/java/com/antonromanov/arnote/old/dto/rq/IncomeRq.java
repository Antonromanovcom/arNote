package com.antonromanov.arnote.old.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

/**
 * DTO для добавления нового дохода
 */
@Data
@Builder
@AllArgsConstructor
public class IncomeRq {
    private Integer income; // Сколько заработали в данный месяц / размер бонуса
    private Boolean isBonus; // Это годовая премия?
    private Date incomeDate; //  Дата для понимания месяца и года прихода
    private String desc; // Описание
    private Long id;
}
