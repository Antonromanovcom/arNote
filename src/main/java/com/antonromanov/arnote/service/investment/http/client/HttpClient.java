package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.MoexDocumentRs;
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
    Optional<MoexDocumentRs> sendAndMarshall(RestTemplateOperation type);
}
