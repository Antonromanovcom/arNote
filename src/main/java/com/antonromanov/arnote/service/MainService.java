package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Wish;

import java.text.ParseException;
import java.sql.Time;
import java.util.List;


public interface MainService {


	List<Wish> getAllWishes(); // все логи

	void updateLastLog(Wish log); // обновить последний пинг (лог) новыми данными



}
