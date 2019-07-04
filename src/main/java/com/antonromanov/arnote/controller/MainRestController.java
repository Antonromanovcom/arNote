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

		List<Wish> todayList = mainService.getAllWishes();
		LOGGER.info("========= TEST ============== ");
		String result = createGsonBuilder().toJson(todayList);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(result, headers, HttpStatus.OK);
		LOGGER.info("RESPONSE: " + responseEntity.toString());

		return responseEntity;
	}





}
