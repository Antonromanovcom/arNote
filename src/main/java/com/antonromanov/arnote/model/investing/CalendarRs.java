package com.antonromanov.arnote.model.investing;


import com.antonromanov.arnote.model.investing.response.MonthDetailRs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.LinkedList;

/**
 * Календарь выплат
 */
@Data
@Builder
@AllArgsConstructor
public class CalendarRs {
    private LinkedList<MonthDetailRs> calendar;
}
