package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.response.WishDTO;
import com.antonromanov.arnote.dto.response.WishList;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.enums.SortMode;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import static com.antonromanov.arnote.utils.Utils.createNullableGsonBuilder;
import static com.antonromanov.arnote.utils.Utils.parseMonthAndCalculatePriority;
import static org.apache.commons.lang3.StringUtils.isBlank;

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

    @Data //todo: вынести отседава, добавить билдер, переименовать
    private class DtoWithOrder {
        private List<WishList> list = new ArrayList<>();
    }

    @Data //todo: вынести отседава, добавить билдер, переименовать
    public static class MoveWishDto {
        private String id; //todo: почему string?????
        private String month;
        private String step; //todo: ENUM????
    }

    /**
     * Получить все желания с группировкой по месяцам.
     *
     * @param principal
     * @param sortType
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping
    public DtoWithOrder getAllWishesWithMonthGrouping(Principal principal,
                                                      @RequestParam String sortType) throws UserNotFoundException { //todo: сорт-тайпы вынести в константы или енумы

        List<WishList> wishListWithMonthOrder;

        log.info("============== GET ALL WISHES WITH MONTH GROUPING ============== ");
        log.info("SORT TYPE: " + sortType);
        log.info("PRINCIPAL: " + principal.getName());

        LocalUser localUser = utils.getUserFromPrincipal(principal);

        if (mainService.getAllWishesByUserId(localUser).size() > 0) {

            DtoWithOrder dtOwithOrder = new DtoWithOrder(); // todo: билдер + вынести это куда-то
            String result = "";
            String finalSortType = sortType; // todo: очень не красивое решение - надо что-то с этим делать.
            wishListWithMonthOrder = mainService.getAllWishesWithGroupPriority(localUser);

            dtOwithOrder.list.addAll(wishListWithMonthOrder); // todo: почему .list, а не getlist() ????

            if (("all".equalsIgnoreCase(finalSortType))
                    && (localUser.getSortMode() != SortMode.ALL)
                    && (localUser.getSortMode() != null)) { // todo: проверяем не сохранен ли до этого режим отображения и если сохранен - выбираем его. Но  вообще это костылище и код не красивый - надо разбираться с этим
                finalSortType = localUser.getSortMode().getUiValue();
            }

            if ("name".equalsIgnoreCase(finalSortType)) { // todo: обработать какой-нить мапой ИФы и сделать все в функциональном стиле
                dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(Comparator.comparing(WishDTO::getWish))); // todo: почему .list, а не getlist() ????
                localUser.setSortMode(SortMode.NAME);
                usersRepo.saveAndFlush(localUser);
            } else if ("price-asc".equalsIgnoreCase(finalSortType)) {
                dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(Comparator.comparing(WishDTO::getPrice)));
                localUser.setSortMode(SortMode.PRICE_ASC);
                usersRepo.saveAndFlush(localUser);
            } else if ("all".equalsIgnoreCase(finalSortType)) {
                localUser.setSortMode(SortMode.ALL);
                usersRepo.saveAndFlush(localUser);
            } else if ("price-desc".equalsIgnoreCase(finalSortType)) { //todo: утащить в ЕНУМы
                Comparator<WishDTO> comparator = Comparator.comparing(WishDTO::getPrice);
                dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(comparator.reversed()));
                localUser.setSortMode(SortMode.PRICE_DESC);
                usersRepo.saveAndFlush(localUser);
            }

            log.info("Данные по пользователю после запроса. Тип отображения: {}, Групповая сортировка: {}",
                    localUser.getViewMode() == null ? "N/A" : localUser.getViewMode(),
                    localUser.getSortMode() == null ? "N/A" : localUser.getSortMode().getUiValue());

            return dtOwithOrder;
        } else {
            return null; // todo: тут надо подумать что делать. Тут не эксепшн надо пробрасывать, а просто возвращать пустой список
        }
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

        Wish wish = mainService.getWishById(Integer.parseInt(payload.getId())).orElseThrow(() ->
                new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
        wish.setPriorityGroup(parseMonthAndCalculatePriority(payload.getMonth()));
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


        Wish wish = checkParametersAndGetWish(payload.getId(), payload.getStep());
        int maxPrior = (mainService.getMaxPriority(localUser)) - 1;
        log.info("MAX PRIORITY: " + maxPrior);


        switch (payload.getStep()) {
            case "down": //todo: вынести в Enum

                if (maxPrior != 0) {
                    if (wish.getPriorityGroup() < maxPrior + 1) {
                        wish.setPriorityGroup(wish.getPriorityGroup() + 1);
                    }
                    log.info("MOVE SUM: " + (wish.getPriorityGroup() + 1));
                    mainService.updateWish(wish);
                    break;
                }

            case "up":

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

    private Wish checkParametersAndGetWish(String id, String move) throws BadIncomeParameter { //todo: ЭТО ДУБЛИКАТ!!!! Вынести в Утилиты!

        if ((!"up".equals(move)) && (!"down".equals(move)))
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.PRIORITYCHANGE);
        if ((isBlank(id)) || (!Pattern.compile("^\\d*$").matcher(id).matches()))
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID);
        return mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
    }
}
