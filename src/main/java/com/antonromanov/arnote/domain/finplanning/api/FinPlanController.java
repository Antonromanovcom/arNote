package com.antonromanov.arnote.domain.finplanning.api;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinPlanListRs;
import com.antonromanov.arnote.domain.finplanning.common.service.FinPlanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param principal
     * @return
     * @throws Exception
     */
    /*@CrossOrigin(origins = "*")
    @GetMapping("/consolidated")
    public FinPlanListRs getFinPlanTableFromCache(Principal principal) {
        return service.getFinPlanTableFromCache(principal);
    }*/

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
     * Пытаемся запихнуть получение консолдированной таблы в поток
     *
     * @param principal
     * @return
     * @throws Exception
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping("/consolidated/thread/start")
    public SingleOperationRs getFinPlanTableFromDbStart(Principal principal) throws Exception {
        log.info("Get Consolidated List From DB. Start calculation in Thread...");
        service.startCalculation(principal);
        return SingleOperationRs.builder()
                .status(ResponseStatusRs.builder()
                        .status("Thread Started")
                        .description("Thread Started")
                        .code(200)
                        .build())
                .build();
    }*/

    /**
     * Получить статус потока
     *
     * @param principal
     * @return
     * @throws Exception
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/consolidated/thread/get")
    public SingleOperationRs getThreadStatus(Principal principal) throws Exception {
        log.info("Get Thread Status");

        return SingleOperationRs.builder()
                .status(ResponseStatusRs.builder()
                        .status("INT = " + service.getThreadStatus())
                        .description("Thread Status gained")
                        .code(200)
                        .build())
                .build();
    }*/


    /**
     * Добавить доход.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PostMapping("/income")
    public SingleOperationRs addIncome(Principal principal, @RequestBody IncomeRq payload) throws UserNotFoundException {
        log.info("Add income: {}", payload);
        return service.addIncome(payload, principal);
    }*/

    /**
     * Удалить доход.
     *
     * @param principal - пользак
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/income/delete")
    public SingleOperationRs deleteIncome(Principal principal, @RequestBody IncomesForDeleteRq payload) throws UserNotFoundException {
        log.info("Delete incomes: {}", payload);
        return service.deleteIncome(principal, payload);
    }*/

    /**
     * Редактировать доход.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PutMapping("/income")
    public SingleOperationRs editIncome(Principal principal, @RequestBody IncomeRq payload) throws UserNotFoundException {
        log.info("Edit income: {}", payload);
        return service.editIncome(payload, principal);
    }*/

    /**
     * Деталка по остаткам.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PostMapping("/remains")
    public FinalBalanceCalculationsRs getRemainsDetailInfo(Principal principal, @RequestBody GetRemainsDetailInfoRq payload)
            throws UserNotFoundException {
        log.info("Get Remains Detail Info: {}", payload);
        return service.getRemainsDetailInfo(payload, principal);
    }*/

    /**
     * Добавить расход / цель.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/goal")
    public SingleOperationRs addGoal(Principal principal, @RequestBody GoalRq payload) throws UserNotFoundException {
        log.info("Add goal: {}", payload);
        return service.addGoal(payload, principal);
    }*/

    /**
     * Редактировать расход / цель.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PutMapping("/goal")
    public SingleOperationRs editGoal(Principal principal, @RequestBody GoalRq payload) throws UserNotFoundException {
        log.info("Edit goal: {}", payload);
        return service.editGoal(payload, principal);
    }*/

    /**
     * Удалить цель.
     *
     * @param principal - пользак
     * @param id        - id кредита
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @DeleteMapping("/goal")
    public AddCreditRs deleteGoal(Principal principal, @RequestParam @NotNull Long id) throws UserNotFoundException {
        log.info("Delete goal: {}", id);
        return service.deleteGoal(principal, id);
    }*/

    /**
     * Запросить все зарплаты.
     *
     * @param principal - пользак
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/salary")
    public SalaryListRs getAllSalaries(Principal principal) throws UserNotFoundException {
        log.info("Getting salaries list...");
        return service.getSalariesList(principal);
    }*/

    /**
     * Запросить все зарплаты.
     *
     * @param principal - пользак
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @PutMapping("/salary")
    public SingleOperationRs editSalary(Principal principal, @RequestBody SalaryRq payload) throws UserNotFoundException {
        log.info("Edit salary {}", payload);
        return service.editSalary(principal, payload);
    }*/

    /**
     * Добавить новую ЗП.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PostMapping("/salary")
    public SingleOperationRs addSalary(Principal principal, @RequestBody SalaryRq payload) throws UserNotFoundException {
        log.info("Add salary {}", payload);
        return service.addSalary(principal, payload);
    }*/

    /**
     * Удалить ЗП.
     *
     * @param principal
     * @param id
     * @return
     * @throws UserNotFoundException
     */
 /*   @CrossOrigin(origins = "*")
    @DeleteMapping("/salary")
    public SingleOperationRs deleteSalary(Principal principal, @RequestParam @NotNull Long id) {
        log.info("Delete salary: {}", id);
        return service.deleteSalary(principal, id);
    }*/

    /**
     * Добавить фриз.
     *
     * @param principal
     * @param request
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PostMapping("/freeze")
    public SingleOperationRs addFreeze(Principal principal, @RequestBody FreezeRq request) {
        log.info("Add freeze: {}", request);
        return service.addFreeze(principal, request);
    }*/

    /**
     * Удалить фриз.
     *
     * @param principal
     * @param
     * @return
     * @throws UserNotFoundException
     */
 /*   @CrossOrigin(origins = "*")
    @DeleteMapping("/freeze")
    public SingleOperationRs deleteFreeze(Principal principal, @RequestParam @NotNull Long year, @RequestParam @NotNull Long month) {
        log.info("Delete freeze");
        return service.deleteFreeze(principal, year, month);
    }*/
}
