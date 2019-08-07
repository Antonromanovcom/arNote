package com.antonromanov.arnote.utils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.Salary;
import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.exceptions.*;
import com.antonromanov.arnote.model.WishDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * Тут собраны основные утлилиты.
 */
public class Utils {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	public enum ParseType {ADD, EDIT}

	private static HashMap<Integer, String> colorClasses;


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
	 * Конвертим пришедший json в нового пользака и валидируем
	 */
	public static LocalUser parseJsonToUserAndValidate(String json) throws Exception {

		if (JSONTemplate.fromString(json).getAsJsonObject().size() == 0) {
			throw new JsonNullException("JSON - пустой");
		}

		LocalUser localUser;
		Date currentDate = new Date();


		try {
			// ------------------ Валидация -------------------------

			if (isBlank(JSONTemplate.fromString(json).get("login").getAsString())) throw new JsonParseException(json);

			LocalUser.Role userRole;

			if (("USER".equals(JSONTemplate.fromString(json).get("userRole").getAsString())) ||
					("ADMIN".equals(JSONTemplate.fromString(json).get("userRole").getAsString()))) {
				userRole = LocalUser.Role.valueOf(JSONTemplate.fromString(json).get("userRole").getAsString());
			} else {
				userRole = LocalUser.Role.USER;

			}

			if (JSONTemplate.fromString(json).get("userCryptoMode") == null) throw new JsonParseException(json);
			if (JSONTemplate.fromString(json).get("pwd") == null) throw new JsonParseException(json);
			if (JSONTemplate.fromString(json).get("email") == null) throw new JsonParseException(json);
			if (JSONTemplate.fromString(json).get("fullname") == null) throw new JsonParseException(json);


			localUser = new LocalUser(
					JSONTemplate.fromString(json).get("login").getAsString(),
					userRole,
					JSONTemplate.fromString(json).get("pwd").getAsString(),
					JSONTemplate.fromString(json).get("userCryptoMode").getAsBoolean(),
					JSONTemplate.fromString(json).get("email").getAsString(),
					JSONTemplate.fromString(json).get("fullname").getAsString()
			);

			localUser.setCreationDate(currentDate);

		} catch (Exception e) {
			throw new JsonParseException(json);
		}
		return localUser;
	}


	/**
	 * Конвертим пришедший json в новую Salary
	 */
	public static Salary parseJsonToSalary(String json, LocalUser user) throws Exception {

		if (JSONTemplate.fromString(json).getAsJsonObject().size() == 0) {
			throw new JsonNullException("JSON - пустой");
		}

		Salary salary;
		Date currentDate = new Date();

		try {
			salary = new Salary(
					JSONTemplate.fromString(json).get("fullsalary").getAsInt(),
					JSONTemplate.fromString(json).get("residualSalary").getAsInt()
			);
			salary.setSalarydate(currentDate);
			salary.setUser(user);
		} catch (Exception e) {
			throw new JsonParseException(json);
		}
		return salary;
	}


	/**
	 * Конвертим пришедший json в новый WISH
	 */
	public static Wish parseJsonToWish(ParseType parseType, String json, LocalUser user) throws Exception {

		if (JSONTemplate.fromString(json).getAsJsonObject().size() == 0) {
			throw new JsonNullException("JSON - пустой");
		}

		Wish wishAfterParse;

		try {

			if (parseType == ParseType.EDIT) {
				wishAfterParse = new Wish(
						JSONTemplate.fromString(json).get("id").getAsLong(),
						JSONTemplate.fromString(json).get("wish").getAsString(),
						JSONTemplate.fromString(json).get("price").getAsInt(),
						JSONTemplate.fromString(json).get("priority").getAsInt(),
						JSONTemplate.fromString(json).get("archive").getAsBoolean(),
						JSONTemplate.fromString(json).get("description").getAsString(),
						JSONTemplate.fromString(json).get("url").getAsString(),
						user, 1, 1);

			} else {
				wishAfterParse = new Wish(
						JSONTemplate.fromString(json).get("wish").getAsString(),
						JSONTemplate.fromString(json).get("price").getAsInt(),
						JSONTemplate.fromString(json).get("priority").getAsInt(),
						JSONTemplate.fromString(json).get("archive").getAsBoolean(),
						JSONTemplate.fromString(json).get("description").getAsString(),
						JSONTemplate.fromString(json).get("url").getAsString(),
						user
				);
			}
		} catch (Exception e) {
			throw new JsonParseException(json);
		}

		return wishAfterParse;
	}

	public static String generateRandomPassword() {

		List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1));

		PasswordGenerator generator = new PasswordGenerator();
		String password = generator.generatePassword(8, rules);
		return password;
	}

	public static String computerMonth(Integer proirity) {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();

		Locale currentLocale = Locale.getDefault();
		return Month.of(month + (proirity - 1)).getDisplayName(TextStyle.FULL_STANDALONE, currentLocale);
	}

	public static int computerMonthNumber(Integer proirity) {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		return Month.of(month + (proirity - 1)).getValue();
	}

	public static int getCurrentYear() {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getYear();
	}

	public static WishDTO prepareWishDTO(Wish w, int maxPrior) {
		return WishDTO.builder()
				.id(w.getId())
				.wish(w.getWish().length()<50 ? w.getWish() : w.getWish().substring(0, 50) + "...")
				.price(w.getPrice())
				.priority(w.getPriority())
				.ac(w.getAc())
				.description(w.getDescription())
				.url(w.getUrl())
				.priorityGroup(w.getPriorityGroup())
				.priorityGroupOrder(w.getPriorityGroupOrder())
				.month(computerMonth(w.getPriorityGroup() == null ? maxPrior : w.getPriorityGroup()))
				.build();
	}

	public static String getClassColorByMonth(int month, boolean overdraft) {

		colorClasses = new HashMap<>();
		colorClasses.put(1, "label label-purple");
		colorClasses.put(2, "label label-blue");
		colorClasses.put(3, "label label-light-blue");
		colorClasses.put(4, "label label-orange");
		colorClasses.put(5, "label label-success");
		colorClasses.put(6, "label label-purple");
		colorClasses.put(7, "label label-blue");
		colorClasses.put(8, "label label-light-blue");
		colorClasses.put(9, "label label-orange");
		colorClasses.put(10, "label label-success");
		colorClasses.put(11, "label label-purple");
		colorClasses.put(12, "label label-blue");
		colorClasses.put(13, "label label-danger");

		LOGGER.info("MONTH (getClassColorByMonth) => " + month);

		if (!overdraft) {
			if (month == 0) {
				return colorClasses.get(1);
			} else {
				LOGGER.info("NEW MONTH (getClassColorByMonth) => " + month);
				LOGGER.info("CLASS (getClassColorByMonth) => " + colorClasses.get(month));
				return colorClasses.get(month);
			}
		} else {
			return colorClasses.get(13);
		}
	}


}
