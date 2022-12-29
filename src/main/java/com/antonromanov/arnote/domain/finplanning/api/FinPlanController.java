package com.antonromanov.arnote.domain.finplanning.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API для фин-планирования.
 */
@CrossOrigin()
@RestController
@RequestMapping("/fin-planning")
public class FinPlanController {

   /* @Autowired
    private final FinPlanService service;

    public FinPlanController(FinPlanService service) {
        this.service = service;
    }*/

    /**
     * Запросить консолидированную таблицу из кэша.
     *
     * @param principal
     * @return
     * @throws Exception
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping("/consolidated")
    public FinPlanListRs getFinPlanTableFromCache(Principal principal) throws Exception {
        log.info("Get Consolidated List From Cache...");
        return service.getFinPlanTableFromCache(principal);
    }*/

    /**
     * Запросить консолидированную таблицу из БД.
     *
     * @param principal
     * @return
     * @throws Exception
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping("/consolidated/db")
    public FinPlanListRs getFinPlanTableFromDb(Principal principal) throws Exception {
        log.info("Get Consolidated List From DB...");
        return service.getFinPlanTableFromDb(principal);
    }
*/
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
     * Добавить кредит.
     *
     * @param principal
     * @param request
     * @return
     * @throws UserNotFoundException
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/loan")
    public AddCreditRs addLoan(Principal principal, @RequestBody CreditRq request) throws UserNotFoundException {
        log.info("Add loan: {}", request);
        return service.addCredit(principal, request);
    }*/

    /**
     * Удалить кредит.
     *
     * @param principal - пользак
     * @param id        - id кредита
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @DeleteMapping("/loan")
    public AddCreditRs deleteLoan(Principal principal, @RequestParam @NotNull Long id) {
        log.info("Delete loan: {}", id);
        return service.deleteLoan(principal, id);
    }*/

    /**
     * Запросить полный список кредитов.
     *
     * @param principal
     * @return
     * @throws Exception
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/loan/list")
    public FullLoansListRs creditList(Principal principal) throws UserNotFoundException {
        log.info("============== Credit list ============== ");
        return service.getFullLoansList(principal);
    }*/

    /**
     * Запросить свободные слоты по кредитам.
     *
     * @param principal
     * @return
     * @throws Exception
     */
  /*  @CrossOrigin(origins = "*")
    @PostMapping("/loan/slots")
    public FreeLoanSlotsRs getLoansSlots(Principal principal, @RequestBody LoanByDateRq payload) throws UserNotFoundException {
        log.info("Free Loans Slots by Date = {}", payload);
        return service.getLoansSlots(principal, payload);
    }*/


    /**
     * Запросить кредит по дате.
     *
     * @param principal
     * @return
     * @throws Exception
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/loan/bydate")
    public FullLoansListRs loanByDate(Principal principal, @RequestBody LoanByDateRq payload) {
        log.info("Credit by Date = {}", payload);
        return service.getLoanByDate(payload, principal);
    }*/

    /**
     * Запросить данные по кредиту по его ID.
     *
     * @param principal
     * @param id
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/loan")
    public CreditRs getLoanById(Principal principal, @RequestParam @NotNull Long id) throws UserNotFoundException { // todo: надо отдельную ДТО с респонсом или как-то научиться заворачивать их (враппить)
        log.info("Get loan by id: {}", id);
        return service.getLoanById(id, principal);
    }*/

    /**
     * Редактировать кредит.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PutMapping("/loan")
    public AddCreditRs editLoan(Principal principal, @RequestBody CreditRq payload) throws UserNotFoundException {
        log.info("Edit loan: {}", payload);
        return service.editLoan(payload, principal);
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
