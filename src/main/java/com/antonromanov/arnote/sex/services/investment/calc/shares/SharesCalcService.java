package com.antonromanov.arnote.sex.services.investment.calc.shares;


public interface SharesCalcService {

    /**
     * Получить Свечи
     *
     * @return
     */
 //   MoexDocumentRs getCandles(String ticker, LocalDate fromDate, LocalDate tillDate);


    /**
     * Запросить дивиденды через API биржи, подсчитать сумму и проценты относительно текущей цены акции и вернуть все это.
     *
     * @return
     */
   // ConsolidatedDividendsRs getDivsByTicker(String ticker);

    /**
     * Запросить текущую цену акции по тикеру.
     *
     * @return
     */
   // Optional<Double> getCurrentQuoteByTicker(String ticker);

    /**
     * Запросить текущую цену акции по тикеру. Та, что обновляется раз в 15 минут с торгов.
     *
     * @return
     */
  //  CurrentPriceRs getRealTimeQuote(String ticker);

    /**
     * Запросить текущую цену бумаги по board_id.
     *
     * @param
     * @return
     */
 //   MoexDocumentRs getCurrentQuoteByBoardId(String boardId);

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     *
     * @return
     */
 //   Optional<MoexDetailInfoRs> getDetailInfo(String ticker);

    /**
     * Запросить board_id.
     *
     * @param ticker - тикер.
     * @return
     */
 //   String getBoardId(String ticker);

    /**
     * Запросить имя инструмента.
     *
     * @param ticker - тикер.
     * @return
     */
  //  String getInstrumentName(String boardId, String ticker);

    /**
     * Запросить и посчитать дельту.
     *
     * @param ticker            - тикер.
     * @param currentStockPrice - текущая рыночная ставка (цена)
     * @param purchaseList      - список покупок (цен) пользователя
     * @param deltaMode      - как считаем Дельту.
     * @return
     */
  //  DeltaRs calculateDelta(String ticker, Double currentStockPrice, List<Purchase> purchaseList,
                        //   DeltaMode deltaMode);

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @param bond
     * @return
     */
  //  Integer calculateFinalPrice(Bond bond);

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
  //  ConsolidatedDividendsRs getDividends(Bond bond);


    /**
     * Подготовить данные по валюте.
     *
     * @return
     */
  //  String getCurrencyOfShare(String ticker);

    /**
     * Достать минимальный лот.
     *
     * @return
     */
  //  Integer getMinimalLot(String ticker, ArNoteUser user);

    /**
     * Запросить исторические данные по котировкам с биржи.
     *
     * @param ticker - тикер бумаги.
     * @param boardId - boardId (только для MOEX)
     * @param forDate -на какую  дату запрашиваем.
     * @return - MoexDocumentRs
     */
  //  MoexDocumentRs getHistory(String ticker, String boardId, LocalDate forDate);

    /**
     * Определить валюту и курсовой-множитель для рубля.
     *
     * @param currency - валютный идентификатор
     * @return
     */
  //  Double getCurrencyMultiplier(String currency);


    /**
     * Поиск акций по boardId.
     *
     * @return
     */
 //   MoexDocumentRs findSharesByBoardId(String boardId);

    /**
     * Выкачать и закэшировать режимы торгов.
     *
     * @return
     */
  //  List<String> getTradeModes();


    /**
     * Найти буржуйскую бумагу по ключевому слову. Возвращает только акции.
     *
     * @param keyword
     * @return
     */
  //  MoexDocumentRs findInstrumentsByName(String keyword);
}
