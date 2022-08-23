package com.antonromanov.arnote.sex.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *  План расходов
 */
@Data
@Builder
@AllArgsConstructor
public class ConsolidatedPurchasesRs {
    private String longDescription; // описание длинное
    private String shortDescription; // описание короткое (20 символов)
    private Integer price; // стоимость общая
    private List<PurchasesRs> purchasePlan; // план покупок

}
