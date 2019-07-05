package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.Exceptions.JsonNullException;
import com.antonromanov.arnote.Exceptions.JsonParseException;
import com.antonromanov.arnote.Exceptions.SaveNewWishException;
import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.ControllerBase;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import static com.antonromanov.arnote.utils.Utils.*;


/**
 * Основной REST-контроллер приложения.
 */
@RestController
@RequestMapping("/rest/wishes")
public class MainRestController extends ControllerBase {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");


	@Data
	private class DTO {
		private List<Wish> list = new ArrayList<>();
	}

	@Data
	private class ErrorDTO {
		private String error;
	}


	/**
	 * Инжектим сервис.
	 */
	@Autowired
	MainService mainService;


	@GetMapping("/daotest")
	public String testDao(HttpServletResponse resp) {
		String str = "0111";
		return $do(s -> {
			String r = str + " 145";
			System.out.println(r);
			//throw new JsonNullException("gbgf");
			return r;
		}, str, resp);
	}

	@GetMapping("/testinsert")
	public String testInsert() {

		//	userDao.testInsert();

		return "TESTED";
	}

	@CrossOrigin(origins = "*")
	@GetMapping
	public ResponseEntity<String> gelAllWishes(HttpServletResponse resp) {

		return $do(s -> {

			List<Wish> todayList = mainService.getAllWishes();
			DTO dto = new DTO();
			dto.list.addAll(todayList);
			LOGGER.info("============== GET ALL WISHES ============== ");
			String result = createGsonBuilder().toJson(dto);
			LOGGER.info("PAYLOAD: " + result);
			return $prepareResponse(result);
		}, null, resp);
	}

	@CrossOrigin(origins = "*")
	@PutMapping
	public ResponseEntity<String> updateWish(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= UPDATE WISH ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);

			mainService.updateWish(parseJsonToWish(ParseType.EDIT, requestParam));

			String result = "";
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, resp);


	}

	@CrossOrigin(origins = "*")
	@PostMapping
	public ResponseEntity<String> addWish(@RequestBody String requestParam, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= ADD WISH ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);

			Wish newWish;
			newWish = mainService.addWish(parseJsonToWish(ParseType.ADD, requestParam));

			String result = createGsonBuilder().toJson(newWish);
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, resp);
	}
}
