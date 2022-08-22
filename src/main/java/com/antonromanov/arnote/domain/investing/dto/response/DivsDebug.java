package com.antonromanov.arnote.domain.investing.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Консолидированная таблица по доходности.
 */
@Data
@Builder
@AllArgsConstructor
public class DivsDebug {
    private String ticker;
    private List<DividendRs> divs;
}
