package com.antonromanov.arnote.domain.finplanning.loan.dto.transfer;

import com.antonromanov.arnote.old.dto.transfer.LoanTr;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;

/**
 * Список кредитов.
 *
 */

@Data
@Builder
@AllArgsConstructor
public class LoanListTr {
    private ArrayList<LoanTr> loanList;
}
