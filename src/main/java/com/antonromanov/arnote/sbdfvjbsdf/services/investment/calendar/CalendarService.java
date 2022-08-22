package com.antonromanov.arnote.services.investment.calendar;

import com.antonromanov.arnote.model.investing.CalendarRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.model.investing.response.ReturnsPerMonthRs;

import java.util.List;

public interface CalendarService {


    /**
     * Отдать календарь выплат
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    CalendarRs getCalendar(ConsolidatedInvestmentDataRs user);

    /**
     * Собрать дивы / купоны упорядоченно.
     * @return
     */
    List<ReturnsPerMonthRs> collectReturns(ConsolidatedInvestmentDataRs data, String p);

}
