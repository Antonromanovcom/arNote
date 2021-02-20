package com.antonromanov.arnote.service.investment.calc.shares;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.CurrentPriceRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import java.util.List;
import java.util.Optional;

public interface SharesCalcService {


    /**
     * Запросить дивиденды через API биржи, подсчитать сумму и проценты относительно текущей цены акции и вернуть все это.
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<ConsolidatedDividendsRs> getDivsByTicker(ArNoteUser user, String ticker);

    /**
     * Запросить текущую цену акции по тикеру.
     *
     * @return
     */
    Optional<Double> getCurrentQuoteByTicker(String ticker);

    /**
     * Запросить текущую цену акции по тикеру. Та, что обновляется раз в 15 минут с торгов.
     *
     * @return
     */
    CurrentPriceRs getRealTimeQuote(String ticker);

    /**
     * Запросить текущую цену бумаги по board_id.
     *
     * @param
     * @return
     */
    Optional<MoexDocumentRs> getCurrentQuoteByBoardId(String boardId);

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<MoexDetailInfoRs> getDetailInfo(ArNoteUser user, String ticker);

    /**
     * Запросить board_id.
     *
     * @param ticker - тикер.
     * @return
     */
    String getBoardId(String ticker);


    /**
     * Запросить имя инструмента.
     *
     * @param ticker - тикер.
     * @return
     */
    Optional<String> getInstrumentName(String boardId, String ticker);

    /**
     * Запросить и посчитать дельту.
     *
     * @param ticker            - тикер.
     * @param currentStockPrice - текущая рыночная ставка (цена)
     * @param purchaseList      - список покупок (цен) пользователя
     * @return
     */
    DeltaRs calculateDelta(String boardId, String ticker, Double currentStockPrice, List<Purchase> purchaseList);

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @param bond
     * @param user
     * @return
     */
    Integer calculateFinalPrice(Bond bond, ArNoteUser user);

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    ConsolidatedDividendsRs getDividends(Bond bond, ArNoteUser user);


    /**
     * Подготовить данные по валюте.
     *
     * @return
     */
    String getCurrencyOfShare(String ticker, ArNoteUser user);

    /**
     * Достать минимальный лот.
     *
     * @return
     */
    Integer getMinimalLot(String ticker, ArNoteUser user);

    /**
     * Запросить исторические данные.
     *
     * @return
     */
    MoexDocumentRs getHistory(String ticker, String boardId);

    /**
     * Определить валюту и курсовой-множитель для рубля.
     *
     * @param currency - валютный идентификатор
     * @return
     */
    Double getCurrencyMultiplier(String currency);


    /**
     * Поиск акций по boardId.
     *
     * @return
     */
    MoexDocumentRs findSharesByBoardId(String boardId);

    /**
     * Выкачать и закэшировать режимы торгов.
     *
     * @return
     */
    List<String> getTradeModes();


    /**
     * Найти буржуйскую бумагу по ключевому слову. Возвращает только акции.
     *
     * @param keyword
     * @return
     */
    MoexDocumentRs getForeignInstrumentsByName(String keyword);
}
