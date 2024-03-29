package com.antonromanov.arnote.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 *  План расходов
 */
@Data
@Builder
@AllArgsConstructor
public class PurchasesRs {
    private String description; // описание
    private Integer price; // стоимость
    private Long id; // id
    private Long loanId; // id кредита, если это досрочное погашение
    private Date startDate;
}
