package com.antonromanov.arnote.domain.wish.service;

import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.*;

import java.security.Principal;

public interface WishService {

	/*List<Wish> getAllWishesWithPriority1(ArNoteUser user); // todo: проверить - если реально нигде не испльзуется - то убрать
	List<Wish> getAl();

	int getMaxPriority(ArNoteUser user);

	*//**
     * Получить все желания с помесячной группировкой и детализованным наполнением.
     *
     *//*
	List<WishDTOList> getAllWishesWithGroupPriority(ArNoteUser user);

	void updateWish(Wish log);

	Wish updateAndFlushWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Integer getSumm4Prior(ArNoteUser user); // todo: переименовать

	Optional<Integer> getImplementedSum(ArNoteUser user, int period);

	 */

    SalaryRs addSalary(SalaryRq request, Principal principal);

//	Salary getLastSalary(ArNoteUser localUser);


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

	/*Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;
	Wish saveWish(Wish wish);*/

}
