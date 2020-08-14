package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.response.SumEntity;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.Optional;

/**
 * REST-контроллер получения статистических данных.
 */
@CrossOrigin()
@RestController
@RequestMapping("/statistic")
@Slf4j
@AllArgsConstructor
@Data
public class StatisticController {

    private final MainService mainService;
    private final UsersRepo usersRepo;
    private final Utils utils;

    /**
     * Получить статистику по всем желаниям
     *
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @CrossOrigin(origins = "*")
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
                            .all(mainService.getSumm4All(localUser))
                            .allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4All(localUser), localUser))
                            .priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4Prior(localUser), localUser))
                            .lastSalary(mainService.getLastSalary(localUser).getResidualSalary())
                            .averageImplementationTime(days)
                            .implementedSumAllTime(implementedSumAllTime)
                            .implementedSumMonth(implementedSumMonth)
                            .priority(mainService.getSumm4Prior(localUser)).build();
                })
                .orElseThrow(() -> new RuntimeException()); //todo: тут надо нормальный эксепшн бросить
    }
}
