package com.antonromanov.temprest.controller;


import com.antonromanov.temprest.model.DailyReport;
import com.antonromanov.temprest.model.Logs;
import com.antonromanov.temprest.model.Status;
import com.antonromanov.temprest.model.Temperature;
import com.antonromanov.temprest.repositoty.DAO;
import com.antonromanov.temprest.service.MainService;
import com.antonromanov.temprest.utils.JSONTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;
import com.google.gson.*;
import static com.antonromanov.temprest.utils.Utils.*;


/**
 * Основной REST-контроллер приложения.
 */
@RestController
@RequestMapping("/rest/users")
public class MainRestController {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	List<Temperature> allTemperatures = new ArrayList<>();


	// todo: надо поменять название проекта
	// todo: надо поменять названия классов и переменных
	// todo: надо поменять адрес REST API
	// todo: прикрутить телеграмм-бота
	// todo: перейти со Спринга на СпрингБут
	// todo: надо поменять еще вот эту ссылку - http://localhost:8080/FirstSPRINGJDBC-2.0-SNAPSHOT/rest/api/add. Чо за ФёрстСпрингДжейДиБиСи ????!!!!!!!
	// todo:  прикрутить экран к ардуине
	// todo:  нужно логгировать и за-трай-кетчить все ошибки, чтобы я их видел потом в боте
	// todo:  прикрутить лампочки и вывод инфы (например, флаги) на OLED.
	// todo: temperature_copy переименовать в нормальную
	// todo: переименовать и перенести все модели в Ангуляре, например что за User.ts - ????
	// todo: переименовать и перенести (Ангуляр) (this.year и this.count)
	// todo: переименовать методы типа addlog3. Ну что это за пипец.....
	// todo: сделать отправку логов/состояний на почту
	// todo: Убрать ненужные депенденси
	// todo: переименовать лог-файл



	/** Значит надо договориться, что постить мы будем в :
	 *      - 02:00
	 *      - 08:00
	 *      - 14:00
	 *      - 19:00
	 */


	/**
	 * Инжектим сервис.
	 */
	@Autowired
	MainService mainService;


	@Autowired
	DAO userDao;


	/**
	 * Глобальные флаги, чтобы отсекать пинги не в нужное время и чтобы в нужное время был только один пинг,
	 * ибо кидаться мы с ардуины будем каждые 15 минут.
	 */
	private boolean at2am = false;
	private boolean at8am = false;
	private boolean at14 = false;
	private boolean at19 = false;


	/**
	 * Выдать все измерения температуры.
	 *
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<String> getAll(HttpServletRequest request) {

		List<Temperature> allTemperaturesList = mainService.getAll();
		LOGGER.info("========= ALL MEASURES LIST ============== ");
		createResponseJson(allTemperaturesList.size(), at2am, at8am, at14, at19, request);

		return createGoodResponse(allTemperaturesList);
	}


	@GetMapping("/daotest")
	public String testDao() {

		Logs user = new Logs(145);
		userDao.create(user);

		return "OK";
	}

	@GetMapping("/testinsert")
	public String testInsert() {

		userDao.testInsert();

		return "TESTED";
	}

	@GetMapping("/testrefs")
	public String testRefCursor() {

		userDao.testRefCursors();

		return "REF CURSORS TESTED";
	}





	/**
	 * Выдать статистику за сегодня.
	 *
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/today")
	public ResponseEntity<String> getTodayMeasures(HttpServletRequest request) throws ParseException {

		List<Temperature> todayList = mainService.getTodayMeasures();
		LOGGER.info("========= TODAY MEASURES LIST ============== ");
		createResponseJson(todayList.size(), at2am, at8am, at14, at19, request);

		return createGoodResponse(todayList);
	}


	/**
	 * Выдать статистику за неделю.
	 *
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/week")
	public ResponseEntity<String> getWeeklyReport(HttpServletRequest request) throws ParseException {

		LOGGER.info("========= WEEK MEASURES LIST ============== ");
		List<DailyReport> weekList = mainService.getWeeklyDayReport();
		createResponseJson(weekList.size(), at2am, at8am, at14, at19, request);

		return createGoodResponse(weekList);
	}


	/**
	 * Выдать статистику за месяц.
	 *
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/month")
	public ResponseEntity<String> getMonthReport(HttpServletRequest request) throws ParseException {

		List<DailyReport> monthList = mainService.getMonthDayReport();
		LOGGER.info("========= MONTH MEASURES LIST ============== ");
		createResponseJson(monthList.size(), at2am, at8am, at14, at19, request);

		return createGoodResponse(monthList);
	}


	/**
	 * Выдать текущий статус (состояние мониторинга).
	 *
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/status")
	public ResponseEntity<String> getStatus() throws ParseException { //todo: причем тут parseException ????

		LOGGER.info("========= GET STATUS ============== ");

	return createGoodResponse4Status(mainService.getGlobalStatus());

	}

	/**
	 * Выплюнуть в http все логи.
	 *
	 * @return
	 */
	@GetMapping("/alllogs")
	public ResponseEntity<String> getAllLogs(HttpServletRequest request) {

		List<Logs> allLogsList = mainService.getAllLogs();
		LOGGER.info("============ ALL LOGS LIST ============== ");
		return createGoodResponse(allLogsList);
	}

