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
    public GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, @RequestParam(required = false) String sortType) {
        return wishService.getAllWishesWithMonthGrouping(principal, sortType);
    }


    @CrossOrigin(origins = "*")
    @PutMapping("/transfer") //todo: переименовать + дока
    public WishRs changeMonthOrder(Principal principal, @Valid @RequestBody WishTransferRq request) {
         return wishService.transferWish(request);
    }


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
    @CrossOrigin(origins = "*")
    @PutMapping("/change-priority")
    public WishRs changePriority(Principal principal, @Valid @RequestBody ChangePriorityRq payload) {
        return wishService.oneStepChangePriority(payload);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/change-month")
    public WishRs changeMonth(Principal principal, @Valid @RequestBody  ChangeTargetMonthRq payload) {
        return wishService.oneStepChangeTargetMonth(payload);
    }

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
