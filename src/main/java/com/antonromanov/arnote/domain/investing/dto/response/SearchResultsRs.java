package com.antonromanov.arnote.domain.investing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Результат поиска бумаг.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultsRs {
   private List<FoundInstrumentRs> instruments;
}
