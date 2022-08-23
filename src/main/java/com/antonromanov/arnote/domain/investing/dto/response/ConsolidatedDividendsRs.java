package com.antonromanov.arnote.domain.investing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Консолидированная инвест-таблица.
 */
/*@Data
@Builder
@AllArgsConstructor*/
public class ConsolidatedDividendsRs {
   /* private final List<DividendRs> dividendList;

    @JsonSerialize(using = DoubleSerializer.class)
    private Double divSum; // Сумма дивидендов за прошлый год
    @JsonSerialize(using = DoubleSerializer.class)
    private Double percent;*/

    /**
     * Подсчитать сумму дивидендов.
     */
   /* public void calculateSum() {
        if (this.getDividendList() != null && this.getDividendList().size() > 0) {
            this.divSum = dividendList.stream()
                    .filter(div -> (LocalDate.parse(div.getRegistryCloseDate())).getYear() == LocalDate.now().getYear() - 1)
                    .map(DividendRs::getValue)
                    .mapToDouble(Double::doubleValue).sum();
        } else {
            this.divSum = 0D;
        }
    }*/

    /**
     * Подсчитать проценты. То есть процент у нас это:
     *
     * Самый маленький див за прошлый год по отношению к цене акции по состоянию на эту дату.
     *
     * @param history - выборка по ставкам
     */
   /* public void calculatePercent(MoexDocumentRs history) {
        if (this.getDividendList() != null && this.getDividendList().size() > 0 && !Double.isNaN(divSum)) {

            Double v1 = dividendList.stream()
                    .filter(div -> (LocalDate.parse(div.getRegistryCloseDate())).getYear() == LocalDate.now().getYear() - 1)
                    .min(Comparator.comparing(DividendRs::getValue))
                    .map(DividendRs::getValue).orElse(0D);

            Double v2 = calculatePrice(history);

            this.percent = v2==0D ? 0D : (((v1) * 100) / v2);
        } else {
            this.percent = 0D;
        }
    }*/


    /**
     * Подсчитать цену из истории.
     *
     * @param history - выборка по ставкам с биржи.
     */
   /* private Double calculatePrice(MoexDocumentRs history) {
        *//*
         * Берем список дивов и выбираем любую дату из прошлого года.
         *//*
        Optional<LocalDate> dateOfDid = dividendList.stream()
                .filter(div -> LocalDate.parse(div.getRegistryCloseDate()).getYear() == LocalDate.now().getYear() - 1)
                .findFirst()
                .map(d -> LocalDate.parse(d.getRegistryCloseDate()));

        return dateOfDid.map(dat -> history.getData().getRow().stream()
                .filter(r -> LocalDate.parse(r.getTradeDate()).isEqual(dat))
                .findFirst()
                .map(MoexRowsRs::getLegalClosePrice)
                .orElse("0.0"))
                .map(Double::valueOf)
                .orElse(0D);
    }*/

}
