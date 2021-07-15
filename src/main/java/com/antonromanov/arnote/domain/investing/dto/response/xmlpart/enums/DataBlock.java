package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.enums;

import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataBlock implements UrlQueryParameters {
    SECURITIES("securities"), BOARDS("boards"), MARKETDATA("marketdata");
    private final String code;
}
