package com.antonromanov.arnote.sex.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;


/**
 * Кредит закрытый
 *
 */
@Data
@Builder
@AllArgsConstructor
public class ClosedLoanTr {
    LocalDate startDate;
    LocalDate closeDate; // дата закрытия
    Integer loanNumber;
}
