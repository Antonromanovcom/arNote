package com.antonromanov.arnote.model.investing.response.xmlpart.enums;

import com.antonromanov.arnote.model.investing.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecuritiesColumns implements UrlQueryParameters {
    SECID("SECID"), PREVADMITTEDQUOTE("PREVADMITTEDQUOTE"), SECNAME("SECNAME");

    private final String code;
}
