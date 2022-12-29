package com.antonromanov.arnote.domain.finplanning.loan.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Консолидированное хранилище кредитов.
 *
 */

@Data
@Builder
@AllArgsConstructor
public class CalculatedLoansTableTr {
    List<LinkedHashMap<LocalDate, LoanListTr>> calculatedLoansList;
}
