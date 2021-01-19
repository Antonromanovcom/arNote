package com.antonromanov.arnote.model.investing.response.xmlpart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardsColumns implements UrlQueryParameters {
    SECID("secid"), BOARDID("boardid"), IS_PRIMARY("is_primary");

    private final String code;
}
