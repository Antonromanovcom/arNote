package com.antonromanov.arnote.old.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Деталка по доходам.
 *
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CurrentIncomeRs {
    Integer salary; // итого
    List<IncomeRs> incomeList; // список доходов
}
