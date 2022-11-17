package com.antonromanov.arnote.domain.wish.service;

import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rq.*;
import com.antonromanov.arnote.domain.wish.dto.rs.*;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import java.security.Principal;

public interface WishService {

    SalaryRs addSalary(SalaryRq request, Principal principal);

    int getMaxPriority(ArNoteUser user);

    /**
     * Список желаний.
     *
     * @param principal - пользак.
     * @return
     */
    WishListRs getAllWishesByUserId(Principal principal, String filter, String sort);

    /**
     * Поиск желаний по имени.
     *
     * @param name
     * @param principal
     * @return
     */
    WishListRs findWishesByName(String name, Principal principal);

    WishRs addWish(WishRq requestParam, Principal principal);

    WishRs updateWish(Principal principal, WishRq newWish);

	WishRs deleteWish(String id);

    WishAnalyticsRs getWishAnalytics(Principal principal);

    LocalUserRs getCurrentUser(Principal principal);

    LocalUserRs toggleUserMode(Principal principal, ToggleUserModeRq mode);

    LocalUserRs addUser(LocalUserRq user);

    GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, String sortType);

    WishRs transferWish(WishTransferRq request);

    WishRs oneStepChangePriority(ChangePriorityRq payload);

    WishRs oneStepChangeTargetMonth(ChangeTargetMonthRq payload);
}
