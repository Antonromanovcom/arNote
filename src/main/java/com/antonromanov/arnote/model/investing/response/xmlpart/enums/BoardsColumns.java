package com.antonromanov.arnote.model.investing.response.xmlpart.enums;

import com.antonromanov.arnote.model.investing.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardsColumns implements UrlQueryParameters {
    SECID("secid"), BOARDID("boardid"), IS_PRIMARY("is_primary");

    private final String code;
}
