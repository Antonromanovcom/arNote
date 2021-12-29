package com.antonromanov.arnote.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;


/**
 * Деталка по доходам.
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CurrentIncomeRs {
    Integer salary; // итого
    List<IncomeRs> incomeList; // список доходов
}
