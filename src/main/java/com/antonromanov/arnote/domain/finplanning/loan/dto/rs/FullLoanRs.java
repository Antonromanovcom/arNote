package com.antonromanov.arnote.domain.finplanning.loan.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

/**
 * DTO для выдачи списка кредитов на фронт.
 */
@Data
@Builder
@AllArgsConstructor
public class FullLoanRs {
    private Long id;
    private String description;
    private Integer fullPayPerMonth; // Общий платеж по кредиту
    private Integer realPayPerMonth; // Сколько уходит на погашение кредита после вычета процентов
    private Date startDate; // Дата взятия кредита
    private Integer startAmount; // Дата взятия кредита
    private Integer number; // номер кредита
}
