package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Wish;

import java.text.ParseException;
import java.sql.Time;
import java.util.List;


public interface MainService {


	List<Wish> getAllLogs(); // все логи

	Time getLastContactTime(); // время последнего контакта

	Boolean getLastContact220();  // AC статус последнего контакта

	Boolean getLastContactLan();  // LAN статус последнего контакта

	Boolean getLastContactLogged();  // Пинг залогирован или нет?

	Wish getLastLog(); // получить последний пинг (лог)

	void updateLastLog(Wish log); // обновить последний пинг (лог) новыми данными



}
