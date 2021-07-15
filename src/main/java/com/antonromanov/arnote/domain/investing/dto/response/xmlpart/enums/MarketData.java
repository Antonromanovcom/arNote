package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.enums;

import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MarketData implements UrlQueryParameters {
    SECID("SECID"), YIELD("YIELD"), DURATION("DURATION"), BOARDID("BOARDID"), LAST("LAST"),
    UPDATETIME("UPDATETIME"), LASTCHANGE("LASTCHANGE"), LASTCHANGEPRCNT("LASTCHANGEPRCNT"), LCURRENTPRICE("LCURRENTPRICE");
    private final String code;
}
