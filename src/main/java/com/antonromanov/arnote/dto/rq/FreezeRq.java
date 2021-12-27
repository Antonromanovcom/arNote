package com.antonromanov.arnote.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

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
