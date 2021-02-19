package com.antonromanov.arnote.model.investing.external.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Параметры URL для буржуйских API (которые после ? идут).
 */
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForeignUrlRequestParams {

    private String modules;

    /*public UrlRequestParamsAdapter convertByAdapter(){
        return UrlRequestParamsAdapter
                .builder()
                .issMeta(this.issMeta)
                .issDp(this.issDp)
                .from(this.from)
                .issOnly(convertEnumSet(this.issOnly, (UrlQueryParameters::getCode)))
                .securitiesColumns(convertEnumSet(this.securitiesColumns, (UrlQueryParameters::getCode)))
                .boardsColumns(convertEnumSet(this.boardsColumns, (UrlQueryParameters::getCode)))
                .marketDataColumns(convertEnumSet(this.marketDataColumns, (UrlQueryParameters::getCode)))
                .build();
    }*/
}
