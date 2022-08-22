package com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.yahoo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ДТО для ответа по текущей ставке от Яху
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YahooQuotePriceInfoRs {
    private YahooRegularMarketPriceRs regularMarketPrice;
    private YahooRegularMarketPriceRs regularMarketChangePercent;
    private YahooRegularMarketPriceRs regularMarketChange;
    private String exchangeName; // биржа
    private String marketState; // статус биржи: открыта / закрыта
    private String quoteType; // тип бумаги, акция или облигация. EQUITY = акция
    private String shortName; // короткое название
    private String longName; // длинное название
    private String currency; // валюта
    private String currencySymbol; // символ валюты
    private Long regularMarketTime; // тайстемп последней ставки
}
