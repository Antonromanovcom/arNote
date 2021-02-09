package com.antonromanov.arnote.model.investing.response.xmlpart.enums;

import com.antonromanov.arnote.model.investing.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataBlock implements UrlQueryParameters {
    SECURITIES("securities"), BOARDS("boards"), MARKETDATA("marketdata");
    private final String code;
}
