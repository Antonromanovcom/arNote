package com.antonromanov.arnote.model.investing;

import com.antonromanov.arnote.model.investing.response.DividendRs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.LinkedHashMap;

/**
 * Календарь выплат
 */
@Data
@Builder
@AllArgsConstructor
public class CalendarRs {
    private LinkedHashMap<String, DividendRs> calendar;
}
