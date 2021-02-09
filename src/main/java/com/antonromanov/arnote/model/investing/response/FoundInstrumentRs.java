package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Найденные по ключевому слову инструменты.
 */
@AllArgsConstructor
@Data
@Builder
public class FoundInstrumentRs {
    private final String ticker;
    private final String description;
    private final Currencies currencies;
    private final BondType type;
    private final StockExchange stockExchange;
}
