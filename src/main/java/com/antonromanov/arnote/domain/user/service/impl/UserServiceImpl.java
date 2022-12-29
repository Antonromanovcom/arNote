package com.antonromanov.arnote.domain.user.service.impl;

import com.antonromanov.arnote.domain.user.mapper.UserMapper;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import com.antonromanov.arnote.domain.wish.enums.FilterMode;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.sex.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import com.antonromanov.arnote.sex.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.domain.user.repository.UsersRepo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

/**
 * Тут собраны методы необходимые для работы с пользователями и авторизацией.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UsersRepo usersRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LocalUserRs getCurrentUser() {
        return mapper.mapArnoteUser(getUserFromPrincipal());
    }

    @Override
    public LocalUserRs toggleUserMode(ToggleUserModeRq mode) {
        ArNoteUser user = getUserFromPrincipal();
        user.setViewMode(mode.getUserViewMode().name());
        return mapper.mapArnoteUser(saveUser(user));
    }

    @Override
    public LocalUserRs addUser(LocalUserRq request) {
        if (findByLogin(request.getLogin()).isPresent()) {
            throw new BadIncomeParameter(ErrorCodes.ERR_11);
        } else {
            ArNoteUser newUser = mapper.mapLocalUserRq(request, passwordEncoder.encode(request.getPwd()));
            return mapper.mapArnoteUser(saveUser(newUser));
        }
    }

    @Override
    public ArNoteUser getUserFromPrincipal() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByLogin(principal.toString()).orElseThrow(UserNotFoundException::new);
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

        if (settings != null && settings.size() > 0) {
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
}
