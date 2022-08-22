package com.antonromanov.arnote.domain.investing.dto.response.foreignstocks.alphaadvantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ДТО для информации о бумаге.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOverviewRs {

    @JsonProperty(value = "Symbol")
    private String ticker;
    @JsonProperty(value = "AssetType")
    private String assetType;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Description")
    private String description;
    @JsonProperty(value = "Exchange")
    private String exchange;
    @JsonProperty(value = "Currency")
    private String currency;
    @JsonProperty(value = "Country")
    private String country;
    @JsonProperty(value = "Sector")
    private String sector;
    @JsonProperty(value = "Industry")
    private String industry;
}
