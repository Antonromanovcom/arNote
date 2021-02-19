package com.antonromanov.arnote.model.investing.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockExchange {
    MOEX("moexCalculator"), SPB("foreignCalculator");

    private final String value;
}
