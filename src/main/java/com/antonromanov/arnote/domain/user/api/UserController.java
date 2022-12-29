package com.antonromanov.arnote.domain.user.api;

import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * REST-контроллер для работы с сущностью Пользователь.
 */
@CrossOrigin()
@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public LocalUserRs addUser(@RequestBody LocalUserRq user) {
        return userService.addUser(user);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/toggle")
    public LocalUserRs toggleUserMode(@RequestBody ToggleUserModeRq mode) {
        return userService.toggleUserMode(mode);

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/current")
    public LocalUserRs getCurrentUser() {
        return userService.getCurrentUser();
    }
}
