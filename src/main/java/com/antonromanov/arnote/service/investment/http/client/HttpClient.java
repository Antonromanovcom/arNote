package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import java.util.Optional;

public interface HttpClient {
    /**
     * Запросить дивиденды.
     *
     * @param ticker
     * @return
     */
    Optional<ConsolidatedDividendsRs> sendAndParse(String ticker);

    /**
     * Запросить последнюю ставку.
     *
     * @param type
     * @return
     */
    CommonMoexDoc sendAndMarshall(RestTemplateOperation type, String ticker);


    /**
     * Временный метод, потом переделаем.
     *
     * @param type
     * @return
     */
    CommonMoexDoc sendAndMarshall2(RestTemplateOperation type, String boardId, String ticker);
}
