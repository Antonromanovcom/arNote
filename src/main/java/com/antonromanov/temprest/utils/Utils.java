package com.antonromanov.temprest.utils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import com.antonromanov.temprest.model.DailyReport;
import com.antonromanov.temprest.model.Status;
import com.antonromanov.temprest.model.Temperature;
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
	 * Проверяем заданное время это дневная температура или ночная
	 *
	 * @param temperatureList
	 * @return
	 */
	public static List<DailyReport> checkDayNight(ArrayList<Temperature> temperatureList) {

		Double dayTemp = 999.0;
		Double nightTemp = 999.0;
		List<DailyReport> weeklyReport = new ArrayList<>();

		if (!temperatureList.isEmpty()) {
			for (Temperature temp : temperatureList) {
				if (temp.getTimeCreated() != null) {
					if (isBetween(temp.getTimeCreated().toLocalTime(), LocalTime.of(13, 0), LocalTime.of(15, 0))) { // если дневное
						dayTemp = temp.getTemperature();
					}
					if (isBetween(temp.getTimeCreated().toLocalTime(), LocalTime.of(1, 0), LocalTime.of(3, 0))) { // если дневное
						nightTemp = temp.getTemperature();
					}
					if ((dayTemp != 999.0) && (nightTemp != 999.0)) {
						weeklyReport.add(new DailyReport(temp.getDateCreated(), dayTemp, nightTemp));
						dayTemp = 999.0;
						nightTemp = 999.0;
					}
				}
			}
		}
		return weeklyReport;
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
	 * Проверяем, что в БД не залоггировали уже температуру
	 *
	 * @param temperatureList
	 * @return
	 */
	public static Boolean isAlreadyWriten(List<Temperature> temperatureList, int startHour, int endHour) {

		if ((!temperatureList.isEmpty())||(temperatureList!=null)) {

			for (Temperature temperature : temperatureList) {

				if ((isBetween(temperature.getTimeCreated().toLocalTime(), LocalTime.of(startHour, 0), LocalTime.of(endHour, 0)))) {
					return true;
				}
			}

		}
		return false;
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

	/**
	 * Создаем хороший (200 OK) response
	 */
	public static ResponseEntity<String> createGoodResponse(Collection collection) {
		String result = createGsonBuilder().toJson(collection);
		return getResponseString(result);
	}

	/**
	 * Создаем хороший (200 OK) response для статуса
	 **/
	public static ResponseEntity<String> createGoodResponse4Status(Status status) {
		String result = createGsonBuilder().toJson(status);
		return getResponseString(result);
	}

	/**
	 * Подготовим ResponseEntity
	 *
	 * @param result
	 * @return
	 */
	private static ResponseEntity<String> getResponseString(String result){
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(result, prepareHeaders(), HttpStatus.OK);
		LOGGER.info("RESPONSE: " + responseEntity.toString());
		return responseEntity;

	}



	/**
	 * Создаем статуса для Телеграмм Бота
	 **/
	public static String createBotStatus(Status status) {
		return createGsonBuilder().toJson(status);
	}


	private static HttpHeaders prepareHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");
		return headers;
	}


}
