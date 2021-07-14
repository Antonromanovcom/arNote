package com.antonromanov.arnote.model.investing.response;


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
public class DivsDetailsRs {
    List<DivsDebug> divs;
    Double sum;
}
