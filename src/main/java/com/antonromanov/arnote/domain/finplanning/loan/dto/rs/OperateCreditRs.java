package com.antonromanov.arnote.domain.finplanning.loan.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Ответ при добавлении кредита.
 */

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperateCreditRs {
    private int creditsCount;
    private int creditNumber;
}
