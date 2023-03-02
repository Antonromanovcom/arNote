package com.antonromanov.arnote.domain.finplanning.income.api;

import com.antonromanov.arnote.domain.finplanning.income.dto.IncomeRq;
import com.antonromanov.arnote.domain.finplanning.income.service.IncomeService;
import com.antonromanov.arnote.old.dto.rq.IncomesForDeleteRq;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * API для фин-планирования.
 */
@CrossOrigin()
@RestController
@RequestMapping("/fin-planning/income")
@AllArgsConstructor
public class IncomeController {

    private final IncomeService service;

    /**
     * Добавить доход.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public SingleOperationRs addIncome(@RequestBody IncomeRq payload) {
        return service.addIncome(payload);
    }

    /**
     * Удалить доход.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public SingleOperationRs deleteIncome(@RequestBody IncomesForDeleteRq payload) {
        return service.deleteIncome(payload);
    }

    /**
     * Редактировать доход.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/income")
    public SingleOperationRs editIncome(@RequestBody IncomeRq payload) {
        return service.editIncome(payload);
    }

}
