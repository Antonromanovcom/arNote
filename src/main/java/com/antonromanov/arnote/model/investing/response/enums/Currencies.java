package com.antonromanov.arnote.model.investing.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Currencies {
    RUB("SUR", "RUB", null),
    USD("USD", null,"USD/RUB"),
    EUR("EUR", null,"EUR/RUB");

    private final String code;
    private final String secondName; // второе имя на всякий случай
    private final String transfer; // перевод из валюты в валюты

    public static String getTransferByCodes(String name){
        return Arrays.stream(Currencies.values())
                .filter(e->e.code.equals(name) || (e.secondName!=null && e.secondName.equals(name)))
                .findFirst()
                .map(z->z.transfer)
                .orElse(null);
    }

    public static Currencies search(String name){
        return Arrays.stream(Currencies.values())
                .filter(e->e.code.equals(name) || (e.secondName!=null && e.secondName.equals(name)))
                .findFirst()
                .orElse(null);
    }
}
