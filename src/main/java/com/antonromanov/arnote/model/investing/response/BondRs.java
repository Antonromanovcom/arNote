package com.antonromanov.arnote.model.investing.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Респонс по ценной бумаге
 */
@Data
@Builder
@AllArgsConstructor
public class BondRs {
    private final String ticker;
    private final Double currentPrice;
    private final ConsolidatedDividendsRs dividends;
    private final Integer minLot; // минимальный лот
    private final Integer finalPrice; // currentPrice * minLot
}
