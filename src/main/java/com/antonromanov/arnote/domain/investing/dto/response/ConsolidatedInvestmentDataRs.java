package com.antonromanov.arnote.domain.investing.dto.response;

import com.antonromanov.arnote.domain.investing.dto.response.BondRs;
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