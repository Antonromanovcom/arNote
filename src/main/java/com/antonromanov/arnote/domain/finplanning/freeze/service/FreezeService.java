package com.antonromanov.arnote.domain.finplanning.freeze.service;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.finplanning.freeze.dto.rq.FreezeRq;
import com.antonromanov.arnote.domain.finplanning.freeze.entity.Freeze;
import java.util.Optional;

public interface FreezeService {
    /**
     * Отфильтровать список фризов по году и месяцу.
     *
     * @param year
     * @param month
     * @return
     */
    Optional<Freeze> filterFreezeListByDate(int year, int month);

    Boolean isThisFreeze(int year, int month);

    /**
     * Добавить фриз.
     *
     * @param request
     * @return
     */
    SingleOperationRs addFreeze(FreezeRq request);


    /**
     * Удалить Фриз.
     * @param year
     * @param month
     * @return
     */
    SingleOperationRs deleteFreeze(Integer year, Integer month);
}
