package com.antonromanov.arnote.domain.wish.service;

import com.antonromanov.arnote.domain.wish.dto.rs.WishListRs;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.sex.model.wish.Wish;

import java.security.Principal;
import java.util.List;

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

	Optional<Wish> getWishById(int id); //todo: почему int ?????

	Integer getSumm4All(ArNoteUser user); // todo: переименовать

	Integer getSumm4Prior(ArNoteUser user); // todo: переименовать

	Optional<Integer> getImplementedSum(ArNoteUser user, int period);

	Salary saveSalary(Salary salary);

	Salary getLastSalary(ArNoteUser localUser);

	Integer calculateImplementationPeriod(Integer summ, ArNoteUser localUser);

	List<Wish> getAllWishesByUserId(ArNoteUser user);*/

	/**
	 * Поиск желаний по имени.
	 *
	 * @param name
	 * @param principal
	 * @return
	 */
	WishListRs findWishesByName(String name, Principal principal);

	/*Optional<List<Wish>> getAllRealizedWishes(ArNoteUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;
	Wish saveWish(Wish wish);*/

}
