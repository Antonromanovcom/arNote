package com.antonromanov.arnote.domain.investing.dto.response;

import com.antonromanov.arnote.domain.investing.dto.response.enums.Currencies;
import com.antonromanov.arnote.domain.investing.dto.response.serializers.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*@Data
@AllArgsConstructor
@Builder*/
public class DividendRs {
   /* private final String registryCloseDate; // дата закрытия регистра
    @JsonSerialize(using = DoubleSerializer.class)
    private final Double value; // цена, размер дивиденда
    private final Currencies currencyId; // валюта*/
}
