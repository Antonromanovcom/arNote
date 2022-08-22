package com.antonromanov.arnote.domain.investing.dto.external.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Так как для буржуйских бумаг пришлось использовать разные API, решил:
 *
 * 1. Не хранить урл в пропертях, ибо это не очень удобно и не очень очевидно с точки зрения замены всего этого
 * и передеплоить проект по джобу на самом деле проще, ибо руками действий делаешь меньше. Все делает джоб.
 *
 * 2. Разные типы API и соответственно разные типы урлов к ним (как минимум - хостов) решил хранить в енумах.
 */

@Getter
@AllArgsConstructor
public enum ForeignRequestsApiType {
    ALFA_ADVANTAGE("www.alphavantage.co"),
    MARKETSTACK("api.marketstack.com/v1"),
    YAHOO_FINANCE("query1.finance.yahoo.com");

    private final String url;
}
