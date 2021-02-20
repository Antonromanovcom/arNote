package com.antonromanov.arnote.service.investment.requestservice;

import com.antonromanov.arnote.model.investing.external.requests.ForeignRequests;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.foreignstocks.AlphavantageSearchListRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import org.springframework.util.MultiValueMap;

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
     * @param requestType - тип, содержащие разные данные по урлу и прочему.
     * @param ticker - тикер бумаги.
     * @param clazz - класс респонса.
     * @param <T> - респонс.
     * @return
     */
    <T>T sendAndMarshallForeignRequest(ForeignRequests requestType, String ticker, Class<T> clazz);
}
