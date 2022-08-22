package com.antonromanov.arnote.domain.investing.dto.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockExchange {
    MOEX("moexCalculator"), SPB("foreignCalculator");

    private final String value;
}
