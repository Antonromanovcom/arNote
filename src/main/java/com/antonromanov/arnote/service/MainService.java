package com.antonromanov.arnote.service;

import com.antonromanov.arnote.dto.response.ResponseParseResult;
import com.antonromanov.arnote.dto.response.WishList;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.entity.Salary;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;


public interface MainService {

	ResponseParseResult parseCsv(MultipartFile file, LocalUser localUser) throws Exception;

	List<Wish> getAllWishesWithPriority1(LocalUser user);

	int getMaxPriority(LocalUser user);

	/**
	 * Получить все желания с помесячной группировкой и детализованным наполнением.
	 *
	 */
	List<WishList> getAllWishesWithGroupPriority(LocalUser user);

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

	Optional<List<Wish>> findAllWishesByWish(String Wish, LocalUser user);

	Optional<List<Wish>> getAllRealizedWishes(LocalUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;

}
