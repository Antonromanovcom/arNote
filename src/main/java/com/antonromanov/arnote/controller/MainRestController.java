package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.email.EmailSender;
import com.antonromanov.arnote.email.EmailStatus;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.ResponseStatusDTO;
import com.antonromanov.arnote.model.investing.InvestingFilterMode;
import com.antonromanov.arnote.model.investing.InvestingSortMode;
import com.antonromanov.arnote.model.wish.*;
import com.antonromanov.arnote.model.wish.enums.WishsFilters;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.ControllerBase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
public class MainRestController extends ControllerBase {

    @Data
    private static class DTO {
        private List<Wish> list = new ArrayList<>();
    }

    @Data
    private static class DtoWithOrder {
        private List<WishDTOList> list = new ArrayList<>();
    }

    @Autowired //переехать на связывание через конструктор
    MainService mainService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    private EmailSender emailSender;


    /**
     * Поиск желаний.
     *
     * @param principal
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/filter")
    public DTO findAll(Principal principal, @RequestBody SearchRq request) throws UserNotFoundException {
            log.info("============== FILTER/SEARCH WISHES ============== ");
            log.info("SEARCH KEYWORD: " + request.getWishName());
            List<Wish> wishes = mainService.findAllWishesByWishName(request,  getUserFromPrincipal(principal));
            DTO dto = new DTO(); //todo: добавить билдеры
            dto.list.addAll(wishes);
            return dto;
    }

    /**
     * Получить все желания с группировкой по месяцам.
     *
     * @param principal
     * @param sortType
     * @param resp
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/groups") // todo: переименовать
    public ResponseEntity<String> getAllWishesWithMonthGrouping(Principal principal,
                                                                @RequestParam String sortType, //todo: сорт-тайпы вынести в константы или енумы
                                                                HttpServletResponse resp) {

        return $do(s -> {
            List<WishDTOList> wishListWithMonthOrder;

            log.info("============== GET ALL WISHES WITH MONTH GROUPING ============== ");
            log.info("SORT TYPE: " + sortType);
            log.info("PRINCIPAL: " + principal.getName());

            LocalUser localUser = getUserFromPrincipal(principal);

            if (mainService.getAllWishesByUserId(localUser).size() > 0) {

                DtoWithOrder dtOwithOrder = new DtoWithOrder(); //todo: билдер
                String result = "";
                String finalSortType = sortType; //todo: очень не красивое решение - надо что-то с этим делать.
                wishListWithMonthOrder = mainService.getAllWishesWithGroupPriority(localUser);

                dtOwithOrder.list.addAll(wishListWithMonthOrder); //todo: почему .list, а не getlist() ????

                if (("all".equalsIgnoreCase(finalSortType))
                        && (localUser.getSortMode()!=SortMode.ALL)
                        && (localUser.getSortMode()!=null)) { //todo: проверяем не сохранен ли до этого режим отображения и если сохранен - выбираем его. Но  вообще это костылище и код не красивый - надо разбираться с этим
                    finalSortType = localUser.getSortMode().getUiValue();
                }

                if ("name".equalsIgnoreCase(finalSortType)) { // todo: обработать какой-нить мапой ИФы и сделать все в функциональном стиле
                    dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(Comparator.comparing(WishDTO::getWish))); //todo: почему .list, а не getlist() ????
                    localUser.setSortMode(SortMode.NAME);
                    usersRepo.saveAndFlush(localUser);
                } else if ("price-asc".equalsIgnoreCase(finalSortType)) {
                    dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(Comparator.comparing(WishDTO::getPrice)));
                    localUser.setSortMode(SortMode.PRICE_ASC);
                    usersRepo.saveAndFlush(localUser);
                } else if ("all".equalsIgnoreCase(finalSortType)) {
                    localUser.setSortMode(SortMode.ALL);
                    usersRepo.saveAndFlush(localUser);
                } else if ("price-desc".equalsIgnoreCase(finalSortType)) {
                    Comparator<WishDTO> comparator = Comparator.comparing(WishDTO::getPrice);
                    dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(comparator.reversed()));
                    localUser.setSortMode(SortMode.PRICE_DESC);
                    usersRepo.saveAndFlush(localUser);
                }

                log.info("Данные по пользователю после запроса. Тип отображения: {}, Групповая сортировка: {}",
                        localUser.getViewMode()==null? "N/A" : localUser.getViewMode(),
                        localUser.getSortMode()==null ? "N/A": localUser.getSortMode().getUiValue());
                result = createNullableGsonBuilder().toJson(dtOwithOrder);

                return $prepareResponse(result);
            } else {
                return $prepareNoDataYetErrorResponse(false);
            }
        }, null, null, null, resp);
    }

    @CrossOrigin(origins = "*")
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
    }

    /**
     * Получить все желания.
     *
     * @param principal
     * @param filter - тип фильтраци: все или только приоритетные
     * @param sort - собственно сортировка
     * @param resp
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<String> getAllWishes(Principal principal,
                                               @RequestParam(required = false) String filter,
                                               @RequestParam(required = false) String sort,
                                               HttpServletResponse resp) {

        return $do(s -> {
            List<Wish> wishList;

            log.info("==================== GET WISHES ======================== ");
            log.info("filter: " + filter);
            log.info("sort: " + sort);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("======================================================== ");

            LocalUser localUser = getUserFromPrincipal(principal);

            /*
             * Логика такая:
             *
             * - если фильтр приходит не пустой - задаем и сохраняем новый фильтр.
             * - если фильтр приходит не пустой, но он NONE, просто удаляем сохраненный фильтр из записи пользака.
             * - если filter пришел пустой - выдаем то, что есть с той фильтрацией, что сохранена.
             *
             */
            if (filter != null) {
                if (WishsFilters.valueOf(filter) == WishsFilters.NONE) {
                    localUser.setViewMode();
                    user = usersRepo.saveAndFlush(user);
                } else {
                    if (user.getInvestingFilterMode() != null && user.getInvestingFilterMode().size() > 0) {
                        user.getInvestingFilterMode().put(InvestingFilterMode.valueOf(filter).getKey(), filter);
                    } else {
                        Map<String, String> filterMap = new HashMap<>();
                        filterMap.put(InvestingFilterMode.valueOf(filter).getKey(), filter);
                        user.setInvestingFilterMode(filterMap);
                    }
                    user = usersRepo.saveAndFlush(user);
                }
            }

