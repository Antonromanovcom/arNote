package com.antonromanov.arnote.domain.finplanning.api;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.*;
import com.antonromanov.arnote.domain.finplanning.common.service.FinPlanService;
import com.antonromanov.arnote.domain.finplanning.common.dto.rq.GetRemainsDetailInfoRq;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * API для фин-планирования.
 */
@CrossOrigin()
@RestController
@RequestMapping("/fin-planning")
@AllArgsConstructor
public class FinPlanController {

    private final FinPlanService service;


    /**
     * Запросить консолидированную таблицу из кэша.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/consolidated/cache")
    public FinPlanListRs getFinPlanTableFromCache() {
        return service.getFinPlanTableFromCache();
    }

    /**
     * Запросить консолидированную таблицу из БД.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/consolidated/db")
    public FinPlanListRs getFinPlanTableFromDb() {
        return service.getFinPlanTableFromDb();
    }


    /**
     * Деталка по остаткам.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/remains")
    public FinalBalanceCalculationsRs getRemainsDetailInfo(@RequestBody GetRemainsDetailInfoRq payload) {
        return service.getRemainsDetailInfo(payload);
    }
}
