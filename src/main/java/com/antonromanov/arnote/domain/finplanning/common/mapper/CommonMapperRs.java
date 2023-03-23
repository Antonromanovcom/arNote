package com.antonromanov.arnote.domain.finplanning.common.mapper;

import com.antonromanov.arnote.domain.finplanning.common.enums.CalcValues;
import com.antonromanov.arnote.domain.finplanning.income.entity.Income;
import com.antonromanov.arnote.domain.finplanning.income.dto.rs.CurrentIncomeRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;
import com.antonromanov.arnote.domain.finplanning.income.dto.rs.IncomeRs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommonMapperRs {

    @Mapping(expression = "java(valuesMap.get(CalcValues.BALANCE))", target = "balance")
    @Mapping(expression = "java(valuesMap.get(CalcValues.CURRENT_INCOME))", target = "currentIncome")
    @Mapping(expression = "java(valuesMap.get(CalcValues.LOAN_PAYMENTS_BY_DATE))", target = "loanPayments")
    @Mapping(expression = "java(valuesMap.get(CalcValues.MONTHLY_SPENDING))", target = "monthlySpending")
    @Mapping(expression = "java(valuesMap.get(CalcValues.PREV_EXPENSE))", target = "previousExpense")
    @Mapping(expression = "java(valuesMap.get(CalcValues.PREV_INCOME))", target = "previousIncome")
    @Mapping(target = "currentIncomeDetail", source = "curIncome")
    FinalBalanceCalculationsRs mapMapToFinalCalculations(EnumMap<CalcValues, Integer> valuesMap, CurrentIncomeRs curIncome);

    @Mapping(target = "emptyCalculations", constant = "false")
    @Mapping(target = "currentIncomeDetail", expression = "java(fillEmptyIncomeDetail())")
    FinalBalanceCalculationsRs fillEmptyCalculations(String date, Date dateInDateFormat);


    @Mapping(source = "salary", target = "salary")
    @Mapping(target = "incomeList", source = "incomesForCurrentDate", qualifiedByName = "mapIncomeList")
    CurrentIncomeRs mapCurrentIncome(Integer salary, List<Income> incomesForCurrentDate);


    @Mapping(target = "incomeDescription", source = "description")
    @Mapping(target = "amount", source = "income")
    IncomeRs mapOneIncome(Income source);


    @Named("mapIncomeList")
    default List<IncomeRs> mapIncomeList(List<Income> incomesForCurrentDate) {
        return incomesForCurrentDate.stream().map(this::mapOneIncome)
                .collect(Collectors.toList());
    }

    default CurrentIncomeRs fillEmptyIncomeDetail() {
        return new CurrentIncomeRs();
    }
}
