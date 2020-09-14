package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.response.DTO;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.enums.ListOfAllType;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;


//todo: заменить логи на интерцепторы
//todo: надо сделать проверку на уникальность добавляемых желаний
//todo: прописать JavaDoc везде
//todo: еррорхендлер сделать и проверить что он отрабатывает на фронте нормально
//todo: добавить тесты
// SELECT sum(w.price) from arnote.wishes w WHERE w.user_id = 8 AND w.realized AND NOT w.archive - запрос реализованных желаний

/**
 * REST-контроллер управления желаниями.
 */
@CrossOrigin()
@RestController
@RequestMapping("/wish")
@Slf4j
@AllArgsConstructor
public class WishController {

    private final MainService mainService;
    private final Utils utils;

    /**
     * Поиск желаний.
     *
     * @param principal
     * @param wish
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/filter")
    public DTO findAll(Principal principal, @RequestBody Wish wish) throws Exception {
        return DTO.builder()
                .list(mainService
                        .findAllWishesByWish(wish, utils.getUserFromPrincipal(principal))
                        .orElseGet(ArrayList::new))
                .build();
    }

    /**
     * Получить все желания.
     *
     * @param principal
     * @param type      тип enum'а ListOfAllType.
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/{type}") //todo: поменять на ?type=....
    public DTO getAllWishes(Principal principal, @PathVariable String type) throws UserNotFoundException, BadIncomeParameter { //todo: эксепшены при пустых желаниях и неверном type.
        return DTO.builder()
                .list(mainService.getAllWishes(utils.getUserFromPrincipal(principal), Arrays
                        .stream(ListOfAllType.values())
                        .filter(t -> t.getUiValue().equals(type))
                        .findFirst()
                        .orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_MONTH)))) //todo: другой тип
                .build();

    }

    /**
     * Изменить желание.
     *
     * @param wish
     * @param principal
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping
    public Wish updateWish(Principal principal, @RequestBody Wish wish) throws BadIncomeParameter {
        return mainService.updateWish(mainService.updateMonthGroup(wish));
    }

    /**
     * Добавить новое желание.
     *
     * @param principal
     * @param wish      - новое желание.
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping
    public Wish addWish(Principal principal, @RequestBody Wish wish) {
        return mainService.addWish(wish);
    }

    /**
     * Удалить желание.
     *
     * @param principal
     * @param id
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}") //todo: тоже убирать надо, менять на ?id=...
    public Wish deleteWish(Principal principal, @PathVariable String id) throws BadIncomeParameter {
        Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID));
        wish.setAc(true); //todo: надо подумать можно ли сделать одной строкой как-нить.
        return mainService.updateWish(wish);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/move")
    public Wish changePriority(Principal principal, @RequestBody MonthPriorityController.MoveWishDto dto) {

        if ("down".equals(dto.getStep())) {
            if (mainService.getWishById(Integer.parseInt(dto.getId())).get().getPriority() > 1) { //todo: с этим пиздецом надо что-то думать
                (mainService.getWishById(Integer.parseInt(dto.getId())).get())
                        .setPriority((mainService.getWishById(Integer.parseInt(dto.getId())).get()).getPriority() - 1);
            }
            return mainService.updateWish(mainService.getWishById(Integer.parseInt(dto.getId())).get());
        }
        (mainService.getWishById(Integer.parseInt(dto.getId())).get())
                .setPriority((mainService.getWishById(Integer.parseInt(dto.getId())).get()).getPriority() + 1);
        return mainService.updateWish(mainService.getWishById(Integer.parseInt(dto.getId())).get());
    }
}
