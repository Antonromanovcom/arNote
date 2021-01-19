package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;

import java.util.List;
import java.util.Optional;

public interface CalculateService {
    /**
     * Запросить дивиденды через API биржи, подсчитать сумму проценты относительно текущей цены акции и вернуть все это.
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<ConsolidatedDividendsRs> getDivsByTicker(LocalUser user, String ticker);

    /**
     * Запросить текущую цену бумаги.
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<Double> getCurrentQuote(LocalUser user, String ticker);

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<MoexDetailInfoRs> getDetailInfo(LocalUser user, String ticker);

    /**
     * Запросить board_id.
     * @param ticker - тикер.
     * @return
     */
    Optional<String> getBoardId(String ticker);


    /**
     * Запросить имя инструмента.
     * @param ticker - тикер.
     * @return
     */
    Optional<String> getInstrumentName(String ticker);

    /**
     * Запросить и посчитать дельту.
     * @param ticker - тикер.
     * @param currentStockPrice - текущая рыночная ставка (цена)
     * @param purchaseList - список покупок (цен) пользователя
     * @return
     */
    DeltaRs getDelta(String boardId, String ticker, Double currentStockPrice, List<Purchase> purchaseList);
}
