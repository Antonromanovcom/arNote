package com.antonromanov.arnote.service;


import com.antonromanov.arnote.exceptions.NoDataYetException;
import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.ResponseParseResult;
import com.antonromanov.arnote.model.Salary;
import com.antonromanov.arnote.model.Wish;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;


public interface MainService {

	ResponseParseResult parseCsv(MultipartFile file, LocalUser localUser) throws Exception;

	void clearCounter();

	List<Wish> getAllWishes();

	List<Wish> getAllWishesWithPriority1(LocalUser user);

	void updateWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Optional<Wish> getWishById(int id);

	Integer getSumm4All(LocalUser user);

	Integer getSumm4Prior(LocalUser user);

	void deleteWish(String id);

	Salary saveSalary(Salary salary);

	Salary getLastSalary(LocalUser localUser);

	Integer calculateImplementationPeriod(Integer summ, LocalUser localUser);

	List<Wish> getAllWishesByUserId(LocalUser user);

	Optional<List<Wish>> findAllWishesByWish(String Wish, LocalUser user);

}
