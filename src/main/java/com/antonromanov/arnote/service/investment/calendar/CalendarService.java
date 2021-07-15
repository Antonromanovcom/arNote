package com.antonromanov.arnote.service.investment.calendar;

import com.antonromanov.arnote.domain.investing.dto.common.CalendarRs;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.domain.investing.dto.response.ReturnsPerMonthRs;

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
