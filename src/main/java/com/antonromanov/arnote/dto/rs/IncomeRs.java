package com.antonromanov.arnote.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;


/**
 * Деталка по доходам.
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
public class IncomeRs {
    Long id; // income ID
    String incomeDescription; // описание
    Boolean isBonus; // Бонус?
    Date incomeDate; // дата платежа
    Integer amount; // дата платежа
}
