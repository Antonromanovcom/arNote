package com.antonromanov.arnote.old.model.common.enums;


/*@AllArgsConstructor
@Getter*/
public enum WorkCalendarSources { //TODO: подумать как это вообще лучше хранить
   /* CAL_2013(2013, "http://xmlcalendar.ru/data/ru/2013/calendar.xml"),
    CAL_2014(2014, "http://xmlcalendar.ru/data/ru/2014/calendar.xml"),
    CAL_2015(2015, "http://xmlcalendar.ru/data/ru/2015/calendar.xml"),
    CAL_2016(2016, "http://xmlcalendar.ru/data/ru/2016/calendar.xml"),
    CAL_2017(2017, "http://xmlcalendar.ru/data/ru/2017/calendar.xml"),
    CAL_2018(2018, "http://xmlcalendar.ru/data/ru/2018/calendar.xml"),
    CAL_2019(2019, "http://xmlcalendar.ru/data/ru/2019/calendar.xml"),
    CAL_2020(2020, "http://xmlcalendar.ru/data/ru/2020/calendar.xml"),
    CAL_2021(2021, "http://xmlcalendar.ru/data/ru/2021/calendar.xml"),
    CAL_2022(2022, "http://xmlcalendar.ru/data/ru/2022/calendar.xml");


    private final Integer year;
    private final String url;

    public static WorkCalendarSources searchURLByYear(Integer year){
        return Arrays.stream(WorkCalendarSources.values())
                .filter(e-> e.year.equals(year))
                .findFirst()
                .orElse(null);
    }

    public static Boolean checkYear(Integer year){
        return Arrays.stream(WorkCalendarSources.values())
                .anyMatch(e-> e.year.equals(year));
    }*/


}
