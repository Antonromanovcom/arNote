package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.Exceptions.BadIncomeParameter;
import com.antonromanov.arnote.model.*;
import com.antonromanov.arnote.repositoty.IUserDAO;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.ControllerBase;
import lombok.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.antonromanov.arnote.utils.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;


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


	/**
	 * Инжектим сервис.
	 */
	@Autowired
	MainService mainService;

	@Autowired
	private IUserDAO api;

//	INSERT INTO arnote.wishes (id, wish, priority, price, archive, description, url) VALUES (1, '1', 1, 1, false, 'desc', '1');





	@RequestMapping(method = RequestMethod.GET, value = "/users")
	@ResponseBody
	public List<Wish> findAll(@RequestParam(value = "search", required = false) String search) {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		if (search != null) {
			//Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([a-zA-Z0-9А-Яа-я]*),");
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
			}
		}
		return api.searchUser(params);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/{type}")
	public ResponseEntity<String> gelAllWishes(@PathVariable String type, HttpServletResponse resp) {

		return $do(s -> {
			List<Wish> wishList;

			if ("all".equalsIgnoreCase(type)) {
				wishList = mainService.getAllWishes();
				LOGGER.info("============== GET ALL WISHES ============== ");
			} else {
				wishList = mainService.getAllWishesWithPriority1();
				LOGGER.info("============== GET PRIORITY WISHES ============== ");
			}

			DTO dto = new DTO();
			dto.list.addAll(wishList);

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

	@CrossOrigin(origins = "*")
	@GetMapping("/summ")
	public ResponseEntity<String> getSumm(HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= GET SUMM ============== ");

			String result = createGsonBuilder().toJson(SummEntity.builder()
					.all(mainService.getSumm4All())
					.allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4All()))
					.priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4Prior()))
					.lastSalary(mainService.getLastSalary().getResidualSalary())
					.priority(mainService.getSumm4Prior()).build());
			LOGGER.info("PAYLOAD: " + result);
			return $prepareResponse(result);
		}, null, resp);
	}


	@CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteWish(@PathVariable String id, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= DELETE WISH ============== ");
			LOGGER.info("ID: " + id);
			mainService.deleteWish(id);
			return $prepareResponse(createGsonBuilder().toJson(ResponseStatusDTO.builder().okMessage("OK").status("OK").build()));
		}, null, resp);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/last")
	public ResponseEntity<String> getLastSalary(HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= GET LAST SALARY ============== ");
			String result = createGsonBuilder().toJson(mainService.calculateImplementationPeriod(143000));
			return $prepareResponse(result);
		}, null, resp);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/salary")
	public ResponseEntity<String> addSalary(@RequestBody String requestParam, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= ADD SALARY ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);

			Salary newSalary;
			newSalary = mainService.saveSalary(parseJsonToSalary(requestParam));

			String result = createGsonBuilder().toJson(newSalary);
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, resp);
	}



	@CrossOrigin(origins = "*")
	@PostMapping("/parsecsv")
	public ResponseEntity<String> testXlsxWithFile(
			@RequestParam(required = false, value = "csvfile") MultipartFile csvFile,
			HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("FILE: " + csvFile.getOriginalFilename());

			String result = createGsonBuilder().toJson(mainService.parseCsv(csvFile));
			return $prepareResponse(result);

		}, null, resp);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/changepriority/{id}/{move}")
	public ResponseEntity<String> changePriority(@PathVariable String id, @PathVariable String move, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= MOVE WISH (CHANGE PRIORITY) ============== ");
			LOGGER.info("id: " + id);
			LOGGER.info("move: " + move);

			if ((!"up".equals(move))&&(!"down".equals(move))) throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.PRIORITYCHANGE);
			if ((isBlank(id)) || (!Pattern.compile("^\\d*$").matcher(id).matches())) throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID);
			Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(()->new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));

			switch (move) {
				case "down":
					if (wish.getPriority()> 1) wish.setPriority(wish.getPriority()-1);
					mainService.updateWish(wish);
					break;

				case "up":
					wish.setPriority(wish.getPriority()+1);
					mainService.updateWish(wish);
					break;
			}

			String result = createGsonBuilder().toJson(wish);

			return $prepareResponse(result);

		}, null, resp);
	}





}
