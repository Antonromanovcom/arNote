package com.antonromanov.arnote.domain.finplanning.loan.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



/**
 * Кредит
 *
 */

@Data
@Builder
@AllArgsConstructor
public class LoanTr {
    Long loanId;
    Integer amount;
}
