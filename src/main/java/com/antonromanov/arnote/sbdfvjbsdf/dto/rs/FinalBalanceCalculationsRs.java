package com.antonromanov.arnote.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;


/**
 * Транспортная ДТО для для подсчета финального построчного баланса / остатков.
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
public class FinalBalanceCalculationsRs {
    Integer balance; // итого
    Integer previousIncome; // предыдущий доход
    Integer previousExpense; // минус предыдущий расход
    Integer monthlySpending;  // минус среднемесячный расход
    Integer loanPayments; // минус покрытие кредитов
    Integer currentIncome; // + ежемесячная зарплата.
    CurrentIncomeRs currentIncomeDetail; // деталка по доходам
    String date; // дата
    Date dateInDateFormat; // дата
    Boolean freeze; // фриз-фикс есть по этой дате?
    Boolean emptyCalculations; // флаг того, что пользак только начал и карта расчетов у него - все по нулям (null)
}
