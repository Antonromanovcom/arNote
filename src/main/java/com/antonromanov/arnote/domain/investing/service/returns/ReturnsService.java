package com.antonromanov.arnote.domain.investing.service.returns;

import com.antonromanov.arnote.domain.investing.dto.response.enums.Targets;
import java.util.Optional;

public interface ReturnsService {

    /**
     * Запросить общую сумму инвестированного.
     *
     * @return
     */
    Optional<Long> getTotalInvestment();

    /**
     * Получить дельту по акциям пользователя.
     * @param user
     * @return
     */
    Optional<Double> getSharesDelta();

    /**
     * Получить дельту только по КУПЛЕННЫМ акциям пользователя.
     *
     * @return
     */
    Optional<Double> getSharesDeltaForBought();

    /**
     * Получить общую доходность по дивидендам.
     *
     * @return
     */
    Optional<Long> getTotalDivsReturn();

    /**
     * Получаем детальную инфу по Дивам.
     * @param user
     * @return
     */
//    List<DivsDebug> getDivsDebug(ArNoteUser user);

    /**
     * Посчитать сколько надо вложить для получения заданной ежемесячной прибыли.
     *
     * @return
     */
    Long calculateRequiredInvestments(Targets target);

    /**
     * Посчитать общую сумму прибыли.
     *
     * @return
     */
    Long calculateTotalReturns();

    /**
     * Получить общий купонный доход по всем облигациям пользователя
     *
     * @return
     */
    Optional<Long> getTotalBondsReturns();
}
