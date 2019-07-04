package com.antonromanov.arnote.controller;


import com.antonromanov.arnote.model.DailyReport;
import com.antonromanov.arnote.model.Logs;
import com.antonromanov.arnote.model.Temperature;
import com.antonromanov.arnote.repositoty.DAO;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.JSONTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import static com.antonromanov.arnote.utils.Utils.*;


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

	@CrossOrigin(origins = "*")
	@GetMapping("/testrefs")
	public ResponseEntity<String> testRefCursor() {

		userDao.testRefCursors();

		List<String> todayList = new ArrayList<>();
		LOGGER.info("========= TEST ============== ");
		todayList.add("111");
		todayList.add("aaaa");
		String result = createGsonBuilder().toJson(todayList);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(result, headers, HttpStatus.OK);
		LOGGER.info("RESPONSE: " + responseEntity.toString());

		return responseEntity;
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
