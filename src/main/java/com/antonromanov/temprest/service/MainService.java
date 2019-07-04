package com.antonromanov.temprest.service;

import com.antonromanov.temprest.livecontrolthread.MainParameters;
import com.antonromanov.temprest.model.DailyReport;
import com.antonromanov.temprest.model.Logs;
import com.antonromanov.temprest.model.Status;
import com.antonromanov.temprest.model.Temperature;
import java.text.ParseException;
import java.sql.Time;
import java.util.List;


public interface MainService {


	List<Temperature> getAll(); // список всех измерений (пока с одного датчика)

	List<Logs> getAllLogs(); // все логи

	List<Temperature> addMeasure(Double temp, String status); // добавить измерение

	List<Logs> addLog(Status log) throws ParseException; // добавить измерение \ todo: убрать этот метод потом

	void addLog2(Status log); // добавить измерение. Версия №2 //todo это надо переименовать

	List<Temperature> getTodayMeasures() throws ParseException; //статистика по сегодня

	List<DailyReport> getWeeklyDayReport() throws ParseException; // недельная статистика

	List<DailyReport> getMonthDayReport() throws ParseException; // статистика за месяц

	Time getLastContactTime(); // время последнего контакта

	Boolean getLastContact220();  // AC статус последнего контакта

	Boolean getLastContactLan();  // LAN статус последнего контакта

	Boolean getLastContactLogged();  // Пинг залогирован или нет?

	Status getGlobalStatus(); // глобальное состояние

	Logs getLastLog(); // получить последний пинг (лог)

	Temperature getLastTemp(); // получить последнюю запись о температуре

	MainParameters getMainParametrs(); // еще одно глобальное состояние инкапсулирующее предыдущее + еще параметры

	void updateLastLog(Logs log); // обновить последний пинг (лог) новыми данными



}
