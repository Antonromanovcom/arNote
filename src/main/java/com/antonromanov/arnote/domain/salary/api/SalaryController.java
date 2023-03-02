package com.antonromanov.arnote.domain.salary.api;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.salary.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryListRs;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryRs;
import com.antonromanov.arnote.domain.salary.service.SalaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * REST-контроллер для работы с сущностью Зарплата.
 */
@CrossOrigin()
@RestController
@RequestMapping("/salary")
@AllArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @CrossOrigin(origins = "*")
    @PostMapping()
    public SalaryRs addSalary(@RequestBody SalaryRq request) {
        return salaryService.addSalary(request);
    }

    /**
     * Запросить все зарплаты.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping()
    public SalaryListRs getAllSalaries() {
        return salaryService.getSalariesList();
    }

    @CrossOrigin(origins = "*")
    @PutMapping() //todo: а не надо ли нам PUT переделать на PATCH? Надо почитать как там по рестфулу на самом деле
    public SingleOperationRs editSalary(@RequestBody SalaryRq payload) {
        return salaryService.editSalary(payload);
    }

    /**
     * Удалить ЗП.
     *
     * @param id
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public SingleOperationRs deleteSalary(@RequestParam @NotNull Long id) {
        return salaryService.deleteSalary(id);
    }
}
