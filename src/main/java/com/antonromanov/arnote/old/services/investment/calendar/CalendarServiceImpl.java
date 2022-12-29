package com.antonromanov.arnote.old.services.investment.calendar;

import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl /*implements CalendarService */{

    /**
     * Отдать календарь выплат
     *
     * @return
     */
/*    @Override
    public CalendarRs getCalendar(ConsolidatedInvestmentDataRs data) {

        LinkedHashMap<Months, List<ReturnsPerMonthRs>> map = Stream.of(Months.values())
                .collect(Collectors.toMap(p -> p, p -> collectReturns(data, p.getRussianName()), (v1, v2) -> v1, LinkedHashMap::new))
                .entrySet()
                .stream()
                .filter(a->a.getValue().size()>0)
                .collect(Collectors.toMap((Map.Entry::getKey), Map.Entry::getValue, (v1, v2)->v1, LinkedHashMap::new));

        return CalendarRs.builder()
                .calendar(map.entrySet()
                        .stream()
                        .map(e-> MonthDetailRs.builder()
                                .monthRussianName(e.getKey().getRussianName())
                                .monthEnglishName(e.getKey().getEnglishName())
                                .data(e.getValue())
                                .build())
                        .collect(Collectors.toCollection(LinkedList::new)))
                .build();
    }*/

    /**
     * Собрать дивы / купоны упорядоченно.
     *
     * @return
     */
  /*  @Override
    public List<ReturnsPerMonthRs> collectReturns(ConsolidatedInvestmentDataRs data, String month) {

        return data.getBonds()
                .stream()
                .flatMap(b -> b.getDividends().getDividendList().stream()
                        .filter(divs -> LocalDate.parse(divs.getRegistryCloseDate()).getMonthValue() == monthNameToNumber(month))
                        .filter(dd->LocalDate.parse(dd.getRegistryCloseDate()).getYear()==LocalDate.now().getYear()) // берем только текущий год
                        .filter(divMo->LocalDate.parse(divMo.getRegistryCloseDate()).isAfter(LocalDate.now()))
                        .map(d -> ReturnsPerMonthRs.builder()
                                .type(BondType.valueOf(b.getType()))
                                .ticker(b.getTicker())
                                .value(d.getValue()* b.getMinLot())
                                .currencyId(d.getCurrencyId())
                                .registryCloseDate(d.getRegistryCloseDate())
                                .build()))
                .collect(Collectors.toList());
    }*/
}
