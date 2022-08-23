package com.antonromanov.arnote.domain.investing.dto.response;

/**
 * Дельта изменения цены бумаги за период истории доступный на бирже.
 */
/*@Data
@Builder
@AllArgsConstructor*/
public class DeltaRs {

    /**
     * Дельта изменения бумаги в рублях. Берутся данные с биржи за максимально доступный диапазон, берется самая
     * ранняя ранняя бумага и ее цена. Потом считаем текущая цена минус самая ранняя
     */
  /*  @JsonSerialize(using = DoubleSerializer.class)
    private final Double deltaInRubles; //todo: переименовать в более внятное на беке и фронте*/

    /**
     * Два варианта расчета в зависимости от переключателя deltaToggle у ArNoteUser:
     *
     * 1) TINKOFF_DELTA
     *
     * Дельта изменения бумаги в рублях по формуле как в приложении Тинькофф Инвестиции: (P*Sl)-(S(p*l)), где
     *
     *
     * (S(p*l)) - сумма всех покупок, где цена покупки на момент покупки перемножена на размер лота
     * P - текущая цена бумаги сейчас
     * Sl - общий размер (сумма) приобретенных лотов
     *
     *
     * 2) CANDLE_DELTA
     *
     * Формула расчета = (цена текущая - цена закрытия вчера) * кол-во акций в портфеле
     *
     */
  /*  @JsonSerialize(using = DoubleSerializer.class)
    private final Double tinkoffDelta; //todo: переименовать в более внятное на беке и фронте*/

    /**
     * tinkoffDelta в процентах от текущей цены.
     */
   /* @JsonProperty("percent")
    @JsonSerialize(using = DoubleSerializer.class)
    private final Double tinkoffDeltaPercent; //todo: переименовать в более внятное на беке и фронте*/

    /**
     * Дельта изменения бумаги в рублях. Берутся данные с биржи за максимально доступный диапазон, берется самая
     * ранняя ранняя бумага. Далее - количество дней в миллисекундах от текущей даты до найденной.
     */
//    private final Long deltaPeriod; // todo: тоже путаешься с названием - это оказывается про время а не про деньги


    /**
     * Общий процент. То есть на сколько процентов произошло изменение с начала истории.
     */
//    @JsonSerialize(using = DoubleSerializer.class)
//    private final Double totalPercent;
}
