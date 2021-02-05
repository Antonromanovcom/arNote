package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.CurrentPriceRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.DividendRs;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalculateService {


    /**
     * Запросить дивиденды через API биржи, подсчитать сумму и проценты относительно текущей цены акции и вернуть все это.
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<ConsolidatedDividendsRs> getDivsByTicker(LocalUser user, String ticker);

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
    CurrentPriceRs getCurrentQuoteWith15MinuteUpdate(String ticker);


    /**
     * Запросить дату по текущей цене акции по тикеру.
     *
     * @return
     */
    Optional<LocalDate> getCurrentQuoteDateByTicker(String ticker);

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
    Optional<MoexDetailInfoRs> getDetailInfo(LocalUser user, String ticker);

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
    Integer calculateFinalPrice(Bond bond, LocalUser user);

    /**
     * Подготовить дивиденды.
     *
     * @return
     */
    ConsolidatedDividendsRs getDividends(Bond bond, LocalUser user);


    /**
     * Подготовить данные по валюте.
     *
     * @return
     */
    String getCurrencyOfShareFromDetailInfo(String ticker, LocalUser user);

    /**
     * Достать минимальный лот.
     *
     * @return
     */
    Integer getMinimalLot(String ticker, LocalUser user);

    /**
     * Запросить исторические данные.
     *
     * @return
     */
    MoexDocumentRs getHistory(String ticker, String boardId);

    /**
     * Запросить Облигации.
     *
     * @return
     */
    MoexDocumentRs getBondsFromMoexForBoardGroup(String boardGroup);

    /**
     * Запросить Облигации по всем доскам сразу.
     *
     * @return
     */
    MoexDocumentRs getBondsFromMoex();

    /**
     * Запросить Облигацию по тикеру.
     *
     * @param ticker - тикер
     * @return
     */
    Optional<MoexRowsRs> getBondDataByTicker(String ticker);

    /**
     * Определить валюту и курсовой-множитель для рубля.
     *
     * @param currency - валютный идентификатор
     * @return
     */
    Double getCurrencyMultiplier(String currency);

    /**
     * Получить текущую цену облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
    Double getCurrentBondPrice(String ticker);

    /**
     * Получить текущую валюту облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
    Currencies getBondCurrency(String ticker);

    /**
     * Получить имя облигации.
     *
     * @param ticker
     * @return
     */
    Optional<String> getBondName(String ticker);

    /**
     * Получить минимальный лот облигации или сколько куплено уже.
     *
     * @return
     */
    Integer getBondLot(Bond bond, LocalUser user, List<Purchase> purchaseList);

    /**
     * Получить купоны по облигации.
     *
     * @return
     */
    ConsolidatedDividendsRs getCoupons(Bond bond, LocalUser user);


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
     * Подготовить список купонов в формате списка дивидендов.
     *
     * @return
     */
    List<DividendRs> prepareCouponList(MoexRowsRs bondData);
}
