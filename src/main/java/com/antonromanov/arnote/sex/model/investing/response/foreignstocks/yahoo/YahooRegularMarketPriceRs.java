package com.antonromanov.arnote.model.investing.response.foreignstocks.yahoo;


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
public class YahooRegularMarketPriceRs {
    private Double raw;
    private String fmt;
}
