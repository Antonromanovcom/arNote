package com.antonromanov.arnote.model.investing.external.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы схем у API
 */

@Getter
@AllArgsConstructor
public enum Schemas {
    HTTP("http"),
    HTTPS("https");

    private final String schema;
}
