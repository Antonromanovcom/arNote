package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Wish;

import java.sql.SQLException;
import java.util.List;


public interface MainService {


	List<Wish> getAllWishes(); // все логи

	void updateWish(Wish log); // обновить последний пинг (лог) новыми данными

	Wish addWish(Wish parseJsonToWish);
}
