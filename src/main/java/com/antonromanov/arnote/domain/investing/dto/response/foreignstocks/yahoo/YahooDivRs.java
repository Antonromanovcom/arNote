package com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.yahoo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ДТО для ответа по дивам от Яху.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YahooDivRs {
    private Double amount; // значение
    private LocalDate date; // дата
}
