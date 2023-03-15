package com.antonromanov.arnote.domain.investing.dto.response;

import com.antonromanov.arnote.domain.investing.dto.common.BondType;
import com.antonromanov.arnote.domain.investing.dto.response.enums.Currencies;
import com.antonromanov.arnote.domain.investing.dto.response.enums.StockExchange;
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
