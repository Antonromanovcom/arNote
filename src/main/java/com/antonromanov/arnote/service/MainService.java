package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.ResponseParseResult;
import com.antonromanov.arnote.model.Salary;
import com.antonromanov.arnote.model.Wish;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MainService {

	ResponseParseResult parseCsv(MultipartFile file) throws Exception;

	void clearCounter();

	List<Wish> getAllWishes();

	List<Wish> getAllWishesWithPriority1();

	void updateWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Integer getSumm4All();

	Integer getSumm4Prior();

	void deleteWish(String id);

	Salary saveSalary(Salary salary);

	Salary getLastSalary();


	Integer calculateImplementationPeriod(Integer summ);
}
