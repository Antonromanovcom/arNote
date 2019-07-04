package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.DailyReport;
import com.antonromanov.arnote.model.Logs;
import com.antonromanov.arnote.model.Temperature;
import java.text.ParseException;
import java.sql.Time;
import java.util.List;


public interface MainService {


	List<Temperature> getAll(); // список всех измерений (пока с одного датчика)

	List<Logs> getAllLogs(); // все логи

	List<Temperature> addMeasure(Double temp, String status); // добавить измерение

	List<Temperature> getTodayMeasures() throws ParseException; //статистика по сегодня

	List<DailyReport> getWeeklyDayReport() throws ParseException; // недельная статистика

	List<DailyReport> getMonthDayReport() throws ParseException; // статистика за месяц

	Time getLastContactTime(); // время последнего контакта

	Boolean getLastContact220();  // AC статус последнего контакта

	Boolean getLastContactLan();  // LAN статус последнего контакта

	Boolean getLastContactLogged();  // Пинг залогирован или нет?

	Logs getLastLog(); // получить последний пинг (лог)

	Temperature getLastTemp(); // получить последнюю запись о температуре

	void updateLastLog(Logs log); // обновить последний пинг (лог) новыми данными



}
