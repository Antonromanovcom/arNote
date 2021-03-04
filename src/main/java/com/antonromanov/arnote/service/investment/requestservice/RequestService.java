package com.antonromanov.arnote.service.investment.requestservice;

import com.antonromanov.arnote.model.investing.external.requests.ForeignRequests;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import org.springframework.util.MultiValueMap;

import java.util.LinkedList;
import java.util.Optional;

public interface RequestService {
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
    CommonMoexDoc sendAndMarshall(MoexRestTemplateOperation type, String ticker, String boardId);

    /**
     * Специфический запрос для запроса истории.
     *
     * @param type
     * @return
     */
    CommonMoexDoc getHistory(MoexRestTemplateOperation type, String ticker, String boardId, String dateFrom, String dateTill, int start);

    /**
     * Сериализовать параметры запроса в MultiValueMap для MOEX.
     *
     * @param type
     * @return
     */
    MultiValueMap<String, String> serializeObjectToMVMap(Object type);


    int getCounter();

    /**
     * Отправить запрос в буржуйское API.
     *
     * @param <T> - респонс.
     * @param requestType - тип, содержащие разные данные по урлу и прочему.
     * @param params - параметры запроса.
     * @param clazz - класс респонса.
     * @return
     */
    <T>T sendAndMarshallForeignRequest(ForeignRequests requestType, LinkedList<String> params, Class<T> clazz);

    /**
     * Отправить запрос в буржуйское API без сериализации (вернуть сразу респонс назад).
     *
     *
     * @param requestType - тип, содержащие разные данные по урлу и прочему.
     * @param params - параметры запроса.
     *
     * @return
     */
    Optional<String> sendForeignRequest(ForeignRequests requestType, LinkedList<String> params);
}
