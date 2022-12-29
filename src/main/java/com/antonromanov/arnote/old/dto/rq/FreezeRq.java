package com.antonromanov.arnote.old.dto.rq;

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
