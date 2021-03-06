package com.antonromanov.arnote.model.investing.response.xmlpart.enums;

import com.antonromanov.arnote.model.investing.response.xmlpart.UrlQueryParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecuritiesColumns implements UrlQueryParameters {
    SECID("SECID"), PREVADMITTEDQUOTE("PREVADMITTEDQUOTE"), SECNAME("SECNAME"),
    PREVLEGALCLOSEPRICE("PREVLEGALCLOSEPRICE"), COUPONVALUE("COUPONVALUE"),
    COUPONPERCENT("COUPONPERCENT"), LOTVALUE("LOTVALUE"), COUPONPERIOD("COUPONPERIOD"),
    CURRENCYID("CURRENCYID"), LOTSIZE("LOTSIZE"), NEXTCOUPON("NEXTCOUPON"), FACEUNIT("FACEUNIT");

    private final String code;
}
