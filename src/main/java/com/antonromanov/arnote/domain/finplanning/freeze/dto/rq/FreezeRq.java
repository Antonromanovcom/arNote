package com.antonromanov.arnote.domain.finplanning.freeze.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для добавления нового фриза.
 */
@Data
@Builder
@AllArgsConstructor
public class FreezeRq {
    private Integer amount;
    private Integer year;
    private Integer month;
}