	/**
	 * Добавить состояние мониторинга (для Ардуины).
	 *
	 * @param requestParam - строка json
	 * @param request      - служебное
	 * @param resp         - служебное
	 * @return - статус и строка json
	 * @throws ParseException
	 */
	@PostMapping("/addlog")
	public ResponseEntity<String> addLog(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse resp) throws ParseException {

      /*
        BODY:

        {
            "who": null,
                "lastTemperature": -15,
                "lastHumidity": -10,
                "serverTime": null,
                "lastContactTime": null,
                "current": 15,
                "amperage": 150,
                "power": 18,
                "consuming": 1024,
                "lastContactDate": null,
                "acOn": true,
                "lanOn": true

        }*/


		Date currentDate = new Date();
		Time time = new Time(currentDate.getTime());
		String remoteAddr = "";
		Status newStatus = null;

		LOGGER.info("################# ADD LOG METHOD #####################");
		LOGGER.info("LOG: " + requestParam);

		// Парсим пришедший JSON  с температурой
		try {

			String who = ((JSONTemplate.fromString(requestParam).get("who") == null) || (JSONTemplate.fromString(requestParam).get("who").isJsonNull())) ? "LearnFlux" : JSONTemplate.fromString(requestParam).get("who").getAsString();
			Boolean acOn = ((JSONTemplate.fromString(requestParam).get("acOn") == null) || (JSONTemplate.fromString(requestParam).get("acOn").isJsonNull())) ? false : JSONTemplate.fromString(requestParam).get("acOn").getAsBoolean();
			Boolean lanOn = ((JSONTemplate.fromString(requestParam).get("lanOn") == null) || (JSONTemplate.fromString(requestParam).get("lanOn").isJsonNull())) ? false : JSONTemplate.fromString(requestParam).get("lanOn").getAsBoolean();
			int lastTemperature = ((JSONTemplate.fromString(requestParam).get("lastTemperature") == null) || (JSONTemplate.fromString(requestParam).get("lastTemperature").isJsonNull())) ? 0 : JSONTemplate.fromString(requestParam).get("lastTemperature").getAsInt();
			int lastHumidity = ((JSONTemplate.fromString(requestParam).get("lastHumidity") == null) || (JSONTemplate.fromString(requestParam).get("lastHumidity").isJsonNull())) ? 0 : JSONTemplate.fromString(requestParam).get("lastHumidity").getAsInt();
			int current = ((JSONTemplate.fromString(requestParam).get("current") == null) || (JSONTemplate.fromString(requestParam).get("current").isJsonNull())) ? 0 : JSONTemplate.fromString(requestParam).get("current").getAsInt();
			int amperage = ((JSONTemplate.fromString(requestParam).get("amperage") == null) || (JSONTemplate.fromString(requestParam).get("amperage").isJsonNull())) ? 0 : JSONTemplate.fromString(requestParam).get("amperage").getAsInt();
			int power = ((JSONTemplate.fromString(requestParam).get("power") == null) || (JSONTemplate.fromString(requestParam).get("power").isJsonNull())) ? 0 : JSONTemplate.fromString(requestParam).get("power").getAsInt();
			int consuming = ((JSONTemplate.fromString(requestParam).get("consuming") == null) || (JSONTemplate.fromString(requestParam).get("consuming").isJsonNull())) ? 0 : JSONTemplate.fromString(requestParam).get("consuming").getAsInt();


			newStatus = new Status(
					who,
					acOn,
					lanOn,
					lastTemperature,
					lastHumidity,
					time, // serverTime
					time, // lastContactTime
					current,
					amperage,
					power,
					consuming,
					currentDate, false); // lastContactDate

			LOGGER.info("PARSING RESULT: " + newStatus.toString());

		} catch (JsonParseException ex) {

			try {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка парсинга JSON");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Пытаемся взять ip
		remoteAddr = getIp(request);

		List<Logs> allStatuses = mainService.getAllLogs();
		ResponseEntity<String> responseEntity = null;
//		// Формируем JSON
		JsonObject responseStatusInJson = null;
		//responseStatusInJson.
		//responseStatusInJson.

		//todo: вот тут надо наормально обрабатывать эксепшены работы с бд. трай-кетчем
		// todo: так же надо как-то обрабатывать ситуацию, если статус мы не заполнили
		if (newStatus != null) {
			mainService.addLog2(newStatus);
			// Формируем JSON
			responseStatusInJson = JSONTemplate.create()
					.add("AllStatuses", allStatuses.size())
					.add("ip", remoteAddr).getJson();
			responseEntity = new ResponseEntity<>(responseStatusInJson.toString(), HttpStatus.OK);

		} else {
			responseStatusInJson = JSONTemplate.create()
					.add("ERROR", "newStatus - null")
					.add("ip", remoteAddr).getJson();
			responseEntity = new ResponseEntity<>(responseStatusInJson.toString(), HttpStatus.BAD_GATEWAY);
		}

		return responseEntity;
	}

	private List<Temperature> addTemperatureMeasure(Double temp, HttpServletRequest request){
		return mainService.addMeasure(temp, createResponseJsonWithReturn(allTemperatures.size(), at2am, at8am, at14, at19, request).toString());
	}

	/**
	 * Добавить состояние мониторинга (для Ардуины).
	 * Отсылаем с Ардуины - {"temp":120}
	 *
	 * @param requestParam - json от Ардуины
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/add")
	public ResponseEntity<String> addLog3(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse resp) throws ParseException {

		Date currentDate = new Date();
		Time time = new Time(currentDate.getTime());
		String remoteAddr = "";

		LOGGER.info("We are in POST HTTP: " + requestParam);
		allTemperatures = mainService.getAll();

		try {

			Double temp = JSONTemplate.fromString(requestParam).get("temp").getAsDouble();

			if ((isBetween(time.toLocalTime(), LocalTime.of(1, 0), LocalTime.of(3, 0))) && !at2am) { // если 2 часа ночи
				at2am = true;

				// Проверяем, что такой температуры нет еще за сегодня
				if (!isAlreadyWriten(mainService.getTodayMeasures(), 1, 3)) {
					allTemperatures = addTemperatureMeasure(temp, request);
					LOGGER.info("POST NIGHT TEMPERATURE --------- SUCCESS:  " + time.toLocalTime());
				} else {
					LOGGER.error("POST NIGHT TEMPERATURE --------- FAIL - DUPLICATE MEASURE:  " + time.toLocalTime());
				}
			}
			if ((isBetween(time.toLocalTime(), LocalTime.of(7, 0), LocalTime.of(9, 0))) && !at8am) { // если 8 утра
				at8am = true;

				// Проверяем, что такой температуры нет еще за сегодня
				if (!isAlreadyWriten(mainService.getTodayMeasures(), 7, 9)) {
					allTemperatures = addTemperatureMeasure(temp, request);
					LOGGER.info("POST MORNING TEMPERATURE --------- SUCCESS:  " + time.toLocalTime());
				} else {
					LOGGER.error("POST MORNING TEMPERATURE --------- FAIL - DUPLICATE MEASURE:  " + time.toLocalTime());
				}
			}
			if ((isBetween(time.toLocalTime(), LocalTime.of(13, 0), LocalTime.of(15, 0))) && !at14) { // если 14 часов дня
				at14 = true;

				// Проверяем, что такой температуры нет еще за сегодня
				if (!isAlreadyWriten(mainService.getTodayMeasures(), 13, 15)) {
					allTemperatures = addTemperatureMeasure(temp, request);
					LOGGER.info("POST DAY TEMPERATURE --------- SUCCESS:  " + time.toLocalTime());
				} else {
					LOGGER.error("POST DAY TEMPERATURE --------- FAIL - DUPLICATE MEASURE:  " + time.toLocalTime());
				}

			}
			if ((isBetween(time.toLocalTime(), LocalTime.of(18, 0), LocalTime.of(20, 0))) && !at19) { // если 19 часов вечера
				at19 = true;

				// Проверяем, что такой температуры нет еще за сегодня
				if (!isAlreadyWriten(mainService.getTodayMeasures(), 18, 20)) {
					allTemperatures = addTemperatureMeasure(temp, request);
					LOGGER.info("POST EVENING TEMPERATURE --------- SUCCESS:  " + time.toLocalTime());
				} else {
					LOGGER.error("POST EVENING TEMPERATURE --------- FAIL - DUPLICATE MEASURE:  " + time.toLocalTime());
				}
			}

			if (at2am && at8am && at14 && at19) {

				at2am = false;
				at8am = false;
				at14 = false;
				at19 = false;
			}
		} catch (JsonParseException ex) {

			try {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка парсинга JSON");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		ResponseEntity<String> responseEntity = new ResponseEntity<>(createResponseJsonWithReturn(allTemperatures.size(),
				at2am, at8am, at14, at19, request).toString(), HttpStatus.OK);
		LOGGER.info("RESPONSE: " + responseEntity.toString());
		return responseEntity;
	}
}
