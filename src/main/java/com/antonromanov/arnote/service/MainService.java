package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Wish;

import java.sql.SQLException;
import java.util.List;


public interface MainService {


	List<Wish> getAllWishes();

	void updateWish(Wish log);

	Wish addWish(Wish parseJsonToWish);
}
