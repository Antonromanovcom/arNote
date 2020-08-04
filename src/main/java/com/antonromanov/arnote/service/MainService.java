package com.antonromanov.arnote.service;

import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.model.*;
import com.antonromanov.arnote.utils.Utils;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;


public interface MainService {

	ResponseParseResult parseCsv(MultipartFile file, LocalUser localUser) throws Exception;

	/**
	 * Получить все желания с наивысшим приоритетом.
	 *
	 * @param user - пользак, по которому ищем желания.
	 * @return - список желаний.
	 */
	List<Wish> getAllWishesWithPriority(LocalUser user);

	int getMaxPriority(LocalUser user);

	/**
	 * Получить все желания с помесячной группировкой и детализованным наполнением.
	 *
	 */
	List<WishDTOList> getAllWishesWithGroupPriority(LocalUser user);

	void updateWish(Wish log);

	Wish updateAndFlushWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Optional<Wish> getWishById(int id);

	Integer getSumm4All(LocalUser user);

	Integer getSumm4Prior(LocalUser user);

	Optional<Integer> getImplementedSum(LocalUser user, int period);

	Salary saveSalary(Salary salary);

	Salary getLastSalary(LocalUser localUser);

	Integer calculateImplementationPeriod(Integer summ, LocalUser localUser);

	List<Wish> getAllWishesByUserId(LocalUser user);

	Optional<List<Wish>> findAllWishesByWish(String wishName, LocalUser user);

	/*Optional<List<Wish>> findAllWishesByWishName(String wishName, LocalUser user);*/

	Optional<List<Wish>> getAllRealizedWishes(LocalUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;

}
