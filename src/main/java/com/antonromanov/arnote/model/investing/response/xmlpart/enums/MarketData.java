package com.antonromanov.arnote.model.investing.response.xmlpart.enums;

import com.antonromanov.arnote.model.investing.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MarketData implements UrlQueryParameters {
    SECID("SECID"), YIELD("YIELD"), DURATION("DURATION");
    private final String code;
}
