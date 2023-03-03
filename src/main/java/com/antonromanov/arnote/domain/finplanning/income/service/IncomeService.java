package com.antonromanov.arnote.domain.finplanning.income.service;

import com.antonromanov.arnote.domain.finplanning.income.dto.IncomeRq;
import com.antonromanov.arnote.domain.finplanning.income.entity.Income;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;
import com.antonromanov.arnote.old.dto.rq.IncomesForDeleteRq;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

public interface IncomeService {
    List<Income> incomesForCurrentDate(int year, int month);

    int getCurrentIncome(int year, int month);

    /**
     * Вытаскиваем предыдущий приход из мапы приходов.
     *
     * @param curMonth - текущий месяц относительного которого происходит запрос
     * @param curYear  - текущий год относительного которого происходит запрос
     * @return
     */
    Integer getPreviousIncome(int curYear, int curMonth, LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> globalBalanceMap);


    /**
     * Добавить доход
     *
     * @param payload
     * @return
     */
    SingleOperationRs addIncome(IncomeRq payload);

    /**
     * Удалить доход
     *
     * @return
     */
    SingleOperationRs deleteIncome(IncomesForDeleteRq req);


    /**
     * Редактировать доход
     *
     * @return
     */
    SingleOperationRs editIncome(IncomeRq req);

}
