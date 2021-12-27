package com.antonromanov.arnote.dto.transfer;

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
    /*Integer fullPayPerMonth;
    Integer realPayPerMonth;
    Integer creditNumber;
    Date startDate;
    String description;*/
}