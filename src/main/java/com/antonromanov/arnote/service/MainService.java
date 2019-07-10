package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Salary;
import com.antonromanov.arnote.model.Wish;
import java.util.List;


public interface MainService {


	List<Wish> getAllWishes();

	List<Wish> getAllWishesWithPriority1();

	void updateWish(Wish log);

	Wish addWish(Wish parseJsonToWish);

	Integer getSumm4All();

	Integer getSumm4Prior();

	void deleteWish(String id);

	void saveSalary(Salary salary);

	Salary getLastSalary();


	Integer calculateImplementationPeriod(Integer summ);
}
