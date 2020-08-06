package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.request.UserDto;
import com.antonromanov.arnote.email.EmailSender;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.antonromanov.arnote.utils.Utils.createGsonBuilder;

/**
 * REST-контроллер управления пользователями.
 */
@CrossOrigin()
@RestController
@RequestMapping("/user")
@Slf4j
@AllArgsConstructor
@Data
public class UserController {

    private final MainService mainService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;
    private final EmailSender emailSender;
    private final Utils utils;

    /**
     * Добавление нового пользователя.
     *
     * @param newUser - Объект типа UserDto, содержащий данные нового пользователя.
     * @return - объект типа LocalUser
     * @throws Exception
     */
    @CrossOrigin(origins = "*")
    @PostMapping
    public LocalUser addUser(@RequestBody UserDto newUser) throws Exception {

        log.info("========= ADD USER  ============== ");
        log.info("PAYLOAD: " + createGsonBuilder().toJson(newUser));

        if (usersRepo.findByLogin(newUser.getLogin()).isPresent()) {
            log.error("Пользователь с логином {} уже есть!", newUser.getLogin());
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
        }
        return usersRepo.saveAndFlush(new LocalUser(newUser, passwordEncoder.encode(newUser.getUnSecurePassword())));
    }


    /**
     * Удалить пользователя.
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping
    public List<LocalUser> deleteUser(@RequestParam UserDto userForEdit) throws BadIncomeParameter {

        log.info("========= DELETE USER  ============== ");
        log.info("PAYLOAD: " + userForEdit);

        return usersRepo.findById(userForEdit.getId())
                .map(localUser -> {
                    usersRepo.delete(localUser);
                    return usersRepo.findAll();
                })
                .orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST));
    }

    /**
     * Редактировать пользователя.
     *
     * @param user объект типа UserDto с обновленными данными пользователя.
     * @return - объект типа LocalUser.
     * @throws UserNotFoundException
     * @throws BadIncomeParameter - если пользователь не найден (id передается в UserDto)
     */
    @CrossOrigin(origins = "*")
    @PutMapping
    public LocalUser editUser(@RequestBody UserDto user) throws BadIncomeParameter {

        log.info("========= EDIT USER  ============== ");
        log.info("PAYLOAD: " + createGsonBuilder().toJson(user));

        return usersRepo.findByLogin(user.getLogin())
                .map(u -> {
                    u.setPwd(passwordEncoder.encode(user.getUnSecurePassword()));
                    u.setLogin(user.getLogin());
                    u.setEmail(user.getEmail());
                    u.setFullname(user.getFullName());
                    u.setUserCryptoMode(user.getUserCryptoMode());
                    return usersRepo.saveAndFlush(u);
                }).orElseThrow(()->new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST));
    }

      /*

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


    *//**
     * Метод, который дергается, если пользователь забыл пароль.
     *
     * @param email
     * @param resp
     * @return
     *//*
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

    *//**
     * Сброс юзерского пароля админом.
     *
     * @param id
     * @param resp
     * @return
     *//*
    @CrossOrigin(origins = "*")
    @GetMapping("/users/reset/{id}")
    public ResponseEntity<String> resetUserPasswordByAdmin(Principal principal, @PathVariable String id, HttpServletResponse resp) {

        return $do(s -> {
		*//*	LOGGER.info("========= RESET USER PWD =============== ");
			LOGGER.info("USER ID - " + id);*//*
            try {
                LocalUser localUser = usersRepo.findById(Long.parseLong(id)).orElseThrow(UserNotFoundException::new);
                return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, localUser.getEmail()).getStatus()));
            } catch (UserNotFoundException e) {
                return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
            }
        }, null, null, null, resp);
    }


    *//**
     * Смена pwd и отправка уведомления на почту.
     *
     * @param user
     * @param email
     * @return
     *//*
    private EmailStatus changePwd(LocalUser user, String email) {

//		LOGGER.info("USER FOUND - " + user.toString());

        String pwd = generateRandomPassword();
//		LOGGER.info("NEW PWD - " + pwd);
        user.setPwd(passwordEncoder.encode(pwd));
        LocalUser updatedUser = usersRepo.saveAndFlush(user);
//		LOGGER.info("UPDATED USER - " + updatedUser.toString());

        return emailSender.sendPlainText(email, "Ваши данные для доступа к arNote", "Ваш пароль - " + pwd + " [email - " + email + " ]");

    }*/
}
