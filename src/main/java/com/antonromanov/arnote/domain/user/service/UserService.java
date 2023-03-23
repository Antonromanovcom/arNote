package com.antonromanov.arnote.domain.user.service;

import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.common.exceptions.UserNotFoundException;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import java.util.Map;
import java.util.Optional;

public interface UserService {


    LocalUserRs getCurrentUser();

    LocalUserRs toggleUserMode(ToggleUserModeRq mode);

    LocalUserRs addUser(LocalUserRq user);

    /**
     * Вытаскиваем юзера из Принципала.
     *
     * @return
     */
    ArNoteUser getUserFromPrincipal() throws UserNotFoundException;

    /**
     * Сохраняет пользака в БД.
     *
     * @param user
     * @return
     */
    ArNoteUser saveUser(ArNoteUser user);

    ArNoteUser checkAndSaveUserSettings(ArNoteUser user, Map<UserSettingType, String> settings);

    Optional<ArNoteUser> findByLogin(String login);

}
