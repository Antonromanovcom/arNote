package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DividendRs {
    private final String registryCloseDate; // дата закрытия регистра
    private final Double value; // цена, размер дивиденда
    private final Currencies currencyId; // валюта
}
