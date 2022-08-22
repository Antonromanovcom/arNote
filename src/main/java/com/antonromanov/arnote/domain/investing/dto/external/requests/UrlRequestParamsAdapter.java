package com.antonromanov.arnote.domain.investing.dto.external.requests;

import com.antonromanov.arnote.domain.investing.dto.response.serializers.IssMetaSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Параметры URL (которые после ? идут)
 */
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlRequestParamsAdapter {
    @JsonSerialize(using = IssMetaSerializer.class)
    @JsonProperty("iss.meta")
    private Boolean issMeta;
    @JsonProperty("iss.dp")
    private String issDp;
    @JsonProperty("iss.only")
    private String issOnly;
    @JsonProperty("securities.columns")
    private String securitiesColumns;
    @JsonProperty("boards.columns")
    private String boardsColumns;
    @JsonProperty("marketdata.columns")
    private String marketDataColumns;
    private String from;

}
