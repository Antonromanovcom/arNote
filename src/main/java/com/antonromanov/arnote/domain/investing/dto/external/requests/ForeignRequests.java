package com.antonromanov.arnote.domain.investing.dto.external.requests;

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
    GET_DIVS("Получить дивиденды", Schemas.HTTPS, ForeignRequestsApiType.YAHOO_FINANCE,
            "/v8/finance/chart/{p1:[a-z]{1,5}}",
            ForeignUrlRequestParams.builder()
                    .symbol("{p1:[a-z]{1,5}}")
                    .period1("{p2:[a-z]{1,5}}")
                    .period2("{p3:[a-z]{1,5}}")
                    .interval("{p4:[a-z]{1,5}}")
                    .includePrePost("{p5:[a-z]{1,5}}")
                    .events("{p6:[a-z]{1,5}}")
                    .build()),
    GET_HISTORY("Запросить историю по ставкам", Schemas.HTTPS, ForeignRequestsApiType.YAHOO_FINANCE,
            "/v8/finance/chart/{p1:[a-z]{1,5}}",
            ForeignUrlRequestParams.builder()
            .symbol("{p1:[a-z]{1,5}}")
            .period1("{p2:[a-z]{1,5}}")
            .period2("{p3:[a-z]{1,5}}")
            .interval("{p4:[a-z]{1,5}}")
            .build()),
    GET_INSTRUMENT_NAME("Запросить название бумаги", Schemas.HTTPS, ForeignRequestsApiType.ALFA_ADVANTAGE,
            "query",
            ForeignUrlRequestParams.builder()
                    .symbol("{p1:[a-z]{1,5}}")
                    .function("{p2:[a-z]{1,5}}")
                    .apikey("{p3:[a-z]{1,5}}")
                    .build()),
    FIND_INSTRUMENT("Найти бумагу по имени", Schemas.HTTPS, ForeignRequestsApiType.ALFA_ADVANTAGE,
            "query",
                        ForeignUrlRequestParams.builder()
                    .keywords("{p1:[a-z]{1,5}}")
                    .function("{p2:[a-z]{1,5}}")
                    .apikey("{p3:[a-z]{1,5}}")
                    .build());

    private final String description;
    private final Schemas schema;
    private final ForeignRequestsApiType host;
    private final String constantPart;
    private final ForeignUrlRequestParams requestParams;
}
