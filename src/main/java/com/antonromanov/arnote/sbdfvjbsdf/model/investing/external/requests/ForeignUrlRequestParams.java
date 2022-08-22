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
    private String symbol;
    private String period1;
    private String period2;
    private String interval;
    private String includePrePost;
    private String events;
    private String function;
    private String apikey;
    private String keywords;
}
