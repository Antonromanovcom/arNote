package com.antonromanov.arnote.model.investing.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
