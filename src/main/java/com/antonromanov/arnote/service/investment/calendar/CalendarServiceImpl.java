package com.antonromanov.arnote.service.investment.calendar;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.CalendarRs;
import org.springframework.stereotype.Service;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Override
    public CalendarRs getCalendar(LocalUser user) {
        DateFormatSymbols russianDfs = new DateFormatSymbols(new Locale("ru"));
        String[] russianMonths = russianDfs.getMonths();

        Arrays.asList("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");
        Stream<String> str = Stream.of(russianMonths);
        Map<String, String> map = str
                .filter(st->!isBlank(st))
                .map(s->{
                    if (s.endsWith("я")) {
                        if (s.startsWith("м")){
                            s = "май";
                        } else {
                            s = s.substring(0,s.length() - 1)+"ь";
                        }
                    }
                    if (s.endsWith("а")) {
                        s = s.substring(0,s.length() - 1);
                    }
                    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
                })
                .collect(Collectors.toMap(p -> p, p -> "1", (v1,v2)->v1,LinkedHashMap::new));


        return CalendarRs.builder()
               // .calendar()
                .build();
    }
}
