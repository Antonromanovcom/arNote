package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.MoexDetailInfoRs;

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
}