            if (sort != null) {
                if (InvestingSortMode.valueOf(sort) == InvestingSortMode.NONE) {
                    user.setInvestingSortMode(null);
                    user = usersRepo.saveAndFlush(user);
                } else {
                    user.setInvestingSortMode(InvestingSortMode.valueOf(sort));
                    user = usersRepo.saveAndFlush(user);
                }
            }


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

            LocalUser localUser = getUserFromPrincipal(principal);
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

            LocalUser localUser = getUserFromPrincipal(principal);

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
    @GetMapping("/summ")
    public ResponseEntity<String> getSum(Principal principal, HttpServletResponse resp) {

        return $do(s -> {
            int days = 0;
            int implementedSumAllTime = 0;
            int implementedSumMonth = 0;

            LocalUser localUser = getUserFromPrincipal(principal);

            if (mainService.getAllRealizedWishes(localUser).isPresent()) {

                List<Long> realizedWishes = mainService.getAllRealizedWishes(localUser).get().stream()
                        .filter(wf -> wf.getRealizationDate() != null && wf.getCreationDate() != null)
                        .map(w -> (w.getRealizationDate().getTime() - w.getCreationDate().getTime())).collect(Collectors.toList());

                days = (realizedWishes.size()==0) ? 0 : (30 / realizedWishes.size());
                implementedSumAllTime = mainService.getImplementedSum(localUser, 1).orElseGet(() -> 0);
                implementedSumMonth = mainService.getImplementedSum(localUser, 2).orElseGet(() -> 0);
            }

            if (mainService.getLastSalary(localUser) != null) {
                String result = createGsonBuilder().toJson(SummEntity.builder()
                        .all(mainService.getSumm4All(localUser))
                        .allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4All(localUser), localUser))
                        .priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4Prior(localUser), localUser))
                        .lastSalary(mainService.getLastSalary(localUser).getResidualSalary())
                        .averageImplementationTime(days)
                        .implemetedSummAllTime(implementedSumAllTime)
                        .implemetedSummMonth(implementedSumMonth)
                        .priority(mainService.getSumm4Prior(localUser)).build());

                log.info("==================== GET SUM ======================== ");
                log.info("PAYLOAD: " + result);
                log.info("PRINCIPAL: " + principal.getName());
                log.info("===================================================== ");

