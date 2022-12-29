package com.antonromanov.arnote.domain.finplanning.loan.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO для добавления нового кредита
 */
@Data
@Builder
@AllArgsConstructor
public class CreditRq {
    @NotNull
    private Integer startAmount; // Общая сумма кредита

    @NotNull
    private Integer fullPayPerMonth; // Общий платеж по кредиту

    @NotNull
    private Integer realPayPerMonth; // Сколько уходит на погашение кредита после вычета процентов

    @NotNull
    private Date startDate; // Дата взятия кредита
    private String desc; // Описание чтобы как-то отличать кредиты
    private Long id;

    @Max(5)
    private Integer slotNumber;
}
