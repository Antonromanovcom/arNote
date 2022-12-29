package com.antonromanov.arnote.domain.salary.api;

import com.antonromanov.arnote.domain.salary.service.SalaryService;
import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rs.SalaryRs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * REST-контроллер для работы с сущностью Зарплата.
 */
@CrossOrigin()
@RestController
@RequestMapping("/salary")
@Slf4j
@AllArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @CrossOrigin(origins = "*")
    @PostMapping()
    public SalaryRs addSalary(@RequestBody SalaryRq request) {
        return salaryService.addSalary(request);
    }
}
