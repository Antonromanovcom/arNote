package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.serializers.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Текущая цена и минимальный лот.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CurrentPriceRs {
   @JsonSerialize(using = DoubleSerializer.class)
   private Double currentPrice;
   private String ticker;
   private Currencies currency;
   private LocalDate date; // дата торгов с которой взята цена
   private LocalTime time; // таймстемп ставки (точнее обновления)
   @JsonSerialize(using = DoubleSerializer.class)
   private Double lastChange; // изменение в рублях
   @JsonSerialize(using = DoubleSerializer.class)
   private Double lastChangePrcnt; // изменение в процентах
   private int minLot; // минимальный лот
}
