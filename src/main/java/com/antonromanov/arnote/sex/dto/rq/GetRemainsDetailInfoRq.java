package com.antonromanov.arnote.sex.dto.rq;

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
