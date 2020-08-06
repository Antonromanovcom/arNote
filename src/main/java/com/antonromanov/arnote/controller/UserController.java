package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.dto.request.UserDto;
import com.antonromanov.arnote.email.EmailStatus;
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
     * @throws BadIncomeParameter    - если пользователь не найден (id передается в UserDto)
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
                }).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST));
    }

    /**
     * Получить текущего пользователя.
     *
     * @param principal - берется из JWT сессии.
     * @return
     * @throws UserNotFoundException
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/current")
    public LocalUser getCurrentUser(Principal principal) throws UserNotFoundException {
                return utils.getUserFromPrincipal(principal); //todo: в теории мы наверное можем узнать сколько осталось жить токену и завернуть это в ДТО Юзера
    }

    /**
     * Установить пользователю режим отображения.
     *
     * @param principal - берется из JWT сессии.
     * @param mode - режим отображения.
     * @return
     * @throws UserNotFoundException
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/mode")
    public LocalUser toggleUserMode(Principal principal, @RequestParam String mode) throws UserNotFoundException {

            log.info("========= TOGGLE / GET USER MODE ============== ");
            log.info("MODE: " + mode);

            LocalUser localuser = utils.getUserFromPrincipal(principal);

            if (("TABLE".equals(mode)) || ("TREE".equals(mode))) { //todo: в ЕНУМ !!!!
                localuser.setViewMode(mode);
                return usersRepo.saveAndFlush(localuser);
            } else {
                //todo: тут надо бросануть ЭКСЕПШН и завернуть его уже в ЕррорХендлере
                return null;
            }
    }

    /**
     * Вернуть всех пользователей.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public List<LocalUser> getAllUsers() {
            log.info("========= GET ALL USERS  ============== ");
            return usersRepo.findAll();
    }


    /**
     * Метод, который дергается, если пользователь забыл пароль.
     *
     * @param email - email пользователя, по которому ищем в Базе пользователей.
     * @return - объект типа EmailStatus с результатами отправки нового пароля.
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/forget")
    public EmailStatus returnUserPassword(@RequestParam(name = "email") String email) throws UserNotFoundException {

        log.info("========= FORGET PWD METHOD =============== ");
        log.info("USER EMAIL - " + email);

        return usersRepo.findByEmail(email)
                .map(u -> utils.changePwd(u, email))
                .orElseThrow(UserNotFoundException::new);
    }


    /**
     * Сброс юзерского пароля админом.
     *
     * @param userId - id пользователя из БД.
     * @return - объект типа EmailStatus с результатами отправки нового пароля.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/reset")
    public EmailStatus resetUserPasswordByAdmin(@RequestParam(name = "userid") String userId) throws UserNotFoundException {

        return usersRepo.findById(Long.parseLong(userId))
                .map(user -> utils.changePwd(user, user.getEmail()))
                .orElseThrow(UserNotFoundException::new);

    }
}
