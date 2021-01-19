package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
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
     * Временный метод, потом переделаем.
     *
     * @param type
     * @return
     */
    CommonMoexDoc sendAndMarshall2(RestTemplateOperation type, String boardId, String ticker);


    /**
     * Сериализовать параметры запроса в MultiValueMap.
     *
     * @param type
     * @return
     */
    MultiValueMap<String, String> serializeObjectToMVMap(RestTemplateOperation type);
}
