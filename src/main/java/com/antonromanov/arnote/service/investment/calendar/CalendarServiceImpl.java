package com.antonromanov.arnote.service.investment.calendar;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.CalendarRs;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Override
    public CalendarRs getCalendar(LocalUser user) {

        Map<String, String> map = Stream.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
                .collect(Collectors.toMap(p -> p, p -> "1", (v1,v2)->v1,LinkedHashMap::new));


        return CalendarRs.builder()
               // .calendar()
                .build();
    }
}
