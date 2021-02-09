package com.antonromanov.arnote.model.investing.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private final Long id;
    private final String ticker;
    private final Double currentPrice;
    private final String currency;
    private final String type;
    private final ConsolidatedDividendsRs dividends;
    private final Integer minLot; // минимальный лот или сколько куплено уже пользователем
    private final Integer finalPrice; // currentPrice * minLot
    private final String description; // описание бумаги
    private final DeltaRs delta; // доступный в истории биржи период дельты
    private final String stockExchange; // биржа
    private Boolean isBought; // факт / План
}
