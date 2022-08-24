package com.antonromanov.arnote.domain.user.service.impl;

import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import com.antonromanov.arnote.sex.repositoty.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;

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
    public com.antonromanov.arnote.sex.model.ArNoteUser getUserFromPrincipal(Principal principal) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
    }

   /* @Override
    public com.antonromanov.arnote.sbdfvjbsdf.model.ArNoteUser getAndUpdateUserTreeViewSettings(Principal principal, SortMode sortType) throws UserNotFoundException {
        return usersRepo.findByLogin(principal.getName()).map(u -> {
            u.setTreeSortMode(sortType);
            return usersRepo.saveAndFlush(u);
        }).orElseThrow(UserNotFoundException::new);
    }*/
}
