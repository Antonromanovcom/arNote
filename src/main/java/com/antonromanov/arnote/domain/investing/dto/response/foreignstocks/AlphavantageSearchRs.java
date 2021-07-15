package com.antonromanov.arnote.domain.investing.dto.response.foreignstocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ сервиса Alphavantage по поиску бумаги.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlphavantageSearchRs {

    @JsonProperty("1. symbol")
    private String symbol;

    @JsonProperty("2. name")
    private String name;

    @JsonProperty("3. type")
    private String type;

    @JsonProperty("4. region")
    private String region;

    @JsonProperty("5. marketOpen")
    private String marketOpen;

    @JsonProperty("6. marketClose")
    private String marketClose;

    @JsonProperty("7. timezone")
    private String timezone;

    @JsonProperty("8. currency")
    private String currency;

    @JsonProperty("9. matchScore")
    private String matchScore;



}
