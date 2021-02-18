package com.antonromanov.arnote.model.investing.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockExchange {
    MOEX("sharesCalculator"), SPB("none");

    private final String value;
}
