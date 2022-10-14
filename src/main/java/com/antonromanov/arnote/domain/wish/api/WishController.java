package com.antonromanov.arnote.domain.wish.api;

import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rs.*;
import com.antonromanov.arnote.domain.wish.dto.rq.*;
import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;


//todo: надо нормально поименовать ендпоинты
//todo: заменить логи на интерцепторы
//todo: надо сделать проверку на уникальность добавляемых желаний
//todo: прописать JavaDoc везде
//todo: надо уходить от json-билдеров и всего-такого
//todo: избавиться от $do
//todo: добавить тесты
// todo: Надо убрать РеспонсЭнтити и возвращать нрмальные ДТО-объекты а не этот пиздец


/**
 * REST-контроллер для Желаний.
 */
@CrossOrigin()
@RestController
@RequestMapping("/rest/wishes") //todo: поменять все урлы на нормальные
@Slf4j
@AllArgsConstructor
public class WishController {

    private final WishService wishService;

    /**
     * Получить все желания.
     *
     * @param principal - JWT-токена пользователя
     * @param filter    - тип фильтрации: все или только приоритетные
     * @param sort      - собственно сортировка
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping
    public WishListRs getAllWishes(Principal principal, @RequestParam(required = false) String filter,
                                   @RequestParam(required = false) String sort) {
        return wishService.getAllWishesByUserId(principal, filter, sort);
    }

    /**
     * Поиск желаний.
     *
     * @param principal - пользователь.
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/filter")
    // todo: почему фильтр-то? Это постоянно вводит в заблуждение. Это фильтр все-таки или поиск???
    public WishListRs findWishes(Principal principal, @Valid @RequestBody SearchWishRq request) throws UserNotFoundException {
        return wishService.findWishesByName(request.getWishName(), principal);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public WishRs addWish(Principal principal, @Valid @RequestBody WishRq request) {
        return wishService.addWish(request, principal);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public WishRs deleteWish(@RequestParam String id) {
        return wishService.deleteWish(id);
    }

    /**
     * Получить все желания с группировкой по месяцам.
     *
     * @param principal
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/groups") // todo: переименовать
    public GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, @RequestParam String sortType) {
        return wishService.getAllWishesWithMonthGrouping(principal, sortType);
    }

  /*  @CrossOrigin(origins = "*")
    @GetMapping("/transferwish")
    public ResponseEntity<String> changeMonthOrder(Principal principal, @RequestParam String id, @RequestParam String month, HttpServletResponse resp) {
        return $do(s -> {

            log.info("========= MOVE WISH (CHANGE MONTH PRIORITY) ============== ");
            log.info("id: " + id);
            log.info("month: " + month);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("=========================================================== ");

            Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() ->
                    new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
            wish.setPriorityGroup(parseMonthAndCalculatePriority(month));
            String result = createNullableGsonBuilder().toJson(mainService.updateAndFlushWish(wish));
            log.info("ОТВЕТ: {}", result); // todo: если prepareResponse отдает нормальный ответ - эту строчку удалим. Ну или надо сделать, чтобы он отдавал нормальный ответ
            return $prepareResponse(result);

        }, null, null, OperationType.EDIT_WISH, resp);
    }*/
    @CrossOrigin(origins = "*")
    @PutMapping
    public WishRs updateWish(Principal principal, @Valid @RequestBody WishRq request) {
        return wishService.updateWish(principal, request);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/summ") //todo: поменять УРЛ
    //todo: посмотреть, можно ли как-то использовать security holder contex чтобы не таскать везде принципала.
    public WishAnalyticsRs getAnalytics(Principal principal) {
        return wishService.getWishAnalytics(principal);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/salary") //todo: вынести в отдельный контроллер.
    public SalaryRs addSalary(Principal principal, @RequestBody SalaryRq request) {
        return wishService.addSalary(request, principal);
    }

    //todo: надо понять какой метод нужен то - этот или changeMonth и какой что выполняет
   /* @CrossOrigin(origins = "*")
    @GetMapping("/changepriority/{id}/{move}")
    public ResponseEntity<String> changePriority(Principal principal, @PathVariable String id, @PathVariable String move, HttpServletResponse resp) {

        return $do(s -> {
  01-
            log.info("==================== MOVE WISH (CHANGE PRIORITY) ======================== ");
            log.info("ID: " + id);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("========================================================================= ");

            Wish wish = checkParametersAndGetWish(id, move);

            switch (move) {
                case "down":
                    if (wish.getPriority() > 1) wish.setPriority(wish.getPriority() - 1);
                    mainService.updateWish(wish);
                    break;

                case "up":
                    wish.setPriority(wish.getPriority() + 1);
                    mainService.updateWish(wish);
                    break;
            }

            String result = createNullableGsonBuilder().toJson(wish);

            return $prepareResponse(result);

        }, null, null, null, resp);
    }

    private Wish checkParametersAndGetWish(String id, String move) throws BadIncomeParameter {

        if ((!"up".equals(move)) && (!"down".equals(move)))
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.PRIORITYCHANGE);
        if ((isBlank(id)) || (!Pattern.compile("^\\d*$").matcher(id).matches()))
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID);
        return mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/changemonth/{id}/{move}")
    public ResponseEntity<String> changeMonth(Principal principal, @PathVariable String id, @PathVariable String move, HttpServletResponse resp) {

        return $do(s -> {
            ArNoteUser localUser = getUserFromPrincipal(principal);
            log.info("==================== MOVE WISH (CHANGE MONTH) ======================== ");
            log.info("ID: " + id);
            log.info("MOVE: " + move);
            log.info("PRINCIPAL: " + principal.getName());


            Wish wish = checkParametersAndGetWish(id, move);
            int maxPrior = (mainService.getMaxPriority(localUser)) - 1;
            log.info("MAX PRIORITY: " + maxPrior);


            switch (move) {
                case "down":

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

            Date currentDate = new Date();
            if (wish.getCreationDate() == null) wish.setCreationDate(currentDate);
            if (wish.getPriorityGroupOrder() == null) wish.setPriorityGroupOrder(1);

            String result = createNullableGsonBuilder().toJson(wish);
            log.info("RESULT: {}", result);
            log.info("====================================================================== ");
            return $prepareResponse(result);

        }, null, null, null, resp);
    }*/

        @CrossOrigin(origins = "*")
        @PostMapping("/users") //todo: вынести в отдельный контроллер
        public LocalUserRs addUser(@RequestBody LocalUserRq user) {
            return wishService.addUser(user); //todo: вынести в отдельный сервис
        }



    //todo: АААААА! Это полная пизда вообще!!!!!! Должен быть отдельный контроллер для юзерских действий и там два метода отдельных! Один для получения, другой для добавления!
    @CrossOrigin(origins = "*")
    @PostMapping("/users/toggle")
    public LocalUserRs toggleUserMode(Principal principal, @RequestBody ToggleUserModeRq mode) {
        return wishService.toggleUserMode(principal, mode); //todo: вынести в отдельный сервис

    }

    @CrossOrigin(origins = "*") //todo: getcurrent переименовать в /current
    @GetMapping("/users/getcurrent") //todo: вынести в отдельный контроллер
    public LocalUserRs getCurrentUser(Principal principal) {
        return wishService.getCurrentUser(principal); //todo: вынести в отдельный сервис
    }
}
