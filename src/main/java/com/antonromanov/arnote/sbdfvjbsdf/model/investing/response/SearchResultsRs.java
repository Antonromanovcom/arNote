package com.antonromanov.arnote.model.investing.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
