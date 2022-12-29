package com.antonromanov.arnote.old.services.investment.requestservice;


public interface RequestService {
    /**
     * Запросить дивиденды.
     *
     * @param ticker
     * @return
     */
//    Optional<ConsolidatedDividendsRs> sendAndParse(String ticker);

    /**
     * Сделать запрос и смаршелить результат.
     *
     * @param type
     * @return
     */
//    CommonMoexDoc sendAndMarshall(MoexRestTemplateOperation type, String ticker, String boardId);

    /**
     * Специфический запрос для запроса истории.
     *
     * @param type
     * @return
     */
//    CommonMoexDoc getHistory(MoexRestTemplateOperation type, String ticker, String boardId, String dateFrom, String dateTill, int start);

    /**
     * Специфический запрос для запроса свечей.
     *
     * @return
     */
//    CommonMoexDoc getCandles(MoexRestTemplateOperation type, String ticker, String dateFrom, String dateTill, int start); //todo: подумать как объединить свечи и историю в один метод

    /**
     * Сериализовать параметры запроса в MultiValueMap для MOEX.
     *
     * @param type
     * @return
     */
//    MultiValueMap<String, String> serializeObjectToMVMap(Object type);


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
//    <T>T sendAndMarshallForeignRequest(ForeignRequests requestType, LinkedList<String> params, Class<T> clazz);

    /**
     * Отправить запрос в буржуйское API без сериализации (вернуть сразу респонс назад).
     *
     *
     * @param requestType - тип, содержащие разные данные по урлу и прочему.
     * @param params - параметры запроса.
     *
     * @return
     */
//    Optional<String> sendForeignRequest(ForeignRequests requestType, LinkedList<String> params);
}
