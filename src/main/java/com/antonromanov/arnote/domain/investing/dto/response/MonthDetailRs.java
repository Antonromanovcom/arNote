package com.antonromanov.arnote.domain.investing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MonthDetailRs {
    private final String monthEnglishName;
    private final String monthRussianName;
    private final List<ReturnsPerMonthRs> data;
}
