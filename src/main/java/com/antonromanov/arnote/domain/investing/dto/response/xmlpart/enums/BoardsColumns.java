package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.enums;

import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardsColumns implements UrlQueryParameters {
    SECID("secid"), BOARDID("boardid"), IS_PRIMARY("is_primary");

    private final String code;
}
