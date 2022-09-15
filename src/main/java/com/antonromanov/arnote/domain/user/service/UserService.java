package com.antonromanov.arnote.domain.user.service;

import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import java.security.Principal;
import java.util.Map;

/**
 * Методы, необходимые для работы с пользователями и авторизацией.
 */
public interface UserService {

    /**
     * Вытаскиваем юзера из Принципала.
     *
     * @param principal
     * @return
     */
    ArNoteUser getUserFromPrincipal(Principal principal) throws UserNotFoundException;

    /**
     * Сохраняет пользака в БД.
     *
     * @param user
     * @return
     */
    ArNoteUser saveUser(ArNoteUser user);

    ArNoteUser checkAndSaveUserSettings(ArNoteUser user, Map<UserSettingType, String> settings);
    ArNoteUser getFirst();




    /**
     * Вытаскиваем юзера из Принципала и обновляем его view-параметры для tree-вида.
     *
     * @param principal
     * @return
     */ //todo: нарушение singleresponsability: GET и UPDATE - это должны быть 2 разных метода.
    /*com.antonromanov.arnote.sbdfvjbsdf.model.ArNoteUser getAndUpdateUserTreeViewSettings(Principal principal,
                                                                              com.antonromanov.arnote.enums.SortMode sortType)
            throws com.antonromanov.arnote.exceptions.UserNotFoundException;*/
}
