package com.antonromanov.arnote.old.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;


/**
 * Деталка по доходам.
 *
 *
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class IncomeRs {
    Long id; // income ID
    String incomeDescription; // описание
    Boolean isBonus; // Бонус?
    Date incomeDate; // дата платежа
    Integer amount; // дата платежа
}
