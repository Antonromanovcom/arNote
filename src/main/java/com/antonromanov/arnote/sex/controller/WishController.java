package com.antonromanov.arnote.sex.controller;

import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.NoDataYetException;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;


//todo: заменить логи на интерцепторы
//todo: надо сделать проверку на уникальность добавляемых желаний
//todo: прописать JavaDoc везде
//todo: еррорхендлер сделать и проверить что он отрабатывает на фронте нормально
//todo: добавить тесты
// SELECT sum(w.price) from arnote.wishes w WHERE w.user_id = 8 AND w.realized AND NOT w.archive - запрос реализованных желаний

/**
 * REST-контроллер управления желаниями.
 */
/*@CrossOrigin()
@RestController
@RequestMapping("/wish")
@Slf4j
@AllArgsConstructor*/
public class WishController {

    /*private final MainService mainService;
    private final UserService userService;*/

    /**
     * Поиск желаний.
     *
     * @param principal
     * @param wish
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/filter")
    public WishListResponse findAll(Principal principal, @RequestBody Wish wish) throws Exception {
        return WishListResponse.builder()
                .list(mainService.findWishesByName(wish, userService.getUserFromPrincipal(principal))
                        .orElseGet(ArrayList::new))
                .build();
    }*/

    /**
     * Получить все желания.
     *
     * @param principal - пользователь.
     * @param filterType - Что показываем: все желания или приоритетные
     * @param sortType - типы сортировки.
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping
    public WishListResponse getAllWishes(Principal principal, @RequestParam FilterMode filterType, @RequestParam SortMode sortType)
            throws UserNotFoundException, NoDataYetException {

        if (mainService.getAllWishesByUser(utils.getUserFromPrincipal(principal)).isEmpty()) {
            throw new NoDataYetException(false); //todo: разобраться с этим моментом
        } else {
            return WishListResponse.builder()
                    .list(mainService.getAllWishesAndUpdateUser(utils.getUserFromPrincipal(principal), filterType, sortType))
                    .build();
        }
    }*/

    /**
     * Изменить желание.
     *
     * @param wish
     * @param principal
     * @return
     */
    /*@CrossOrigin(origins = "*")
    @PutMapping
    public Wish updateWish(Principal principal, @RequestBody Wish wish) throws BadIncomeParameter, UserNotFoundException {
        wish.setUser(utils.getUserFromPrincipal(principal));
        wish.setRealizationDate(new Date());
        return mainService.updateWish(mainService.updateMonthGroup(wish));
    }*/

    /**
     * Добавить новое желание.
     *
     * @param principal
     * @param wish - новое желание.
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping
    public Wish addWish(Principal principal, @RequestBody Wish wish) throws UserNotFoundException {
        wish.setUser(utils.getUserFromPrincipal(principal));
        wish.setCreationDate(new Date());
        wish.setRealized(false);
        wish.setAc(false);
        return mainService.addWish(wish);
    }*/

    /**
     * Удалить желание.
     *
     * @param principal
     * @param id
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @DeleteMapping
    public Wish deleteWish(Principal principal, @RequestParam String id) throws BadIncomeParameter {
        Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() ->
                new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
        wish.setAc(true); //todo: билдер или отдельное дто для wishResponse ???
        return mainService.updateWish(wish);
    }*/

    /**
     * Дискретное изменение приоритета.
     *
     * @param principal
     * @param dto
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/move")
    public Wish changePriority(Principal principal, @RequestBody MoveWishDto dto) throws BadIncomeParameter {
        return mainService.getWishById(dto.getId())
                .map(wish ->  dto.getStep().getChangePriority().move(wish, mainService))
                .orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
    }*/
}
