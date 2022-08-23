package com.antonromanov.arnote.sex.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * DTO для выдачи списка кредитов на фронт.
 */
@Data
@Builder
@AllArgsConstructor
public class FullLoansListRs {
    private List<FullLoanRs> loansList;
}
