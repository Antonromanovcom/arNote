package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.response.ResponseStatus;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.ControllerBase;
import com.antonromanov.arnote.utils.Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import static com.antonromanov.arnote.utils.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

//todo: надо нормально поименовать ендпоинты
//todo: заменить логи на интерцепторы
//todo: надо сделать проверку на уникальность добавляемых желаний
//todo: прописать JavaDoc везде
//todo: надо уходить от json-билдеров и всего-такого
//todo: избавиться от $do
//todo: добавить тесты
// todo: Надо убрать РеспонсЭнтити и возвращать налормальные ДТО-объекты а не этот пиздец
// SELECT sum(w.price) from arnote.wishes w WHERE w.user_id = 8 AND w.realized AND NOT w.archive - запрос реализованных желаний

/**
 * Основной REST-контроллер приложения.
 */
@CrossOrigin()
@RestController
@RequestMapping("/rest/wishes")
@Slf4j
public class MainRestController extends ControllerBase { //todo: переименовать в WishController

    @Data
    private class DTO {
        private List<Wish> list = new ArrayList<>();
    }


    @Autowired //todo: переехать на связывание через конструктор
    MainService mainService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    private Utils arnoteUtils;


    /**
     * Поиск желаний.
     *
     * @param principal
     * @param requestParam
     * @param resp
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/filter")
    public ResponseEntity<String> findAll(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {

        return $do(s -> {
            log.info("============== FILTER/SEARCH WISHES ============== ");
            log.info("SEARCH KEYWORD: " + requestParam);
            log.info("PRINCIPAL: " + principal.getName());

            LocalUser localUser = arnoteUtils.getUserFromPrincipal(principal);

            List<Wish> wishes = mainService
                    .findAllWishesByWish(parseJsonToWish(Utils.ParseType.EDIT, requestParam, localUser).getWish(), localUser)
                    .orElseGet(ArrayList::new);

            DTO dto = new DTO(); // todo: добавить билдеры
            dto.list.addAll(wishes);

            String res = createGsonBuilder().toJson(dto);
            log.info("PAYLOAD: " + res);

            return $prepareResponse(res);

        }, null, null, null, resp);
    }

    /**
     * Получить все желания.
     *
     * @param principal
     * @param type
     * @param resp
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/{type}")
    public ResponseEntity<String> getAllWishes(Principal principal, @PathVariable String type, HttpServletResponse resp) {

        return $do(s -> {
            List<Wish> wishList;

            log.info("==================== GET WISHES ======================== ");
            log.info("type: " + type);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("======================================================== ");

            LocalUser localUser = arnoteUtils.getUserFromPrincipal(principal);
            if (mainService.getAllWishesByUserId(localUser).size() > 0) {

                DTO dto = new DTO();
                String result = "";

                if ("all".equalsIgnoreCase(type)) {
                    wishList = mainService.getAllWishesByUserId(localUser);
                    // Предотвращение вываливания на пустых датах
                    wishList.forEach(w -> {
                        if (w.getCreationDate() == null) w.setCreationDate(new Date());
                        if (w.getRealized() == null) w.setRealized(false);
                    });

                    dto.list.addAll(wishList);
                    result = createNullableGsonBuilder().toJson(dto);
                } else {
                    wishList = mainService.getAllWishesWithPriority1(localUser);
                    dto.list.addAll(wishList);
                    result = createNullableGsonBuilder().toJson(dto);
                }

                return $prepareResponse(result);
            } else {
                return $prepareNoDataYetErrorResponse(false);
            }
        }, null, null, OperationType.GET_ALL_WISHES, resp);
    }

    @CrossOrigin(origins = "*")
    @PutMapping
    public ResponseEntity<String> updateWish(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {

        return $do(s -> {

            log.info("==================== UPDATE WISHES ======================== ");
            log.info("PAYLOAD: " + requestParam);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("=========================================================== ");

            LocalUser localUser = arnoteUtils.getUserFromPrincipal(principal);
            Wish wish = parseJsonToWish(ParseType.EDIT, requestParam, localUser);
            mainService.updateWish(mainService.updateMonthGroup(wish));

            String result = "";
            return $prepareResponse(result);

        }, requestParam, null, OperationType.EDIT_WISH, resp);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<String> addWish(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {


        return $do(s -> {

            log.info("==================== ADD WISHES ======================== ");
            log.info("PAYLOAD: " + requestParam);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("======================================================== ");

            LocalUser localUser = arnoteUtils.getUserFromPrincipal(principal);

            Wish newWish;
            newWish = mainService.addWish(parseJsonToWish(ParseType.ADD, requestParam, localUser));

            // Предотвращение вываливания на пустых датах
            if (newWish.getCreationDate() == null) newWish.setCreationDate(new Date());
            if (newWish.getRealized() == null) newWish.setRealized(false);
            if (newWish.getRealizationDate() == null) newWish.setRealizationDate(new Date());

            String result = createGsonBuilder().toJson(newWish);
            return $prepareResponse(result);

        }, requestParam, null, OperationType.ADD_WISH, resp);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWish(Principal principal, @PathVariable String id, HttpServletResponse resp) {

        return $do(s -> {

            log.info("==================== DELETE WISH ======================== ");
            log.info("ID: " + id);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("===================================================== ");

            Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID));
            wish.setAc(true);
            mainService.updateWish(wish);
            return $prepareResponse(createGsonBuilder().toJson(ResponseStatus.builder().okMessage("OK").status("OK").build()));
        }, null, null, null, resp);
    }



    //todo: надо понять какой метод нужен то - этот или changeMonth и какой что выполняет
    @CrossOrigin(origins = "*")
    @GetMapping("/changepriority/{id}/{move}")
    public ResponseEntity<String> changePriority(Principal principal, @PathVariable String id, @PathVariable String move, HttpServletResponse resp) {

        return $do(s -> {

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
}
