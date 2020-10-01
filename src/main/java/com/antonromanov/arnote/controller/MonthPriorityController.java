package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.request.MoveWishDto;
import com.antonromanov.arnote.dto.response.monthgroupping.ListOfMonthsResponse;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.enums.SortMode;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.NoDataYetException;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import static com.antonromanov.arnote.utils.Utils.parseMonthAndCalculatePriority;

/**
 * REST-контроллер для работы с приоритетом по месяцам.
 */
@CrossOrigin()
@RestController
@RequestMapping("/month-grouping")
@Slf4j
@AllArgsConstructor
@Data
public class MonthPriorityController {

    private final MainService mainService;
    private final UsersRepo usersRepo;
    private final Utils utils;


    /**
     * Получить все желания с группировкой по месяцам.
     *
     * @param principal
     * @param sortType
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping
    public ListOfMonthsResponse getAllWishesWithMonthGrouping(Principal principal, @RequestParam SortMode sortType)
            throws UserNotFoundException, NoDataYetException {

            return ListOfMonthsResponse.builder()
                    .list(mainService.getAllWishesWithGroupPriority(utils.getUserFromPrincipal(principal), sortType)
                            .orElseThrow(()->new NoDataYetException(false)))
                    .build();
    }

    /**
     * Переместить желание по месяцам.
     *
     * @param principal - пользователь.
     * @param payload   - ДТО с параметрами куда перемещать.
     * @return - итоговое желание.
     */
    @CrossOrigin(origins = "*")
    @PostMapping
    public Wish changeMonthOrder(Principal principal, @RequestBody MoveWishDto payload) throws BadIncomeParameter {

        Wish wish = mainService.getWishById(payload.getId()).orElseThrow(() ->
                new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
        wish.setPriorityGroup(parseMonthAndCalculatePriority(payload.getMonth())); //todo: может добавить билдер в энтити или сделать отдельное ДТО для респонса с билдером
        return mainService.updateAndFlushWish(wish);
    }

    /**
     * Переместить желание по месяцам +/- на 1 месяц.
     *
     * @param principal - пользователь.
     * @param payload   - ДТО с параметрами куда перемещать.
     * @return - итоговое желание.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/one-step")
    public Wish changeMonth(Principal principal, @RequestBody MoveWishDto payload) throws BadIncomeParameter {
        return mainService.getWishById(payload.getId())
                .map(wish -> payload.getStep().getChangeMonthOrder().move(wish, mainService))
                .orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
    }
}
