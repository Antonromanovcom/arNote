package com.antonromanov.arnote.service.investment.returns;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.response.DivsDebug;
import com.antonromanov.arnote.model.investing.response.enums.Targets;

import java.util.List;
import java.util.Optional;

public interface ReturnsService {
    /**
     * Запросить общую сумму инвестированного.
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    Optional<Long> getTotalInvestment(ArNoteUser user);

    /**
     * Получить дельту по акциям пользователя.
     * @param user
     * @return
     */
    Optional<Double> getSharesDelta(ArNoteUser user);

    /**
     * Получить общую доходность по дивидендам.
     * @param user
     * @return
     */
    Optional<Long> getTotalDivsReturn(ArNoteUser user);

    /**
     * Получаем детальную инфу по Дивам.
     * @param user
     * @return
     */
    List<DivsDebug> getDivsDebug(ArNoteUser user);

    /**
     * Посчитать сколько надо вложить для получения заданной ежемесячной прибыли.
     * @param user
     * @return
     */
    Long calculateRequiredInvestments(ArNoteUser user, Targets target);

    /**
     * Посчитать общую сумму прибыли.
     * @param user
     * @return
     */
    Long calculateTotalReturns(ArNoteUser user);

    /**
     * Получить общий купонный доход по всем облигациям пользователя.
     * @param user
     * @return
     */
    Optional<Long> getTotalBondsReturns(ArNoteUser user);
}
