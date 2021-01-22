package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import java.util.Optional;

public interface ReturnsService {
    /**
     * Запросить общую сумму инвестированного.
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<Long> getTotalInvestment(LocalUser user);

    /**
     * Получить дельту по всем бумагам пользователя.
     * @param user
     * @return
     */
    Optional<Long> getSharesDelta(LocalUser user);

    /**
     * Получить общую доходность по дивидендам.
     * @param user
     * @return
     */
    Optional<Long> getTotalDivsReturn(LocalUser user);

    /**
     * Посчитать сколько надо вложить для получения заданной ежемесячной прибыли.
     * @param user
     * @return
     */
    Long calculateRequiredInvestments(LocalUser user, Targets target);

    /**
     * Посчитать общую сумму прибыли.
     * @param user
     * @return
     */
    Long calculateTotalReturns(LocalUser user);


}
