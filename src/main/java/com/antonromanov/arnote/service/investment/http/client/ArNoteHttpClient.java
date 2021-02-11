package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.enums.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import org.springframework.util.MultiValueMap;
import java.util.Optional;

public interface ArNoteHttpClient {
    /**
     * Запросить дивиденды.
     *
     * @param ticker
     * @return
     */
    Optional<ConsolidatedDividendsRs> sendAndParse(String ticker);

    /**
     * Сделать запрос и смаршелить результат.
     *
     * @param type
     * @return
     */
    CommonMoexDoc sendAndMarshall(RestTemplateOperation type, String ticker, String boardId);

    /**
     * Специфический запрос для запроса истории.
     *
     * @param type
     * @return
     */
    CommonMoexDoc getHistory(RestTemplateOperation type, String ticker, String boardId, String dateFrom, String dateTill, int start);



    /**
     * Сериализовать параметры запроса в MultiValueMap.
     *
     * @param type
     * @return
     */
    MultiValueMap<String, String> serializeObjectToMVMap(RestTemplateOperation type);

    int getCounter();

}