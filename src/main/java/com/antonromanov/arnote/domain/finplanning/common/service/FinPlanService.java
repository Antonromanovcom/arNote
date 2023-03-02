package com.antonromanov.arnote.domain.finplanning.common.service;

import com.antonromanov.arnote.domain.finplanning.common.dto.rq.GetRemainsDetailInfoRq;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanListRs;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;

public interface FinPlanService {

    /**
     * Запросить консолидированную таблицу из кэша.
     *
     * @return
     */
    FinPlanListRs getFinPlanTableFromCache();

    /**
     * Запросить консолидированную таблицу из БД.
     * @return
     */
    FinPlanListRs getFinPlanTableFromDb();


    /**
     * Деталка по остаткам.
     *
     * @param payload
     * @return
     */
    FinalBalanceCalculationsRs getRemainsDetailInfo(GetRemainsDetailInfoRq payload);
}
