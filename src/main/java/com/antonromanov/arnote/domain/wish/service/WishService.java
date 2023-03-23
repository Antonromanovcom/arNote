package com.antonromanov.arnote.domain.wish.service;

import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rq.*;
import com.antonromanov.arnote.domain.wish.dto.rs.*;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;

import java.security.Principal;

public interface WishService {

    int getMaxPriority(ArNoteUser user);

    /**
     * Список желаний.
     *
     * @return
     */
    WishListRs getAllWishesByUserId(String filter, String sort);

    /**
     * Поиск желаний по имени.
     *
     * @param name
     * @return
     */
    WishListRs findWishesByName(String name);

    WishRs addWish(WishRq requestParam);

    WishRs updateWish(WishRq newWish);

    WishRs deleteWish(String id);

    WishAnalyticsRs getWishAnalytics();

    GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, String sortType);

    /**
     * Изменить у желание месяц в каком планируется его реализация.
     *
     * @param request
     * @return
     */
    WishRs transferWish(WishTransferRq request);

    WishRs oneStepChangePriority(ChangePriorityRq payload);

    /**
     * +/- 1 месяц к реализации желания.
     *
     * @param payload
     * @return
     */
    WishRs oneStepChangeTargetMonth(ChangeTargetMonthRq payload);
}
