package com.antonromanov.arnote.sex.controller;


/**
 * REST-контроллер работы с зарплатой.
 */
//@CrossOrigin()
//@RestController
//@RequestMapping("/salary")
//@Slf4j
//@AllArgsConstructor
//@Data
public class SalaryController {

   /* private final MainService mainService;
    private final UsersRepo usersRepo;
    private final Utils utils;*/

    /**
     * Получить последнюю зарплату.
     *
     * @param principal - JWT-пользователь.
     * @return
     */
   /* @CrossOrigin(origins = "*")
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
*/
    /**
     * Добавить новую зарплату.
     *
     * @param principal
     * @param newSalary
     * @return
     * @throws UserNotFoundException
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping()
    public Salary addSalary(Principal principal, @RequestBody NewSalaryRq newSalary) throws UserNotFoundException {

        log.info("==================== ADD SALARY ======================== ");
        log.info("PAYLOAD: " + createGsonBuilder().toJson(newSalary));
        log.info("PRINCIPAL: " + principal.getName());

        LocalUser localUser = utils.getUserFromPrincipal(principal);
        log.info("======================================================== ");
        return mainService.saveSalary(new Salary(newSalary, localUser));


    }*/
}
