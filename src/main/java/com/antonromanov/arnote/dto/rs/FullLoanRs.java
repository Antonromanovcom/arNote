package com.antonromanov.arnote.dto.rs;

import com.antonromanov.arnote.entity.finplan.Credit;
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

    public static FullLoanRs $fromCredit(CreditRs loan){
        return FullLoanRs.builder()
                .startAmount(loan.getAmount())
                .startDate(loan.getStartDate())
                .realPayPerMonth(loan.getRealPayPerMonth())
                .fullPayPerMonth(loan.getFullPayPerMonth())
                .description(loan.getDescription())
                .id(loan.getId())
                .number(loan.getNumber())
                .build();
    }
}