package com.antonromanov.arnote.domain.investing.dto.common;


import com.antonromanov.arnote.domain.investing.dto.response.MonthDetailRs;
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
