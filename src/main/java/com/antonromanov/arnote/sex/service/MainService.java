package com.antonromanov.arnote.sex.service;


import com.antonromanov.arnote.domain.wish.entity.Wish;

import java.util.List;
import java.util.Optional;


public interface MainService {



	/**
	 * Получить все желания с наивысшим приоритетом.
	 *
	 * @param user - пользак, по которому ищем желания.
	 * @return - список желаний.
	 */
	/*List<Wish> getAllWishesWithPriority(LocalUser user);

	List<Wish> getAllWishes(LocalUser user, FilterMode filterMode, SortMode sortType);*/

	/**
	 * Получить желания согласно переданным настройкам сортировки/фильтрации и сохранить их.
	 * @param user - пользователь.
	 * @param filterMode
	 * @param sortType
	 * @return
	 */
	/*List<Wish> getAllWishesAndUpdateUser(LocalUser user, FilterMode filterMode, SortMode sortType) throws UserNotFoundException;

	int getMaxPriority(LocalUser user);*/

	/**
	 * Получить все желания с помесячной группировкой и детализованным наполнением.
	 *
	 */
	/*Optional<List<GroupOfWishesForOneMonth>> getAllWishesWithGroupPriority(LocalUser user, SortMode sortType);

	Wish updateWish(Wish log);

	Wish updateAndFlushWish(Wish log);

	Wish addWish(Wish wish);

	Optional<Wish> getWishById(long id);

	Integer getSumForAllWishes(LocalUser user);

	Integer getSumForPriorityWishes(LocalUser user);

	Optional<Integer> getImplementedSum(LocalUser user, int period);

	Salary saveSalary(Salary salary);

	Salary getLastSalary(LocalUser localUser);

	Integer calculateImplementationPeriod(Integer summ, LocalUser localUser);

	List<Wish> getAllWishesByUser(LocalUser user);*/

	Optional<List<Wish>> findWishesByName(Wish wish, com.antonromanov.arnote.sex.model.ArNoteUser user);

	/*List<Wish> getAllRealizedWishes(LocalUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;*/

}
