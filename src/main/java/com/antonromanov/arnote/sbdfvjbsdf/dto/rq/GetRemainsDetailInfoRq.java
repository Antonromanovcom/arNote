package com.antonromanov.arnote.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * DTO для запроса деталки по остаткам.
 */
@Data
@AllArgsConstructor
public class GetRemainsDetailInfoRq {
    private Integer month; // Месяц
    private Integer year; // Год
}
