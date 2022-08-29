package com.antonromanov.arnote.domain.wish.api;

import com.antonromanov.arnote.EnableResponseWrapper;
import com.antonromanov.arnote.GlobalResponse;
import com.antonromanov.arnote.ResponseStatus;
import com.antonromanov.arnote.Wrapper;
import com.antonromanov.arnote.domain.wish.dto.rq.SearchWishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.WishListRs;
import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


//todo: надо нормально поименовать ендпоинты
//todo: заменить логи на интерцепторы
//todo: надо сделать проверку на уникальность добавляемых желаний
//todo: прописать JavaDoc везде
//todo: надо уходить от json-билдеров и всего-такого
//todo: избавиться от $do
//todo: добавить тесты
// todo: Надо убрать РеспонсЭнтити и возвращать налормальные ДТО-объекты а не этот пиздец


/**
 * REST-контроллер для Желаний.
 */
@CrossOrigin()
@RestController
@RequestMapping("/rest/wishes") //todo: поменять все урлы на нормальные
@Slf4j
@AllArgsConstructor
@EnableResponseWrapper(wrapperClass = Wrapper.class)
public class WishController {

    private final WishService wishService;

    /**
     * Поиск желаний.
     *
     * @param principal
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/filter")
    // todo: почему фильтр-то? Это постоянно вводит в заблуждение. Это фильтр все-таки или поиск???
    public WishListRs findWishes(Principal principal, @RequestBody SearchWishRq request) throws UserNotFoundException {


      //  GlobalResponse<String> s = new GlobalResponse<>();
       // s.setBody("1");

     //   return wishService.findWishesByName(request.getWishName(), principal);
        throw new UserNotFoundException();

       /* return GlobalResponse.builder()
               // .body(wishService.findWishesByName(request.getWishName(), principal))
               // .body(WishListRs.builder().build())
                .body(WishListRs.builder().build())
               // .withBody(WishListRs.builder().build())
              //  .body("WishListRs.builder().build()")
              //  .status(ResponseStatus.builder().build())
                .build();*/
     //  return s;
    }


    /**
     * Получить все желания с группировкой по месяцам.
     *
     * @param principal
     * @param sortType
     * @param resp
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @GetMapping("/groups") // todo: переименовать
    public ResponseEntity<String> getAllWishesWithMonthGrouping(Principal principal,
                                                                @RequestParam String sortType,
                                                                HttpServletResponse resp) {

        return $do(s -> {
            List<WishDTOList> wishListWithMonthOrder;

            log.info("============== GET ALL WISHES WITH MONTH GROUPING ============== ");
            log.info("SORT TYPE: " + sortType);
            log.info("PRINCIPAL: " + principal.getName());

            ArNoteUser localUser = getUserFromPrincipal(principal);

            if (mainService.getAllWishesByUserId(localUser).size() > 0) {

                DtoWithOrder dtOwithOrder = new DtoWithOrder(); //todo: билдер
                String result = "";
                String finalSortType = sortType; //todo: очень не красивое решение - надо что-то с этим делать.
                wishListWithMonthOrder = mainService.getAllWishesWithGroupPriority(localUser);

                dtOwithOrder.list.addAll(wishListWithMonthOrder); //todo: почему .list, а не getlist() ????

                if (("all".equalsIgnoreCase(finalSortType))
                        && (localUser.getSortMode() != SortMode.ALL)
                        && (localUser.getSortMode() != null)) { //todo: проверяем не сохранен ли до этого режим отображения и если сохранен - выбираем его. Но  вообще это костылище и код не красивый - надо разбираться с этим
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
                        localUser.getViewMode() == null ? "N/A" : localUser.getViewMode(),
                        localUser.getSortMode() == null ? "N/A" : localUser.getSortMode().getUiValue());
                result = createNullableGsonBuilder().toJson(dtOwithOrder);

                return $prepareResponse(result);
            } else {
                return $prepareNoDataYetErrorResponse(ErrorCodes.ERR_O1);
            }
        }, null, null, null, resp);
    }*/

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

    /**
     * Получить все желания.
     *
     * @param principal
     * @param filter    - тип фильтраци: все или только приоритетные
     * @param sort      - собственно сортировка
     * @param resp
     * @return
     */
   /* @CrossOrigin(origins = "*")
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

            ArNoteUser localUser = getUserFromPrincipal(principal);

            *//*
     * Логика такая:
     *
     * - если фильтр приходит не пустой - задаем и сохраняем новый фильтр.
     * - если фильтр приходит не пустой, но он NONE, просто удаляем сохраненный фильтр из записи пользака.
     * - если filter пришел пустой - выдаем то, что есть с той фильтрацией, что сохранена.
     *
     *//*
            if (filter != null) {
                localUser.setFilterMode(FilterMode.valueOf(filter));
                localUser = usersRepo.saveAndFlush(localUser);
            }
            if (localUser.getFilterMode() == null) {
                localUser.setFilterMode(FilterMode.NONE);
                localUser = usersRepo.saveAndFlush(localUser);
            }

            if (sort != null) {
                localUser.setSortMode(SortMode.valueOf(sort));
                localUser = usersRepo.saveAndFlush(localUser);
            }

            if (localUser.getSortMode() == null) {
                localUser.setSortMode(SortMode.ALL);
                localUser = usersRepo.saveAndFlush(localUser);
            }

            if (mainService.getAllWishesByUserId(localUser).size() > 0) {

                DTO dto = new DTO();
                String result = "";
                wishList = mainService.getAllWishesByUserId(localUser).stream()
                        .filter(localUser.getFilterMode().getFilterPredicate())
                        .sorted(localUser.getSortMode().getCompareInstrument())
                        .collect(Collectors.toList());

                // Предотвращение вываливания на пустых датах
                wishList.forEach(w -> {
                    if (w.getCreationDate() == null) w.setCreationDate(new Date());
                    if (w.getRealized() == null) w.setRealized(false);
                });

                dto.list.addAll(wishList);
                result = createNullableGsonBuilder().toJson(dto);


                return $prepareResponse(result);
            } else {
                return $prepareNoDataYetErrorResponse(ErrorCodes.ERR_O1);
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

            ArNoteUser localUser = getUserFromPrincipal(principal);
            Wish wish = parseJsonToWish(ParseType.EDIT, requestParam, localUser);
            mainService.updateWish(mainService.updateMonthGroup(wish));

            String result = "";
            return $prepareResponse(result);

        }, requestParam, null, OperationType.EDIT_WISH, resp);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Wish addWish(Principal principal, @RequestBody String requestParam) throws Exception {

        log.info("==================== ADD WISHES ======================== ");
        log.info("PAYLOAD: {}", requestParam);
        log.info("PRINCIPAL: {}", principal.getName());
        log.info("======================================================== ");
        return mainService.addWish(parseJsonToWish(ParseType.ADD, requestParam, getUserFromPrincipal(principal)));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/summ")
    public ResponseEntity<String> getSum(Principal principal, HttpServletResponse resp) {

        return $do(s -> {
            int days = 0;
            int implementedSumAllTime = 0;
            int implementedSumMonth = 0;

            ArNoteUser localUser = getUserFromPrincipal(principal);

            if (mainService.getAllRealizedWishes(localUser).isPresent()) {

                List<Long> realizedWishes = mainService.getAllRealizedWishes(localUser).get().stream()
                        .filter(wf -> wf.getRealizationDate() != null && wf.getCreationDate() != null)
                        .map(w -> (w.getRealizationDate().getTime() - w.getCreationDate().getTime())).collect(Collectors.toList());

                days = (realizedWishes.size() == 0) ? 0 : (30 / realizedWishes.size());
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
                return $prepareNoDataYetErrorResponse(ErrorCodes.ERR_O2);
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

            ArNoteUser localUser = getUserFromPrincipal(principal);
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
    public Salary addSalary(Principal principal, @RequestBody String requestParam) throws Exception {

        log.info("==================== ADD SALARY ======================== ");
        log.info("PAYLOAD: " + requestParam);
        log.info("PRINCIPAL: " + principal.getName());
        ArNoteUser localUser = getUserFromPrincipal(principal);
        return mainService.saveSalary(parseJsonToSalary(requestParam, localUser));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/parsecsv")
    public ResponseEntity<String> parseCsv(Principal principal,
                                           @RequestParam(required = false, value = "csvfile") MultipartFile csvFile,
                                           HttpServletResponse resp) {

        return $do(s -> {
            ArNoteUser localUser = getUserFromPrincipal(principal);
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
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody String user, HttpServletResponse resp) {


        return $do(s -> {

            ArNoteUser newUser = parseJsonToUserAndValidate(user);
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
            ArNoteUser newUser = parseJsonToUserAndValidate(user);
            ArNoteUser localuser = getUserFromPrincipal(principal);
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
    public ArNoteUser toggleUserMode(Principal principal, @PathVariable String mode) throws UserNotFoundException {

        log.info("========= TOGGLE / GET USER MODE ============== ");
        log.info("MODE: " + mode);

        ArNoteUser localuser = getUserFromPrincipal(principal);

        if (("TABLE".equals(mode)) || ("TREE".equals(mode))) {
            localuser.setViewMode(mode);
            return usersRepo.saveAndFlush(localuser);
        } else {
            return localuser;
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/list")
    public ResponseEntity<String> getAllUsers(Principal principal, HttpServletResponse resp) {

        return $do(s -> {
            log.info("========= GET ALL USERS  ============== ");

            List<ArNoteUser> userList = usersRepo.findAll().stream().map(u -> {
                if (u.getCreationDate() == null) u.setCreationDate(new Date());
                return u;
            }).collect(Collectors.toList());

            return $prepareResponse(createGsonBuilder().toJson(userList));
        }, null, null, null, resp);
    }

    private void fixNullUserFields(ArNoteUser localUser) {
        // Проверяем на заполненность пользовательских данных, чтобы не отваливались эксепшены:
        if (localUser.getUserRole() == null) localUser.setUserRole(ArNoteUser.Role.USER);
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

            ArNoteUser localUser = getUserFromPrincipal(principal);
            // Проверяем на заполненность пользовательских данных, чтобы не отваливались эксепшены:
            fixNullUserFields(localUser);
            return $prepareResponse(createGsonBuilder().toJson(localUser));
        }, null, null, OperationType.GET_CURRENT_USER, resp);
    }*/


    /**
     * Метод, который дергается, если пользователь забыл пароль.
     *
     * @param email
     * @param resp
     * @return
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping(value = "/users/forget", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> returnUserPassword(Principal principal, @RequestParam(name = "email") String email, HttpServletResponse resp) {

        return $do(s -> {
            log.info("========= FORGET PWD METHOD =============== ");
            log.info("USER EMAIL - " + email);
            try {
                ArNoteUser localUser = usersRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
                return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, email).getStatus()));
            } catch (UserNotFoundException e) {
                return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
            }
        }, null, null, null, resp);
    }*/

    /**
     * Сброс юзерского пароля админом.
     *
     * @param id
     * @param resp
     * @return
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/users/reset/{id}")
    public ResponseEntity<String> resetUserPasswordByAdmin(Principal principal, @PathVariable String id, HttpServletResponse resp) {

        return $do(s -> {
            try {
                ArNoteUser localUser = usersRepo.findById(Long.parseLong(id)).orElseThrow(UserNotFoundException::new);
                return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, localUser.getEmail()).getStatus()));
            } catch (UserNotFoundException e) {
                return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
            }
        }, null, null, null, resp);
    }*/


    /**
     * Смена pwd и отправка уведомления на почту.
     *
     * @param user
     * @param email
     * @return
     */
  /*  private EmailStatus changePwd(ArNoteUser user, String email) {

        String pwd = generateRandomPassword();
        user.setPwd(passwordEncoder.encode(pwd));
        ArNoteUser updatedUser = usersRepo.saveAndFlush(user);
        return emailSender.sendPlainText(email, "Ваши данные для доступа к arNote", "Ваш пароль - " + pwd +
                " [email - " + email + " ]");
    }*/


}
