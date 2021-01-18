package com.antonromanov.arnote.model.investing.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * Консолидированная инвест-таблица.
 */
@Data
@Builder
@AllArgsConstructor
public class ConsolidatedDividendsRs {
    private final List<DividendRs> dividendList;
    private Double divSum; // Сумма дивидендов за прошлый год
    private Integer percent; // Сумма дивидендов за прошлый год

    /**
     * Подсчитать сумму дивидендов.
     */
    public void calculateSum(){
        if (this.getDividendList().size()>0){
            this.divSum = dividendList.stream()
                    .map(DividendRs::getValue)
            .mapToDouble(Double::doubleValue).sum();
        } else {
            this.divSum=Double.NaN;
        }
    }

    /**
     * Подсчитать проценты.
     */
    public void calculatePercent(Double price){
        if (this.getDividendList().size()>0 && !Double.isNaN(divSum) && !price.isNaN()){
            this.percent = Math.toIntExact(Math.round((this.divSum * 100) / price));
        } else {
            this.divSum=Double.NaN;
        }
    }
}
