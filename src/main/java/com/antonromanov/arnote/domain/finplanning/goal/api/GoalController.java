package com.antonromanov.arnote.domain.finplanning.goal.api;

import com.antonromanov.arnote.domain.finplanning.goal.service.GoalsService;
import com.antonromanov.arnote.domain.finplanning.goal.dto.rq.GoalRq;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

/**
 * API для фин-планирования.
 */
@CrossOrigin()
@RestController
@RequestMapping("/fin-planning/goal")
@AllArgsConstructor
public class GoalController {

    private final GoalsService service;

    /**
     * Добавить расход / цель.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public SingleOperationRs addGoal(@RequestBody GoalRq payload) {
        return service.addGoal(payload);
    }

    /**
     * Редактировать расход / цель.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping()
    public SingleOperationRs editGoal( @RequestBody GoalRq payload) {
        return service.editGoal(payload);
    }

    /**
     * Удалить цель.
     *
     * @param id - id кредита
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public SingleOperationRs deleteGoal(@RequestParam @NotNull Long id) {
        return service.deleteGoal(id); //todo: во всех контроллерах и сервисах нужны валидаторы + проверки + тесты. Нужно проверять например, а если такой цели (goal) нет? Если нет - то вываливаться с с ошибкой. И тесты на то, что оно вываливается с ошибкой
    }

}
