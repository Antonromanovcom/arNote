package com.antonromanov.arnote.old.dto.rs;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * Один месяц фин-планов
 */
@Data
@Builder
@AllArgsConstructor
public class FinPlanRs {
    private String month; // текущий месяц
    private Integer monthNumber; // номер текущего месяца
    private Integer year; // текущий год
    @JsonUnwrapped
    private CreditListRs credits; // расклад по кредитам - разрешено не более 5-ти
    private Integer allCredits; // сумма всех кредитов
    private ConsolidatedPurchasesRs purchasePlan; // план покупок
    private Integer remains; // доходы и остатки
    private Boolean freeze; // это фриз?
    private String color; // цвет строки
    private String fontColor; // цвет шрифта
    private String borderWidth; // толщина границы
}
