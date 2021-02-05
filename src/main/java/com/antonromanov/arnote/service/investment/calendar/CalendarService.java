package com.antonromanov.arnote.service.investment.calendar;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.CalendarRs;

public interface CalendarService {


    /**
     * Отдать календарь выплат
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    CalendarRs getCalendar(LocalUser user);

}
