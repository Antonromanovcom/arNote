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
public class YahooQuoteSummeryElementRs {
    private YahooQuotePriceInfoRs price;
}
