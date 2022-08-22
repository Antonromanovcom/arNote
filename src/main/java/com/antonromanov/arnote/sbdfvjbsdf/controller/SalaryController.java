package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.request.NewSalaryRq;
import com.antonromanov.arnote.dto.response.SumEntity;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.entity.Salary;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import static com.antonromanov.arnote.utils.Utils.createGsonBuilder;

/**
 * REST-контроллер работы с зарплатой.
 */
@CrossOrigin()
@RestController
@RequestMapping("/salary")
@Slf4j
@AllArgsConstructor
@Data
public class SalaryController {

    private final MainService mainService;
    private final UsersRepo usersRepo;
    private final Utils utils;

    /**
     * Получить последнюю зарплату.
     *
     * @param principal - JWT-пользователь.
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping()
    public SumEntity getLastSalary(Principal principal) throws UserNotFoundException {
        LocalUser localUser = utils.getUserFromPrincipal(principal);
        return SumEntity.builder()
                .all(mainService.getSumForAllWishes(localUser))
                .allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumForAllWishes(localUser), localUser))
                .priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumForPriorityWishes(localUser), localUser))
                .lastSalary(mainService.getLastSalary(localUser).getResidualSalary())
                .priority(mainService.getSumForPriorityWishes(localUser)).build();
    }

    /**
     * Добавить новую зарплату.
     *
     * @param principal
     * @param newSalary
     * @return
     * @throws UserNotFoundException
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public Salary addSalary(Principal principal, @RequestBody NewSalaryRq newSalary) throws UserNotFoundException {

        log.info("==================== ADD SALARY ======================== ");
        log.info("PAYLOAD: " + createGsonBuilder().toJson(newSalary));
        log.info("PRINCIPAL: " + principal.getName());

        LocalUser localUser = utils.getUserFromPrincipal(principal);
        log.info("======================================================== ");
        return mainService.saveSalary(new Salary(newSalary, localUser));


    }
}
