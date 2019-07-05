package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.service.MainService;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import static com.antonromanov.arnote.utils.Utils.*;


/**
 * Основной REST-контроллер приложения.
 */
@RestController
@RequestMapping("/rest/users")
public class MainRestController {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");


	@Data
	private class DTO {


		private List<Wish> list= new ArrayList<>();


//		private Integer price;
//		private Integer priority;

	}


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
		DTO dto = new DTO();
		dto.list.addAll(todayList);
		LOGGER.info("========= TEST ============== ");
		String result = createGsonBuilder().toJson(dto);
		LOGGER.info("PAYLOAD: " + result);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(result, headers, HttpStatus.OK);
		LOGGER.info("RESPONSE: " + responseEntity.toString());

		return responseEntity;
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/update")
	public ResponseEntity<String> updateWish(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse resp) {

		//List<Wish> todayList = mainService.getAllWishes();
		//DTO dto = new DTO();
		//dto.list.addAll(todayList);
		LOGGER.info("========= UPDATE WISH ============== ");
		LOGGER.info("PAYLOAD: " + requestParam);

	//	mainService.updateLastLog();

		/*String result = createGsonBuilder().toJson(dto);
		LOGGER.info("PAYLOAD: " + result);*/
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(null, headers, HttpStatus.OK);
		LOGGER.info("RESPONSE: " + responseEntity.toString());

		return responseEntity;
	}





}
