package com.antonromanov.arnote.domain.user.service;

import java.security.Principal;

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
    com.antonromanov.arnote.sex.model.ArNoteUser getUserFromPrincipal(Principal principal) throws
            com.antonromanov.arnote.exceptions.UserNotFoundException;


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