                return $prepareResponse(result);
            } else {
                return $prepareNoDataYetErrorResponse(true);
            }
        }, null, principal, OperationType.GET_SUMS, resp);
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
            return $prepareResponse(createGsonBuilder().toJson(ResponseStatusDTO.builder().okMessage("OK").status("OK").build()));
        }, null, null, null, resp);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/last")
    public ResponseEntity<String> getLastSalary(Principal principal, HttpServletResponse resp) {

        return $do(s -> {

            LocalUser localUser = getUserFromPrincipal(principal);
            String result = createGsonBuilder().toJson(mainService.getLastSalary(localUser).getResidualSalary());
            log.info("==================== GET LAST SALARY ======================== ");
            log.info("PAYLOAD: " + result);
            log.info("PRINCIPAL: " + principal.getName());
            log.info("============================================================= ");
            return $prepareResponse(result);
        }, null, null, null, resp);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/salary")
    public ResponseEntity<String> addSalary(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {


        return $do(s -> {

            log.info("==================== ADD SALARY ======================== ");
            log.info("PAYLOAD: " + requestParam);
            log.info("PRINCIPAL: " + principal.getName());

            LocalUser localUser = getUserFromPrincipal(principal);
            Salary newSalary;
            newSalary = mainService.saveSalary(parseJsonToSalary(requestParam, localUser));
            String result = createGsonBuilder().toJson(newSalary);
            log.info("PAYLOAD: " + result);
            log.info("======================================================== ");

            return $prepareResponse(result);

        }, requestParam, null, null, resp);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/parsecsv")
    public ResponseEntity<String> parseCsv(Principal principal,
                                           @RequestParam(required = false, value = "csvfile") MultipartFile csvFile,
                                           HttpServletResponse resp) {

        return $do(s -> {

//			LOGGER.info("FILE: " + csvFile.getOriginalFilename());
            LocalUser localUser = getUserFromPrincipal(principal);
//			LOGGER.info("PRINCIPAL: " + localUser.toString());

            String result = createGsonBuilder().toJson(mainService.parseCsv(csvFile, localUser));
            return $prepareResponse(result);

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


    @CrossOrigin(origins = "*")
    @GetMapping("/changemonth/{id}/{move}")
    public ResponseEntity<String> changeMonth(Principal principal, @PathVariable String id, @PathVariable String move, HttpServletResponse resp) {

        return $do(s -> {
            LocalUser localUser = getUserFromPrincipal(principal);
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
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody String user, HttpServletResponse resp) {


        return $do(s -> {

		/*	LOGGER.info("========= ADD USER  ============== ");
			LOGGER.info("PAYLOAD: " + user);*/

            LocalUser newUser = parseJsonToUserAndValidate(user);
            newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));
            newUser.setViewMode("TABLE");

            if (usersRepo.findByLogin(newUser.getLogin()).isPresent()) {
                throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
            }
            usersRepo.save(newUser);
            return $prepareResponse(createGsonBuilder().toJson(newUser));

        }, null, null, null, resp);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(Principal principal, @PathVariable String id, HttpServletResponse resp) {


        return $do(s -> {

			/*LOGGER.info("========= DELETE USER  ============== ");
			LOGGER.info("PAYLOAD: " + id);*/

            if (!usersRepo.findById(Long.valueOf(id)).isPresent()) {
                throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST);
            }

            usersRepo.deleteById(Long.valueOf(id));

            return $prepareResponse(createGsonBuilder().toJson(id));

        }, id, null, null, resp);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}")
    public ResponseEntity<String> editUser(Principal principal, @RequestBody String user, @PathVariable String id, HttpServletResponse resp) {

        return $do(s -> {

		/*	LOGGER.info("========= EDIT USER  ============== ");
			LOGGER.info("PAYLOAD: " + user);
			LOGGER.info("id: " + id);*/

            LocalUser newUser = parseJsonToUserAndValidate(user);
            LocalUser localuser = getUserFromPrincipal(principal);
            newUser.setCreationDate(localuser.getCreationDate());

            if ((usersRepo.findByLogin(newUser.getLogin()).isPresent()) && (!localuser.getLogin().equals(newUser.getLogin()))) {
                throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
            }

            newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));
            localuser.setPwd(newUser.getPwd());
            localuser.setLogin(newUser.getLogin());
            localuser.setEmail(newUser.getEmail());
            localuser.setFullname(newUser.getFullname());

            fixNullUserFields(localuser);

            return $prepareResponse(createGsonBuilder().toJson(usersRepo.saveAndFlush(localuser)));

        }, user, null, OperationType.UPDATE_USER, resp);
    }

//todo: АААААА! Это полная пизда вообще!!!!!! Должен быть отдельный контроллер для юзерских действий и там два метода отдельных! Один для получения, другой для добавления!
    @CrossOrigin(origins = "*")
    @GetMapping("/users/toggle/{mode}")
    public ResponseEntity<String> toggleUserMode(Principal principal, @PathVariable String mode, HttpServletResponse resp) {

        return $do(s -> {

            log.info("========= TOGGLE / GET USER MODE ============== ");
            log.info("MODE: " + mode);

            LocalUser localuser = getUserFromPrincipal(principal);

            if (("TABLE".equals(mode)) || ("TREE".equals(mode))) {
                localuser.setViewMode(mode);
                return $prepareResponse(createGsonBuilder().toJson(usersRepo.saveAndFlush(localuser)));
            } else if ("GET".equals(mode)) { //todo: вот эту жесть конечно же надо убрать будет и исправить на фронте
              //  localuser.setViewMode("TABLE");
                return $prepareResponse(createGsonBuilder().toJson(localuser));
            } else {
                return $prepareBadResponse(createGsonBuilder().toJson("Bad mode parameter!"));
            }

        }, null, null, OperationType.TOGGLE_USER_MODE, resp);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/list")
    public ResponseEntity<String> getAllUsers(Principal principal, HttpServletResponse resp) {

        return $do(s -> {
            log.info("========= GET ALL USERS  ============== ");

            List<LocalUser> userList = usersRepo.findAll().stream().map(u -> {
                if (u.getCreationDate() == null) u.setCreationDate(new Date());
                return u;
            }).collect(Collectors.toList());

            return $prepareResponse(createGsonBuilder().toJson(userList));
        }, null, null, null, resp);
    }

    private void fixNullUserFields(LocalUser localUser) {
        // Проверяем на заполненность пользовательских данных, чтобы не отваливались эксепшены:
        if (localUser.getUserRole() == null) localUser.setUserRole(LocalUser.Role.USER);
        if (localUser.getUserCryptoMode() == null) localUser.setUserCryptoMode(false);
        if (localUser.getCreationDate() == null) localUser.setCreationDate(new Date());
        if (localUser.getEmail() == null) localUser.setEmail("antonr0manov@yndex.ru");
        if (localUser.getFullname() == null) localUser.setFullname("Имя неизвестно");
        if (localUser.getViewMode() == null) localUser.setViewMode("TABLE");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/getcurrent")
    public ResponseEntity<String> getCurrentUser(Principal principal, HttpServletResponse resp) {

        return $do(s -> {

            LocalUser localUser = getUserFromPrincipal(principal);
            // Проверяем на заполненность пользовательских данных, чтобы не отваливались эксепшены:
            fixNullUserFields(localUser);
            return $prepareResponse(createGsonBuilder().toJson(localUser));
        }, null, null, OperationType.GET_CURRENT_USER, resp);
    }


    /**
     * Метод, который дергается, если пользователь забыл пароль.
     *
     * @param email
     * @param resp
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/users/forget", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> returnUserPassword(Principal principal, @RequestParam(name = "email") String email, HttpServletResponse resp) {

        return $do(s -> {
            log.info("========= FORGET PWD METHOD =============== ");
            log.info("USER EMAIL - " + email);
            try {
                LocalUser localUser = usersRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
                return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, email).getStatus()));
            } catch (UserNotFoundException e) {
                return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
            }
        }, null, null, null, resp);
    }

    /**
     * Сброс юзерского пароля админом.
     *
     * @param id
     * @param resp
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/users/reset/{id}")
    public ResponseEntity<String> resetUserPasswordByAdmin(Principal principal, @PathVariable String id, HttpServletResponse resp) {

        return $do(s -> {
		/*	LOGGER.info("========= RESET USER PWD =============== ");
			LOGGER.info("USER ID - " + id);*/
            try {
                LocalUser localUser = usersRepo.findById(Long.parseLong(id)).orElseThrow(UserNotFoundException::new);
                return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, localUser.getEmail()).getStatus()));
            } catch (UserNotFoundException e) {
                return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
            }
        }, null, null, null, resp);
    }


    /**
     * Смена pwd и отправка уведомления на почту.
     *
     * @param user
     * @param email
     * @return
     */
    private EmailStatus changePwd(LocalUser user, String email) {

//		LOGGER.info("USER FOUND - " + user.toString());

        String pwd = generateRandomPassword();
//		LOGGER.info("NEW PWD - " + pwd);
        user.setPwd(passwordEncoder.encode(pwd));
        LocalUser updatedUser = usersRepo.saveAndFlush(user);
//		LOGGER.info("UPDATED USER - " + updatedUser.toString());

        return emailSender.sendPlainText(email, "Ваши данные для доступа к arNote", "Ваш пароль - " + pwd + " [email - " + email + " ]");

    }

    /**
     * Вытаскиваем юзера из Принципала
     *
     * @param principal
     * @return
     */
    private LocalUser getUserFromPrincipal(Principal principal) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
    }
}
