package com.antonromanov.arnote.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Данные по кредитам
 */
@Data
@Builder
@AllArgsConstructor
public class CreditRs {
    private Long id;
    private Integer number;
    private Integer amount;
    private String description;
    private Date startDate;
    private Integer fullPayPerMonth; // Общий платеж по кредиту
    private Integer realPayPerMonth; // Сколько уходит на погашение кредита после вычета процентов
}
