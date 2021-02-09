package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReturnsPerMonthRs {

    private final String registryCloseDate; // дата закрытия регистра
    private final Double value; // цена, размер дивиденда
    private final Currencies currencyId; // валюта
    private final String ticker;
    private final BondType type;
}
