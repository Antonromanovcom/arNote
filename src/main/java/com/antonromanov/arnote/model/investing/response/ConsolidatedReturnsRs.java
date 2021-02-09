package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Консолидированная таблица по доходности.
 */
@Data
@Builder
@AllArgsConstructor
public class ConsolidatedReturnsRs {
    private Long invested; // инвестировано
    private Long bondsReturns; // купоны с облигаций
    private Long sharesReturns; // доходы с дивидендов
    private Long sharesDelta; // Рост акций
    private Long sum; // купоны + дивиденды + рост акций
    Map<Targets, Long> targets; // сколько надо вложить для той или иной суммы
}
