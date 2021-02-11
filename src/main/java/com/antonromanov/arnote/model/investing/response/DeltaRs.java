package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.serializers.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Дельта изменения цены бумаги за период истории доступный на бирже.
 */
@Data
@Builder
@AllArgsConstructor
public class DeltaRs {

    /**
     * Дельта изменения бумаги в рублях. Берутся данные с биржи за максимально доступный диапазон, берется самая
     * ранняя ранняя бумага и ее цена. Потом считаем текущая цена минус самая ранняя
     */
    @JsonSerialize(using = DoubleSerializer.class)
    private final Double deltaInRubles;
    /**
     * Дельта изменения бумаги в рублях по формуле как в приложении Тинькофф Инвестиции: (P*Sl)-(S(p*l)), где
     *
     * (S(p*l)) - сумма всех покупок, где цена покупки на момент покупки перемножена на размер лота
     * P - текущая цена бумаги сейчас
     * Sl - общий размер (сумма) приобретенных лотов
     */
    @JsonSerialize(using = DoubleSerializer.class)
    private final Double tinkoffDelta;

    /**
     * tinkoffDelta в процентах от текущей цены.
     */
    @JsonProperty("percent")
    @JsonSerialize(using = DoubleSerializer.class)
    private final Double tinkoffDeltaPercent;

    /**
     * Дельта изменения бумаги в рублях. Берутся данные с биржи за максимально доступный диапазон, берется самая
     * ранняя ранняя бумага. Далее - количество дней в миллисекундах от текущей даты до найденной.
     */
    private final Long deltaPeriod;


    /**
     * Общий процент. То есть на сколько процентов произошло изменение с начала истории.
     */
    @JsonSerialize(using = DoubleSerializer.class)
    private final Double totalPercent;
}
