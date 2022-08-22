package com.antonromanov.arnote.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

/**
 * DTO для добавления нового кредита
 */
@Data
@Builder
@AllArgsConstructor
public class CreditRq {
    private Integer startAmount; // Общая сумма кредита
    private Integer fullPayPerMonth; // Общий платеж по кредиту
    private Integer realPayPerMonth; // Сколько уходит на погашение кредита после вычета процентов
    private Date startDate; // Дата взятия кредита
    private String desc; // Описание чтобы как-то отличать кредиты
    private Long id;
    private Integer slotNumber;
}
