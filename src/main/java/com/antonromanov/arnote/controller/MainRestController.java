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
@RequestMapping("/rest/users")
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

	@CrossOrigin(origins = "*")
	@PostMapping("/add")
	public ResponseEntity<String> addWish(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse resp) {

		LOGGER.info("========= ADD WISH ============== ");
		LOGGER.info("PAYLOAD: " + requestParam);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");


		Wish newWish = null;
		ResponseEntity<String> responseEntity;
		ErrorDTO errorDTO = new ErrorDTO();

		try {
			newWish = Optional.ofNullable(mainService.addWish(parseJsonToWish(requestParam))).orElseThrow(SaveNewWishException::new);

			String result = createGsonBuilder().toJson(newWish);
			LOGGER.info("PAYLOAD: " + result);

			responseEntity = new ResponseEntity<String>(result, headers, HttpStatus.OK);
			LOGGER.info("RESPONSE: " + responseEntity.toString());

		} catch (JsonParseException | JsonNullException e) {

			errorDTO.setError(e.getMessage());
			String result = createGsonBuilder().toJson(errorDTO);
			LOGGER.info("PAYLOAD: " + result);

			responseEntity = new ResponseEntity<String>(result, headers, HttpStatus.BAD_REQUEST);
			LOGGER.info("RESPONSE: " + responseEntity.toString());
		} catch (SaveNewWishException e){

			errorDTO.setError("Невозможно сохранить желание!");
			String result = createGsonBuilder().toJson(errorDTO);
			LOGGER.info("PAYLOAD: " + result);

			responseEntity = new ResponseEntity<String>(result, headers, HttpStatus.BAD_REQUEST);
			LOGGER.info("RESPONSE: " + responseEntity.toString());
		}


		return responseEntity;
	}


}
