package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.email.EmailSender;
import com.antonromanov.arnote.email.EmailStatus;
import com.antonromanov.arnote.model.*;
import com.antonromanov.arnote.repositoty.IUserDAO;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.MainService;
import com.antonromanov.arnote.utils.ControllerBase;
import lombok.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.utils.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;



//todo: надо нормально поименовать ендпоинты
//todo: надо сделать проверку на уникальность добавляемых желаний

/**
 * Основной REST-контроллер приложения.
 */
@CrossOrigin()
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
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UsersRepo usersRepo;

	@Autowired
	private IUserDAO api;

	@Autowired
	private EmailSender emailSender;



	@RequestMapping(method = RequestMethod.GET, value = "/users")
	@ResponseBody
	public List<Wish> findAll(@RequestParam(value = "search", required = false) String search) {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		if (search != null) {
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
	public ResponseEntity<String> gelAllWishes(@PathVariable String type, Principal principal, HttpServletResponse resp) {

		return $do(s -> {
			List<Wish> wishList;

			LOGGER.info("PRINCIPAL: " + principal.getName());
			LocalUser localUser = getUserFromPrincipal(principal);



			if ("all".equalsIgnoreCase(type)) {
				wishList = mainService.getAllWishesByUserId(localUser);
				LOGGER.info("============== GET ALL WISHES ============== ");
			} else {
				wishList = mainService.getAllWishesWithPriority1(localUser);
				LOGGER.info("============== GET PRIORITY WISHES ============== ");
			}

			DTO dto = new DTO();
			dto.list.addAll(wishList);

			String result = createGsonBuilder().toJson(dto);
			LOGGER.info("PAYLOAD (wishes count): " + dto.list.size());
			return $prepareResponse(result);
		}, null, resp);
	}

	@CrossOrigin(origins = "*")
	@PutMapping
	public ResponseEntity<String> updateWish(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= UPDATE WISH ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);

			mainService.updateWish(parseJsonToWish(ParseType.EDIT, requestParam, usersRepo));

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
			newWish = mainService.addWish(parseJsonToWish(ParseType.ADD, requestParam, usersRepo));

			String result = createGsonBuilder().toJson(newWish);
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, resp);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/summ")
	public ResponseEntity<String> getSumm(HttpServletResponse resp, Principal principal) {

		return $do(s -> {
			LOGGER.info("========= GET SUMM ============== ");
			LOGGER.info("PRINCIPAL: " + principal.getName());

			LocalUser localUser = getUserFromPrincipal(principal);

			String result = createGsonBuilder().toJson(SummEntity.builder()
					.all(mainService.getSumm4All(localUser))
					.allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4All(localUser), localUser))
					.priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4Prior(localUser), localUser))
					.lastSalary(mainService.getLastSalary(localUser).getResidualSalary())
					.priority(mainService.getSumm4Prior(localUser)).build());
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
	public ResponseEntity<String> getLastSalary(HttpServletResponse resp, Principal principal) {

		return $do(s -> {
			LOGGER.info("========= GET LAST SALARY ============== ");
			LOGGER.info("PRINCIPAL: " + principal.getName());

			LocalUser localUser = getUserFromPrincipal(principal);


			String result = createGsonBuilder().toJson(mainService.getLastSalary(localUser).getResidualSalary());
			return $prepareResponse(result);
		}, null, resp);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/salary")
	public ResponseEntity<String> addSalary(@RequestBody String requestParam, HttpServletResponse resp, Principal principal) {


		return $do(s -> {

			LOGGER.info("========= ADD SALARY ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);
			LOGGER.info("PRINCIPAL: " + principal.getName());

			LocalUser localUser = getUserFromPrincipal(principal);

			Salary newSalary;
			newSalary = mainService.saveSalary(parseJsonToSalary(requestParam, localUser));

			String result = createGsonBuilder().toJson(newSalary);
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, resp);
	}


	@CrossOrigin(origins = "*")
	@PostMapping("/parsecsv")
	public ResponseEntity<String> parseCsv(
			@RequestParam(required = false, value = "csvfile") MultipartFile csvFile, Principal principal,
			HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("FILE: " + csvFile.getOriginalFilename());
			LocalUser localUser = getUserFromPrincipal(principal);
			LOGGER.info("PRINCIPAL: " + localUser.toString());

			String result = createGsonBuilder().toJson(mainService.parseCsv(csvFile, localUser));
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

			if ((!"up".equals(move)) && (!"down".equals(move)))
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.PRIORITYCHANGE);
			if ((isBlank(id)) || (!Pattern.compile("^\\d*$").matcher(id).matches()))
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID);
			Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));

			switch (move) {
				case "down":
					if (wish.getPriority() > 1) wish.setPriority(wish.getPriority() - 1);
					mainService.updateWish(wish);
					break;

				case "up":
					wish.setPriority(wish.getPriority() + 1);
					mainService.updateWish(wish);
					break;
			}

			String result = createGsonBuilder().toJson(wish);

			return $prepareResponse(result);

		}, null, resp);
	}


	@CrossOrigin(origins = "*")
	@PostMapping("/users")
	public ResponseEntity<String> addUser(@RequestBody String user, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= ADD USER  ============== ");
			LOGGER.info("PAYLOAD: " + user);


			LocalUser newUser = parseJsonToUserAndValidate(user);
			newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));


			if (usersRepo.findByLogin(newUser.getLogin()).isPresent()) {
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
			}

			usersRepo.save(newUser);

			return $prepareResponse(createGsonBuilder().toJson(newUser));

		}, null, resp);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= DELETE USER  ============== ");
			LOGGER.info("PAYLOAD: " + id);


			if (!usersRepo.findById(Long.valueOf(id)).isPresent()) {
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST);
			}

			usersRepo.deleteById(Long.valueOf(id));

			return $prepareResponse(createGsonBuilder().toJson(id));

		}, id, resp);
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/users/{id}")
	public ResponseEntity<String> editUser(@RequestBody String user, @PathVariable String id, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= EDIT USER  ============== ");
			LOGGER.info("PAYLOAD: " + user);
			LOGGER.info("id: " + id);

			LocalUser newUser = parseJsonToUserAndValidate(user);

			if (usersRepo.findByLogin(newUser.getLogin()).isPresent()) {
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
			}

			newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));

			LocalUser user4update = usersRepo.findById(Long.valueOf(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST));
			user4update.setPwd(newUser.getPwd());
			user4update.setLogin(newUser.getLogin());
			user4update.setEmail(newUser.getEmail());
			user4update.setFullname(newUser.getFullname());

			return $prepareResponse(createGsonBuilder().toJson(usersRepo.saveAndFlush(user4update)));

		}, user, resp);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/users/list")
	public ResponseEntity<String> getAllUsers(HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= GET ALL USERS  ============== ");

			List<LocalUser> userList = usersRepo.findAll().stream().map(u -> {
				if (u.getCreationDate() == null) u.setCreationDate(new Date());
				return u;
			}).collect(Collectors.toList());

			return $prepareResponse(createGsonBuilder().toJson(userList));
		}, null, resp);
	}


	/**
	 * Метод, который дергается, если пользователь забыл пароль.
	 *
	 * @param email
	 * @param resp
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/users/forget", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> returnUserPassword(@RequestParam(name = "email") String email, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= FORGET PWD METHOD =============== ");
			LOGGER.info("USER EMAIL - " + email);
			try {
				LocalUser localUser = usersRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
				return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, email).getStatus()));
			} catch (UserNotFoundException e) {
				return $prepare400Response(createGsonBuilder().toJson("No such user!"));
			}
		}, null, resp);
	}

	/**
	 * Сброс юзерского пароля админом.
	 *
	 * @param id
	 * @param resp
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@GetMapping("/users/reset/{id}")
	public ResponseEntity<String> resetUserPasswordByAdmin(@PathVariable String id, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= RESET USER PWD =============== ");
			LOGGER.info("USER ID - " + id);
			try {
				LocalUser localUser = usersRepo.findById(Long.parseLong(id)).orElseThrow(UserNotFoundException::new);
				return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, localUser.getEmail()).getStatus()));
			} catch (UserNotFoundException e) {
				return $prepare400Response(createGsonBuilder().toJson("No such user!"));
			}
		}, null, resp);
	}


	/**
	 * Смена pwd и отправка уведомления на почту.
	 * @param user
	 * @param email
	 * @return
	 */
	private EmailStatus changePwd(LocalUser user, String email){

		LOGGER.info("USER FOUND - " + user.toString());

		String pwd = generateRandomPassword();
		LOGGER.info("NEW PWD - " + pwd);
		user.setPwd(passwordEncoder.encode(pwd));
		LocalUser updatedUser = usersRepo.saveAndFlush(user);
		LOGGER.info("UPDATED USER - " + updatedUser.toString());


		return emailSender.sendPlainText(email, "Ваши данные для доступа к arNote", "Ваш пароль - " + pwd + " [email - " + email + " ]");

	}

	/**
	 * Вытаскиваем юзера из Принципала
	 *
	 * @param principal
	 * @return
	 */
	private LocalUser getUserFromPrincipal(Principal principal) throws UserNotFoundException {
		return usersRepo.findByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
	}
}
