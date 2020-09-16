package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.request.MoveWishDto;
import com.antonromanov.arnote.dto.response.WishResponse;
import com.antonromanov.arnote.dto.response.monthgroupping.ListOfMonthsResponse;
import com.antonromanov.arnote.entity.LocalUser;
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
import java.util.Comparator;
import java.util.Date;
import static com.antonromanov.arnote.utils.Utils.createNullableGsonBuilder;
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
                    .list(mainService.getAllWishesWithGroupPriority(utils.getAndUpdateUser(principal, sortType), sortType)
                            .orElseThrow(()->new NoDataYetException(false) ))
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


        log.info("========= MOVE WISH (CHANGE MONTH PRIORITY) ============== ");
        log.info("id: " + payload.getId());
        log.info("month: " + payload.getMonth());
        log.info("PRINCIPAL: " + principal.getName());
        log.info("=========================================================== ");

        Wish wish = mainService.getWishById(payload.getId()).orElseThrow(() ->
                new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
        wish.setPriorityGroup(parseMonthAndCalculatePriority(payload.getMonth())); //todo: может добавить билдер в энтити или сделать отдельное ДТО для респонса с билдером
        Wish movedWish = mainService.updateAndFlushWish(wish);
        String result = createNullableGsonBuilder().toJson(movedWish);
        log.info("ОТВЕТ: {}", result); // todo: если prepareResponse отдает нормальный ответ - эту строчку удалим. Ну или надо сделать, чтобы он отдавал нормальный ответ
        return movedWish;

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
    public Wish changeMonth(Principal principal, @RequestBody MoveWishDto payload)
            throws UserNotFoundException, BadIncomeParameter {

        LocalUser localUser = utils.getUserFromPrincipal(principal);
        log.info("==================== MOVE WISH (CHANGE MONTH) ======================== ");
        log.info("ID: " + payload.getId());
        log.info("MOVE: " + payload.getStep());
        log.info("PRINCIPAL: " + principal.getName());


        Wish wish = checkParametersAndGetWish(payload);
        int maxPrior = (mainService.getMaxPriority(localUser)) - 1;
        log.info("MAX PRIORITY: " + maxPrior);


        switch (payload.getStep()) {
            case DOWN: //todo: вынести в Enum

                if (maxPrior != 0) {
                    if (wish.getPriorityGroup() < maxPrior + 1) {
                        wish.setPriorityGroup(wish.getPriorityGroup() + 1);
                    }
                    log.info("MOVE SUM: " + (wish.getPriorityGroup() + 1));
                    mainService.updateWish(wish);
                    break;
                }

            case UP:

                if (maxPrior == 0) {
                    log.info("MAX PRIORITY = 0. MOVE SUM: 1");
                    wish.setPriorityGroup(1);
                } else {
                    if (wish.getPriorityGroup() == null) {
                        log.info("MOVE SUM: " + maxPrior);
                        wish.setPriorityGroup(maxPrior);
                    } else if (wish.getPriorityGroup() > 1) {
                        log.info("MOVE SUM: " + (wish.getPriorityGroup() - 1));
                        wish.setPriorityGroup(wish.getPriorityGroup() - 1);
                    }
                }

                mainService.updateWish(wish);
                break;
        }

        if (wish.getCreationDate() == null) wish.setCreationDate(new Date());
        if (wish.getPriorityGroupOrder() == null) wish.setPriorityGroupOrder(1);

        String result = createNullableGsonBuilder().toJson(wish);
        log.info("RESULT: {}", result);
        log.info("====================================================================== ");
        return wish;
    }

    private Wish checkParametersAndGetWish(MoveWishDto payload) throws BadIncomeParameter {

        if ((!"up".equals(payload.getStep())) && (!"down".equals(payload.getStep())))
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.PRIORITYCHANGE);
        return mainService.getWishById(payload.getId()).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
    }
}
