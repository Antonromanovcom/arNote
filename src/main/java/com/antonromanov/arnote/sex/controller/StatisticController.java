package com.antonromanov.arnote.sex.controller;

import com.antonromanov.arnote.exceptions.UserNotFoundException;

/**
 * REST-контроллер получения статистических данных.
 */
/*@CrossOrigin()
@RestController
@RequestMapping("/statistic")
@Slf4j
@AllArgsConstructor
@Data*/
public class StatisticController {

    /*private final MainService mainService;
    private final UsersRepo usersRepo;
    private final Utils utils;*/

    /**
     * Получить статистику по всем желаниям
     *
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping("/sum")
    public SumEntity getSum(Principal principal) throws UserNotFoundException {

        LocalUser localUser = utils.getUserFromPrincipal(principal);

        return Optional.ofNullable(mainService.getLastSalary(localUser))
                .map(ls -> {
                    int days;
                    int implementedSumAllTime;
                    int implementedSumMonth;

                    int realizedWishesSize = (int) (mainService.getAllRealizedWishes(localUser)).stream()
                            .filter(wf -> wf.getRealizationDate() != null && wf.getCreationDate() != null)
                            .map(w -> (w.getRealizationDate().getTime() - w.getCreationDate().getTime()))
                            .count();

                    days = (realizedWishesSize == 0) ? 0 : (30 / realizedWishesSize);
                    implementedSumAllTime = mainService.getImplementedSum(localUser, 1).orElse(0);
                    implementedSumMonth = mainService.getImplementedSum(localUser, 2).orElse(0);

                    return SumEntity.builder()
                            .all(mainService.getSumForAllWishes(localUser))
                            .allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumForAllWishes(localUser), localUser))
                            .priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumForPriorityWishes(localUser), localUser))
                            .lastSalary(mainService.getLastSalary(localUser).getResidualSalary())
                            .averageImplementationTime(days)
                            .implementedSumAllTime(implementedSumAllTime)
                            .implementedSumMonth(implementedSumMonth)
                            .priority(mainService.getSumForPriorityWishes(localUser)).build();
                })
                .orElseThrow(() -> new RuntimeException()); //todo: тут надо нормальный эксепшн бросить
    }*/
}
