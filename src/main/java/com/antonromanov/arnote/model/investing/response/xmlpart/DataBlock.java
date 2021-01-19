package com.antonromanov.arnote.model.investing.response.xmlpart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataBlock implements UrlQueryParameters {
    SECURITIES("securities"), BOARDS("boards");
    private final String code;
}
