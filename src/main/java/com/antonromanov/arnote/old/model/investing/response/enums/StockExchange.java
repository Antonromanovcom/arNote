package com.antonromanov.arnote.old.model.investing.response.enums;

import lombok.Getter;

@Getter
public enum StockExchange {
    MOEX("moexCalculator"), SPB("foreignCalculator");

    private  String value;

    StockExchange(String value) {
        this.value = value;
    }
}
