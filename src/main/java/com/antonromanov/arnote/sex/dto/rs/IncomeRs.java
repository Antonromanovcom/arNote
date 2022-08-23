package com.antonromanov.arnote.sex.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.ALWAYS)
public class IncomeRs {
    Long id; // income ID
    String incomeDescription; // описание
    Boolean isBonus; // Бонус?
    Date incomeDate; // дата платежа
    Integer amount; // дата платежа
}
