package com.antonromanov.arnote.domain.investing.service.consolidated;

import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedReturnsRs;

/**
 * Главный сервис калькуляции консолидированных данных.
 */
public interface ConsolidatedDataService {

    /**
     * Рассчитать и отдать и основные консолидированные данные.
     *
     * @return
     */
    ConsolidatedInvestmentDataRs getConsolidatedData(String filter, String sort);

    /**
     * Консолидированные данные по доходности.
     * @return
     */
    ConsolidatedReturnsRs getSummaryIncomeData();
}
