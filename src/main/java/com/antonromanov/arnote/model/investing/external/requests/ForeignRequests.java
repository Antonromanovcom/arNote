package com.antonromanov.arnote.model.investing.external.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Запросы к буржуйским API.
 */

@Getter
@AllArgsConstructor
public enum ForeignRequests {
    GET_REALTIME_QUOTE("Получить последнюю ставку в реальном времени",  Schemas.HTTPS,
            ForeignRequestsApiType.YAHOO_FINANCE, "/v10/finance/quoteSummary/{p1:[a-z]{1,5}}",
            ForeignUrlRequestParams.builder().modules("price").build()),
    MARKETSTACK("api.marketstack.com/v1", null, null, null, null),
    YAHOO_FINANCE("query1.finance.yahoo.com", null, null, null, null);

//    https://query1.finance.yahoo.com/v10/finance/quoteSummary/BOH?modules=price


    private final String description;
    private final Schemas schema;
    private final ForeignRequestsApiType host;
    private final String constantPart;
    private final ForeignUrlRequestParams requestParams;
}
