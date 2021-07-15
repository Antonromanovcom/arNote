package com.antonromanov.arnote.domain.wish.service;

import com.antonromanov.arnote.domain.user.dto.ArNoteUser;
import com.antonromanov.arnote.domain.wish.dto.*;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;


public interface MainService {

	ResponseParseResult parseCsv(MultipartFile file, ArNoteUser localUser) throws Exception;

	List<Wish> getAllWishesWithPriority1(ArNoteUser user);

	int getMaxPriority(ArNoteUser user);

	/**
	 * Получить все желания с помесячной группировкой и детализованным наполнением.
	 *
	 */
	List<WishDTOList> getAllWishesWithGroupPriority(ArNoteUser user);

	void updateWish(Wish log);

	Wish updateAndFlushWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Optional<Wish> getWishById(int id);

	Integer getSumm4All(ArNoteUser user);

	Integer getSumm4Prior(ArNoteUser user);

	Optional<Integer> getImplementedSum(ArNoteUser user, int period);

	Salary saveSalary(Salary salary);

	Salary getLastSalary(ArNoteUser localUser);

	Integer calculateImplementationPeriod(Integer summ, ArNoteUser localUser);

	List<Wish> getAllWishesByUserId(ArNoteUser user);

	/**
	 * Поиск желаний по имени.
	 *
	 * @param request
	 * @param user
	 * @return
	 */
	List<Wish> findAllWishesByWishName(SearchRq request, ArNoteUser user);

	Optional<List<Wish>> getAllRealizedWishes(ArNoteUser user);

	Wish updateMonthGroup(Wish wish) throws BadIncomeParameter;

}
