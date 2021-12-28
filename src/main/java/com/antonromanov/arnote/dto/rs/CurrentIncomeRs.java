package com.antonromanov.arnote.dto.rs;

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
public class CurrentIncomeRs {
    Integer salary; // итого
    List<IncomeRs> incomeList; // список доходов
}
