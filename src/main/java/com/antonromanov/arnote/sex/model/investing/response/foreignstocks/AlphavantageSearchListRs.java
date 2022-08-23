package com.antonromanov.arnote.model.investing.response.foreignstocks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Ответ сервиса Alphavantage по поиску бумаги.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlphavantageSearchListRs {
    private List<AlphavantageSearchRs> bestMatches;
}
