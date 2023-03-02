package com.antonromanov.arnote.domain.user.service.impl;

import com.antonromanov.arnote.domain.investing.dto.common.InvestingFilterMode;
import com.antonromanov.arnote.domain.user.mapper.UserMapper;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import com.antonromanov.arnote.domain.wish.enums.FilterMode;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.old.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.old.exceptions.UserNotFoundException;
import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.old.model.ArNoteUser;
import com.antonromanov.arnote.domain.user.repository.UsersRepo;
import com.antonromanov.arnote.old.model.investing.InvestingSortMode;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
            settings.forEach((key, value) -> {

                if (StringUtils.isNotBlank(value)) {
                    switch (key) {
                        case FILTER:
                            user.setFilterMode(FilterMode.valueOf(value));
                            break;
                        case SORT:
                            user.setSortMode(SortMode.valueOf(value));
                            break;

                        case INVEST_FILTER:
                            if (InvestingFilterMode.valueOf(value) == InvestingFilterMode.NONE) {
                                user.setInvestingFilterMode(null);
                            }else {
                                if (user.getInvestingFilterMode() != null && user.getInvestingFilterMode().size() > 0) {
                                    user.getInvestingFilterMode().put(InvestingFilterMode.valueOf(value).getKey(), value);
                                } else {
                                    Map<String, String> filterMap = new HashMap<>();
                                    filterMap.put(InvestingFilterMode.valueOf(value).getKey(), value);
                                    user.setInvestingFilterMode(filterMap);
                                }
                            }
                            break;

                        case INVEST_SORT:
                                if (InvestingSortMode.valueOf(value) == InvestingSortMode.NONE) {
                                    user.setInvestingSortMode(null);
                                } else {
                                    user.setInvestingSortMode(InvestingSortMode.valueOf(value));
                                }
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
