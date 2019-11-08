package com.antonromanov.arnote.controller;

import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.email.EmailSender;
import com.antonromanov.arnote.email.EmailStatus;
import com.antonromanov.arnote.model.*;
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
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.utils.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;


//todo: надо нормально поименовать ендпоинты
//todo: надо сделать проверку на уникальность добавляемых желаний
// SELECT sum(w.price) from arnote.wishes w WHERE w.user_id = 8 AND w.realized AND NOT w.archive - запрос реализованных желаний

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

	@Data
	private class DTOwithOrder {
		private List<WishDTOList> list = new ArrayList<>();
	}

	@Autowired
	MainService mainService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UsersRepo usersRepo;

	@Autowired
	private EmailSender emailSender;


	@CrossOrigin(origins = "*")
	@PostMapping("/filter")
	public ResponseEntity<String> findAll(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {


		return $do(s -> {
			LOGGER.info("============== FILTER WISHES ============== ");
			LOGGER.info("VALUE: " + requestParam);
			LOGGER.info("PRINCIPAL: " + principal.getName());
			LocalUser localUser = getUserFromPrincipal(principal);

			List<Wish> wishes = mainService
					.findAllWishesByWish(parseJsonToWish(ParseType.EDIT, requestParam, localUser).getWish(), localUser)
					.orElseGet(ArrayList::new);

			DTO dto = new DTO();
			dto.list.addAll(wishes);

			String res = createGsonBuilder().toJson(dto);
			LOGGER.info("PAYLOAD: " + res);

			return $prepareResponse(res);

		}, null, null, null, resp);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/groups")
	public ResponseEntity<String> gelAllWishesWithMonthGroupping(Principal principal, @RequestParam String sortType, HttpServletResponse resp) {

		return $do(s -> {
			List<WishDTOList> wishListWithMonthOrder;
			LOGGER.info("PRINCIPAL: " + principal.getName());
			LocalUser localUser = getUserFromPrincipal(principal);
			if (mainService.getAllWishesByUserId(localUser).size() > 0) {

				DTOwithOrder dtOwithOrder = new DTOwithOrder();
				String result = "";
				wishListWithMonthOrder = mainService.getAllWishesWithGroupPriority(localUser);
				dtOwithOrder.list.addAll(wishListWithMonthOrder);

				if ("name".equalsIgnoreCase(sortType)) {
					LOGGER.info("SORTING BY NAME");
					dtOwithOrder.list.forEach(wl -> {
						wl.getWishList().sort(Comparator.comparing(WishDTO::getWish));
					});
				} else if ("price-asc".equalsIgnoreCase(sortType)) {
					LOGGER.info("SORTING BY PRICE ASC");
					dtOwithOrder.list.forEach(wl -> {
						wl.getWishList().sort(Comparator.comparing(WishDTO::getPrice));
					});
				} else if ("price-desc".equalsIgnoreCase(sortType)) {
					LOGGER.info("SORTING BY PRICE DESC");
					Comparator<WishDTO> comparator = Comparator.comparing(WishDTO::getPrice);
					dtOwithOrder.list.forEach(wl -> {
						wl.getWishList().sort(comparator.reversed());
					});
				}

				result = createNullableGsonBuilder().toJson(dtOwithOrder);
				LOGGER.info("PAYLOAD (wishes count): " + dtOwithOrder.list.size());
				LOGGER.info("============== GET WISHES WITH GROUP ORDER ============== ");

				return $prepareResponse(result);
			} else {
				return $prepareNoDataYetErrorResponse(false);
			}
		}, null, null, null, resp);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/transferwish")
	public ResponseEntity<String> changeMonthOrder(Principal principal, @RequestParam String id, @RequestParam String month, HttpServletResponse resp) {
		return $do(s -> {

			LOGGER.info("========= MOVE WISH (CHANGE MONTH PRIORITY) ============== ");
			LOGGER.info("PRINCIPAL: " + principal.getName());
			LOGGER.info("id: " + id);

			Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
			wish.setPriorityGroup(parseMonthAndCalculatePriority(month));

			String result = createNullableGsonBuilder().toJson(mainService.updateAndFlushWish(wish));
			return $prepareResponse(result);

		}, null, null, OperationType.EDIT_WISH, resp);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/{type}")
	public ResponseEntity<String> gelAllWishes(Principal principal, @PathVariable String type, HttpServletResponse resp) {

		return $do(s -> {
			List<Wish> wishList;
			List<WishDTOList> wishListWithMonthOrder;
			LOGGER.info("PRINCIPAL: " + principal.getName());
			LocalUser localUser = getUserFromPrincipal(principal);
			if (mainService.getAllWishesByUserId(localUser).size() > 0) {

				DTO dto = new DTO();
//				DTOwithOrder dtOwithOrder = new DTOwithOrder();
				String result = "";

				if ("all".equalsIgnoreCase(type)) {
					wishList = mainService.getAllWishesByUserId(localUser);

					// Предотвращение вываливания на пустых датах
					wishList.forEach(w -> {
						if (w.getCreationDate() == null) w.setCreationDate(new Date());
						if (w.getRealized() == null) w.setRealized(false);
					});

					dto.list.addAll(wishList);
					result = createNullableGsonBuilder().toJson(dto);
					LOGGER.info("PAYLOAD (wishes count): " + dto.list.size());
					LOGGER.info("============== GET ALL WISHES ============== ");
				} else {
					wishList = mainService.getAllWishesWithPriority1(localUser);
					dto.list.addAll(wishList);
					LOGGER.info("============== GET PRIORITY WISHES ============== ");
					result = createNullableGsonBuilder().toJson(dto);
					LOGGER.info("PAYLOAD (wishes count): " + dto.list.size());
				}

				return $prepareResponse(result);
			} else {
				return $prepareNoDataYetErrorResponse(false);
			}
		}, null, null, OperationType.GET_ALL_WISHES, resp);
	}

	@CrossOrigin(origins = "*")
	@PutMapping
	public ResponseEntity<String> updateWish(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= UPDATE WISH ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);
			LocalUser localUser = getUserFromPrincipal(principal);
			Wish wish = parseJsonToWish(ParseType.EDIT, requestParam, localUser);
			mainService.updateWish(mainService.updateMonthGroup(wish));

			String result = "";
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, null, OperationType.EDIT_WISH, resp);
	}

	@CrossOrigin(origins = "*")
	@PostMapping
	public ResponseEntity<String> addWish(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= ADD WISH ============== ");
			LOGGER.info("PAYLOAD: " + requestParam);

			LocalUser localUser = getUserFromPrincipal(principal);

			Wish newWish;
			newWish = mainService.addWish(parseJsonToWish(ParseType.ADD, requestParam, localUser));

			// Предотвращение вываливания на пустых датах
			if (newWish.getCreationDate() == null) newWish.setCreationDate(new Date());
			if (newWish.getRealized() == null) newWish.setRealized(false);
			if (newWish.getRealizationDate() == null) newWish.setRealizationDate(new Date());

			String result = createGsonBuilder().toJson(newWish);
			LOGGER.info("PAYLOAD: " + result);

			return $prepareResponse(result);

		}, requestParam, null, OperationType.ADD_WISH, resp);
	}


	@CrossOrigin(origins = "*")
	@PostMapping("/www")
	public ResponseEntity<String> addWishWithPicture(Principal principal, @RequestBody WishWithPicture wishWithPicture, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= ADD WISH ============== ");
			LOGGER.info(wishWithPicture.getDecodedBase64String());
			wishWithPicture.base64StringToJpg();

		/*LOGGER.info("PAYLOAD: " + requestParam);

			LocalUser localUser = getUserFromPrincipal(principal);

			Wish newWish;
			newWish = mainService.addWish(parseJsonToWish(ParseType.ADD, requestParam, localUser));

			// Предотвращение вываливания на пустых датах
			if (newWish.getCreationDate() == null) newWish.setCreationDate(new Date());
			if (newWish.getRealized() == null) newWish.setRealized(false);
			if (newWish.getRealizationDate() == null) newWish.setRealizationDate(new Date());

			String result = createGsonBuilder().toJson(newWish);
			LOGGER.info("PAYLOAD: " + result);*/

			return null;

		}, wishWithPicture, null, OperationType.ADD_WISH, resp);
	}



	@CrossOrigin(origins = "*")
	@GetMapping("/summ")
	public ResponseEntity<String> getSumm(Principal principal, HttpServletResponse resp) {

		return $do(s -> {

			long localAverageImplementationTime = 0L;
			int days = 0;
			int implemetedSummAllTime = 0;
			int implemetedSummMonth = 0;
			int littleWishes = 0;

			LocalUser localUser = getUserFromPrincipal(principal);

			if (mainService.getAllRealizedWishes(localUser).isPresent()) {

				List<Long> realizedWishes = mainService.getAllRealizedWishes(localUser).get().stream()
						.filter(wf->wf.getRealizationDate()!=null && wf.getCreationDate()!=null )
						.map(w -> (w.getRealizationDate().getTime() - w.getCreationDate().getTime())).collect(Collectors.toList());
				Optional<Long> summ = realizedWishes.stream().reduce((l, r) -> l + r);
				if (summ.isPresent()) {
					localAverageImplementationTime = (summ.get()) / realizedWishes.size();
				}
				days = (int) (localAverageImplementationTime / (1000 * 60 * 60 * 24)); // Переводим в кол-во дней

				implemetedSummAllTime = mainService.getImplementedSum(localUser, 1).orElseGet(()->0);
				implemetedSummMonth = mainService.getImplementedSum(localUser, 2).orElseGet(()->0);


			}

			if (mainService.getLastSalary(localUser) != null) {
				String result = createGsonBuilder().toJson(SummEntity.builder()
						.all(mainService.getSumm4All(localUser))
						.allPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4All(localUser), localUser))
						.priorityPeriodForImplementation(mainService.calculateImplementationPeriod(mainService.getSumm4Prior(localUser), localUser))
						.lastSalary(mainService.getLastSalary(localUser).getResidualSalary())
						.averageImplementationTime(days)
						.implemetedSummAllTime(implemetedSummAllTime)
						.implemetedSummMonth(implemetedSummMonth)
						.priority(mainService.getSumm4Prior(localUser)).build());
				LOGGER.info("PAYLOAD: " + result);
				return $prepareResponse(result);
			} else {
				return $prepareNoDataYetErrorResponse(true);
			}
		}, null, principal, OperationType.GET_SUMS, resp);
	}


	@CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteWish(Principal principal, @PathVariable String id, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= DELETE WISH ============== ");
			LOGGER.info("ID: " + id);
			Wish wish = mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID));
			wish.setAc(true);
			mainService.updateWish(wish);
			return $prepareResponse(createGsonBuilder().toJson(ResponseStatusDTO.builder().okMessage("OK").status("OK").build()));
		}, null, null, null, resp);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/last")
	public ResponseEntity<String> getLastSalary(Principal principal, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= GET LAST SALARY ============== ");
			LOGGER.info("PRINCIPAL: " + principal.getName());

			LocalUser localUser = getUserFromPrincipal(principal);


			String result = createGsonBuilder().toJson(mainService.getLastSalary(localUser).getResidualSalary());
			return $prepareResponse(result);
		}, null, null, null, resp);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/salary")
	public ResponseEntity<String> addSalary(Principal principal, @RequestBody String requestParam, HttpServletResponse resp) {


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

		}, requestParam, null, null, resp);
	}


	@CrossOrigin(origins = "*")
	@PostMapping("/parsecsv")
	public ResponseEntity<String> parseCsv(Principal principal,
	                                       @RequestParam(required = false, value = "csvfile") MultipartFile csvFile,
	                                       HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("FILE: " + csvFile.getOriginalFilename());
			LocalUser localUser = getUserFromPrincipal(principal);
			LOGGER.info("PRINCIPAL: " + localUser.toString());

			String result = createGsonBuilder().toJson(mainService.parseCsv(csvFile, localUser));
			return $prepareResponse(result);

		}, null, null, null, resp);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/changepriority/{id}/{move}")
	public ResponseEntity<String> changePriority(Principal principal, @PathVariable String id, @PathVariable String move, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= MOVE WISH (CHANGE PRIORITY) ============== ");
			LOGGER.info("id: " + id);

			Wish wish = checkParametersAndGetWish(id, move);

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

			String result = createNullableGsonBuilder().toJson(wish);

			return $prepareResponse(result);

		}, null, null, null, resp);
	}

	private Wish checkParametersAndGetWish(String id, String move) throws BadIncomeParameter {

		if ((!"up".equals(move)) && (!"down".equals(move)))
			throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.PRIORITYCHANGE);
		if ((isBlank(id)) || (!Pattern.compile("^\\d*$").matcher(id).matches()))
			throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_ID);

		LOGGER.info("move: " + move);

		return mainService.getWishById(Integer.parseInt(id)).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/changemonth/{id}/{move}")
	public ResponseEntity<String> changeMonth(Principal principal, @PathVariable String id, @PathVariable String move, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= MOVE WISH (CHANGE MONTH) ============== ");
			LOGGER.info("id: " + id);
			LOGGER.info("move: " + move);
			LocalUser localUser = getUserFromPrincipal(principal);
			LOGGER.info("principal: " + localUser.getLogin());

			Wish wish = checkParametersAndGetWish(id, move);
			int maxPrior = (mainService.getMaxPriority(localUser)) - 1;
			LOGGER.info("max prior: " + maxPrior);

			switch (move) {
				case "down":

					if (maxPrior != 0) {
						if (wish.getPriorityGroup() < maxPrior + 1) {
							wish.setPriorityGroup(wish.getPriorityGroup() + 1);
						}
						LOGGER.info("move summary: " + (wish.getPriorityGroup() + 1));
						mainService.updateWish(wish);
						break;
					}

				case "up":

					if (maxPrior == 0) {
						LOGGER.info("max prior = 0. move summary: 1");
						wish.setPriorityGroup(1);
					} else {
						if (wish.getPriorityGroup() == null) {
							LOGGER.info("move summary: " + maxPrior);
							wish.setPriorityGroup(maxPrior);
						} else if (wish.getPriorityGroup() > 1) {
							LOGGER.info("move summary: " + (wish.getPriorityGroup() - 1));
							wish.setPriorityGroup(wish.getPriorityGroup() - 1);
						}
					}

					mainService.updateWish(wish);
					break;
			}

			Date currentDate = new Date();
			if (wish.getCreationDate() == null) wish.setCreationDate(currentDate);
			if (wish.getPriorityGroupOrder() == null) wish.setPriorityGroupOrder(1);

			String result = createNullableGsonBuilder().toJson(wish);

			return $prepareResponse(result);

		}, null, null, null, resp);
	}


	@CrossOrigin(origins = "*")
	@PostMapping("/users")
	public ResponseEntity<String> addUser(@RequestBody String user, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= ADD USER  ============== ");
			LOGGER.info("PAYLOAD: " + user);

			LocalUser newUser = parseJsonToUserAndValidate(user);
			newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));
			newUser.setViewMode("TABLE");

			if (usersRepo.findByLogin(newUser.getLogin()).isPresent()) {
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
			}
			usersRepo.save(newUser);
			return $prepareResponse(createGsonBuilder().toJson(newUser));

		}, null, null, null, resp);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(Principal principal, @PathVariable String id, HttpServletResponse resp) {


		return $do(s -> {

			LOGGER.info("========= DELETE USER  ============== ");
			LOGGER.info("PAYLOAD: " + id);

			if (!usersRepo.findById(Long.valueOf(id)).isPresent()) {
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_NO_EXIST);
			}

			usersRepo.deleteById(Long.valueOf(id));

			return $prepareResponse(createGsonBuilder().toJson(id));

		}, id, null, null, resp);
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/users/{id}")
	public ResponseEntity<String> editUser(Principal principal, @RequestBody String user, @PathVariable String id, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= EDIT USER  ============== ");
			LOGGER.info("PAYLOAD: " + user);
			LOGGER.info("id: " + id);

			LocalUser newUser = parseJsonToUserAndValidate(user);
			LocalUser localuser = getUserFromPrincipal(principal);
			newUser.setCreationDate(localuser.getCreationDate());

			if ((usersRepo.findByLogin(newUser.getLogin()).isPresent()) && (!localuser.getLogin().equals(newUser.getLogin()))) {
				throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.SUCH_USER_EXIST);
			}

			newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));
			localuser.setPwd(newUser.getPwd());
			localuser.setLogin(newUser.getLogin());
			localuser.setEmail(newUser.getEmail());
			localuser.setFullname(newUser.getFullname());

			fixNullUserFields(localuser);

			return $prepareResponse(createGsonBuilder().toJson(usersRepo.saveAndFlush(localuser)));

		}, user, null, OperationType.UPDATE_USER, resp);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/users/toggle/{mode}")
	public ResponseEntity<String> toggleUserMode(Principal principal, @PathVariable String mode, HttpServletResponse resp) {

		return $do(s -> {

			LOGGER.info("========= TOGGLE / GET USER MODE ============== ");
			LOGGER.info("MODE: " + mode);
			LocalUser localuser = getUserFromPrincipal(principal);

			if (("TABLE".equals(mode)) || ("TREE".equals(mode))) {
				localuser.setViewMode(mode);
				return $prepareResponse(createGsonBuilder().toJson(usersRepo.saveAndFlush(localuser)));
			} else {
				return $prepareResponse(createGsonBuilder().toJson(localuser));
			}

		}, null, null, OperationType.TOGGLE_USER_MODE, resp);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/users/list")
	public ResponseEntity<String> getAllUsers(Principal principal, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= GET ALL USERS  ============== ");

			List<LocalUser> userList = usersRepo.findAll().stream().map(u -> {
				if (u.getCreationDate() == null) u.setCreationDate(new Date());
				return u;
			}).collect(Collectors.toList());

			return $prepareResponse(createGsonBuilder().toJson(userList));
		}, null, null, null, resp);
	}

	private void fixNullUserFields(LocalUser localUser) {
		// Проверяем на заполненность пользовательских данных, чтобы не отваливались эксепшены:
		if (localUser.getUserRole() == null) localUser.setUserRole(LocalUser.Role.USER);
		if (localUser.getUserCryptoMode() == null) localUser.setUserCryptoMode(false);
		if (localUser.getCreationDate() == null) localUser.setCreationDate(new Date());
		if (localUser.getEmail() == null) localUser.setEmail("antonr0manov@yndex.ru");
		if (localUser.getFullname() == null) localUser.setFullname("Имя неизвестно");
		if (localUser.getViewMode() == null) localUser.setViewMode("TABLE");
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/users/getcurrent")
	public ResponseEntity<String> getCurrentUser(Principal principal, HttpServletResponse resp) {

		return $do(s -> {

			LocalUser localUser = getUserFromPrincipal(principal);
			// Проверяем на заполненность пользовательских данных, чтобы не отваливались эксепшены:
			fixNullUserFields(localUser);
			return $prepareResponse(createGsonBuilder().toJson(localUser));
		}, null, null, OperationType.GET_CURRENT_USER, resp);
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
	public ResponseEntity<String> returnUserPassword(Principal principal, @RequestParam(name = "email") String email, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= FORGET PWD METHOD =============== ");
			LOGGER.info("USER EMAIL - " + email);
			try {
				LocalUser localUser = usersRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
				return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, email).getStatus()));
			} catch (UserNotFoundException e) {
				return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
			}
		}, null, null, null, resp);
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
	public ResponseEntity<String> resetUserPasswordByAdmin(Principal principal, @PathVariable String id, HttpServletResponse resp) {

		return $do(s -> {
			LOGGER.info("========= RESET USER PWD =============== ");
			LOGGER.info("USER ID - " + id);
			try {
				LocalUser localUser = usersRepo.findById(Long.parseLong(id)).orElseThrow(UserNotFoundException::new);
				return $prepareResponse(createGsonBuilder().toJson(changePwd(localUser, localUser.getEmail()).getStatus()));
			} catch (UserNotFoundException e) {
				return $prepareBadResponse(createGsonBuilder().toJson("No such user!"));
			}
		}, null, null, null, resp);
	}


	/**
	 * Смена pwd и отправка уведомления на почту.
	 *
	 * @param user
	 * @param email
	 * @return
	 */
	private EmailStatus changePwd(LocalUser user, String email) {

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
