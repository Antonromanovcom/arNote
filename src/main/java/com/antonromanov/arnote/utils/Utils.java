package com.antonromanov.arnote.utils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


/**
 * Тут собраны основные утлилиты.
 */
public class Utils {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	/**
	 * Определяет лижит ли указанное время между двумя заданными.
	 *
	 * @param candidate
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
		return !candidate.isBefore(start) && !candidate.isAfter(end);  // Inclusive.
	}

	/**
	 * Конвертим SQL-TIME в LOCALTIME
	 *
	 * @param time
	 * @return
	 */
	public static LocalTime toLocalTime(java.sql.Time time) {
		return time.toLocalTime();
	}

	// Проверяем таймут до последнего пинга.
	public static Boolean checkTimeout(Time lastPingTime) {

		Date date = new Date();
		Time time = new Time(date.getTime());
		Boolean result = true;

		if (lastPingTime != null) { // время должно быть не ноль, иначе все наебнется
			// TODO надо еще проверить, чтобы дата была именно сегодняшняя
			LocalTime offsetTime = toLocalTime(lastPingTime).plusMinutes(15);
			result = isBetween(toLocalTime(time), toLocalTime(lastPingTime), offsetTime);
		}
		return result;

	}



	/**
	 * Проверяем ip
	 */
	public static String getIp(HttpServletRequest request) {

		String remoteAddr = "";

		// Пытаемся взять ip
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
				LOGGER.info("GETTING REQUEST FROM:  " + remoteAddr);
			}
		}

		return remoteAddr;
	}

	/**
	 * Формируем ответный JSON
	 */
	public static void createResponseJson(int size, Boolean at2am, Boolean at8am, Boolean at14, Boolean at19, HttpServletRequest request) {

		// Формируем JSON
		JsonObject responseStatusInJson = JSONTemplate.create()
				.add("AllTemperatures", size)
				.add("NightPost", at2am)
				.add("MorningPost", at8am)
				.add("DayPost", at14)
				.add("EveningPost", at19)
				.add("ip", getIp(request)).getJson();

		LOGGER.info("RESULT:  " + responseStatusInJson.toString());
		//return remoteAddr;
	}

	/**
	 * Формируем ответный JSON
	 */
	public static void createResponseJsonForLogs(int size, Boolean at2am, Boolean at8am, Boolean at14, Boolean at19, HttpServletRequest request) {

		// Формируем JSON
		JsonObject responseStatusInJson = JSONTemplate.create()
				.add("AllTemperatures", size)
				.add("NightPost", at2am)
				.add("MorningPost", at8am)
				.add("DayPost", at14)
				.add("EveningPost", at19)
				.add("ip", getIp(request)).getJson();

		LOGGER.info("RESULT:  " + responseStatusInJson.toString());
		//return remoteAddr;
	}



	/**
	 * Формируем ответный JSON
	 */
	public static JsonObject createResponseJsonWithReturn(int size, Boolean at2am, Boolean at8am, Boolean at14, Boolean at19, HttpServletRequest request) {

		// Формируем JSON
		JsonObject responseStatusInJson = JSONTemplate.create()
				.add("AllTemperatures", size)
				.add("NightPost", at2am)
				.add("MorningPost", at8am)
				.add("DayPost", at14)
				.add("EveningPost", at19)
				.add("ip", getIp(request)).getJson();

		LOGGER.info("RESULT:  " + responseStatusInJson.toString());
		return responseStatusInJson;
	}

	/**
	 * Создаем gson builder
	 */
	public static Gson createGsonBuilder() {


		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setDateFormat("dd/MM/yyyy")
				.registerTypeAdapter(java.sql.Time.class, new TimeSerializer())
				.create();

		return gson;
	}



}
