package com.antonromanov.arnote.service;

import com.antonromanov.arnote.dto.response.ResponseParseResult;
import com.antonromanov.arnote.dto.response.monthgroupping.GroupOfWishesForOneMonth;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.entity.Salary;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.enums.ListOfAllType;
import com.antonromanov.arnote.enums.SortMode;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
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

	List<Wish> getAllWishes(LocalUser user, ListOfAllType type);

	int getMaxPriority(LocalUser user);

	/**
	 * Получить все желания с помесячной группировкой и детализованным наполнением.
	 *
	 */
	Optional<List<GroupOfWishesForOneMonth>> getAllWishesWithGroupPriority(LocalUser user, SortMode sortType);

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

	List<Wish> getAllWishesByUser(LocalUser user);

	Optional<List<Wish>> findAllWishesByWish(Wish wish, LocalUser user);

	List<Wish> getAllRealizedWishes(LocalUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;

}
