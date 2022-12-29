package com.antonromanov.arnote.old.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для запроса деталки по остаткам.
 */
@Data
@AllArgsConstructor
public class GetRemainsDetailInfoRq {
    private Integer month; // Месяц
    private Integer year; // Год
}
