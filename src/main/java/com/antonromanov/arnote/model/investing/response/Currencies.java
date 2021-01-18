package com.antonromanov.arnote.model.investing.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currencies {
    RUB("RUB");

    private final String code;
}
