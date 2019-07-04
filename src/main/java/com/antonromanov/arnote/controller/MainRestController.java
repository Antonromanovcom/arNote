package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.service.MainService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.antonromanov.arnote.utils.Utils.*;


/**
 * Основной REST-контроллер приложения.
 */
@RestController
@RequestMapping("/rest/users")
public class MainRestController {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");


	/**
	 * Инжектим сервис.
	 */
	@Autowired
	MainService mainService;


	/**
	 * Глобальные флаги, чтобы отсекать пинги не в нужное время и чтобы в нужное время был только один пинг,
	 * ибо кидаться мы с ардуины будем каждые 15 минут.
	 */
	private boolean at2am = false;
	private boolean at8am = false;
	private boolean at14 = false;
	private boolean at19 = false;




	@GetMapping("/daotest")
	public String testDao() {

	//	Logs user = new Logs(145);
	//	userDao.create(user);

		return "OK";
	}

	@GetMapping("/testinsert")
	public String testInsert() {

	//	userDao.testInsert();

		return "TESTED";
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/testrefs")
	public ResponseEntity<String> testRefCursor() {

		///userDao.testRefCursors();

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
	 * Выплюнуть в http все логи.
	 *
	 * @return
	 */
	@GetMapping("/alllogs")
	public ResponseEntity<String> getAllLogs(HttpServletRequest request) {

		List<Wish> allLogsList = mainService.getAllLogs();
		LOGGER.info("============ ALL LOGS LIST ============== ");
		return createGoodResponse(allLogsList);
	}


}
