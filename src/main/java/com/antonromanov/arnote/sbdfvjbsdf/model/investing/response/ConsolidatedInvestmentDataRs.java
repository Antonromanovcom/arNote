package com.antonromanov.arnote.model.investing.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Консолидированная инвест-таблица.
 */
@Data
@Builder
@AllArgsConstructor
public class ConsolidatedInvestmentDataRs {
    private List<BondRs> bonds;
}
