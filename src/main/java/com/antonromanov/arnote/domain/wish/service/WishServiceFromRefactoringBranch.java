package com.antonromanov.arnote.domain.wish.service;

import com.antonromanov.arnote.domain.wish.dto.rs.ListOfGroupedWishesRs;
import com.antonromanov.arnote.sex.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.sex.model.wish.Wish;
import java.util.List;
import java.util.Optional;


public interface WishServiceFromRefactoringBranch {

	List<Wish> getAllWishesWithPriority1(ArNoteUser user);

	int getMaxPriority(ArNoteUser user);

	/**
	 * Получить все желания с помесячной группировкой и детализованным наполнением.
	 *
	 */
	List<ListOfGroupedWishesRs> getAllWishesWithGroupPriority(ArNoteUser user);

	void updateWish(Wish log);

	Wish updateAndFlushWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Optional<Wish> getWishById(int id);

	Integer getSumm4All(ArNoteUser user);

	Integer getSumm4Prior(ArNoteUser user);

	Optional<Integer> getImplementedSum(ArNoteUser user, int period);

	/*Salary saveSalary(Salary salary);

	Salary getLastSalary(ArNoteUser localUser);*/

	Integer calculateImplementationPeriod(Integer summ, ArNoteUser localUser);

	List<Wish> getAllWishesByUserId(ArNoteUser user);

	/**
	 * Поиск желаний по имени.
	 *
	 * @param wishName - желание
	 * @param user
	 * @return
	 */
	List<Wish> findAllWishesByWishName(String wishName, ArNoteUser user);

	Optional<List<Wish>> getAllRealizedWishes(ArNoteUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;

}
