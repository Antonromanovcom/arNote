package com.antonromanov.arnote.domain.user.service.impl;

import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.enums.FilterMode;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.sex.repositoty.UsersRepo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

/**
 * Тут собраны методы необходимые для работы с пользователями и авторизацией.
 */
@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Autowired
    private final UsersRepo usersRepo;

    /*@Autowired
    BCryptPasswordEncoder passwordEncoder;*/


    @Override
    public ArNoteUser getUserFromPrincipal(Principal principal) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ArNoteUser saveUser(ArNoteUser user) {
        try {
            return usersRepo.saveAndFlush(user);
        } catch (Exception e) {
            return user;
        }
    }

    @Override
    public ArNoteUser checkAndSaveUserSettings(ArNoteUser user, Map<UserSettingType, String> settings) {

        if (settings != null && settings.size()>0) {
            settings.entrySet().forEach(e -> {

                if (StringUtils.isNotBlank(e.getValue())) {
                    switch (e.getKey()) {
                        case FILTER:
                            user.setFilterMode(FilterMode.valueOf(e.getValue()));
                            break;
                        case SORT:
                            user.setSortMode(SortMode.valueOf(e.getValue()));
                            break;
                    }
                }
            });
        }

        return saveUser(user);
    }

    @Override
    public Optional<ArNoteUser> findByLogin(String login) {
        return usersRepo.findByLogin(login);
    }

   /* @Override
    public com.antonromanov.arnote.sbdfvjbsdf.model.ArNoteUser getAndUpdateUserTreeViewSettings(Principal principal, SortMode sortType) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).map(u -> {
            u.setTreeSortMode(sortType);
            return usersRepo.saveAndFlush(u);
        }).orElseThrow(UserNotFoundException::new);
    }*/
}
