package com.antonromanov.arnote.domain.wish.service.impl;

import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.*;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.domain.wish.mapper.WishMapper;
import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.sex.entity.common.Salary;
import com.antonromanov.arnote.sex.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.sex.exceptions.NoDataYetException;
import com.antonromanov.arnote.sex.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.sex.model.wish.Wish;
import com.antonromanov.arnote.sex.repositoty.SalaryRepository;
import com.antonromanov.arnote.sex.repositoty.WishRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.sex.utils.ArNoteUtils.*;
import static com.antonromanov.arnote.sex.utils.Utils.wishLifeTime;

@Service
@AllArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final WishMapper mapper;
    private final UserService userService;
    private final SalaryRepository salaryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

/*
    Integer addCount = 0;  //todo: почему это здесь???? (// Количество добавлений)

    @Override
    public List<Wish> getAllWishesAndUpdateUser(LocalUser user, FilterMode filterType, SortMode sortType)  {

        if (filterType == FilterMode.DEFAULT) {
            filterType = (user.getFilterMode() != null) ? user.getFilterMode() : FilterMode.ALL;
        }

        if ((sortType == SortMode.DEFAULT)) {
            sortType = (user.getTableSortMode() != null) ? user.getTableSortMode() : SortMode.ALL;
        }

        user.setTableSortMode(sortType);
        user.setFilterMode(filterType);
        usersRepo.saveAndFlush(user);

        return  wishRepository.findAllByIdSorted(user)
                .stream()
                .filter(filterType.getFilterPredicate())
                .sorted(sortType.getWishComparator())
                .collect(Collectors.toList());
    }

    @Override
    public List<Wish> getAllWishesWithPriority1(ArNoteUser user) {
        return wishRepository.getAllWithPriority1(user);
    }

    @Override
    public List<Wish> getAl() {
        return wishRepository.getAll();
    }

    */

    /**
     * Берем максимальный priorityGroup, добавляем +1 и возвращаем.
     *
     * @param user
     * @return
     */
    private int getMaxPriority(ArNoteUser user) {
        List<Wish> wishDTOList = wishRepository.getAllWithGroupOrder(user);

        return wishDTOList.stream()
                .filter(wish -> wish.getPriorityGroup() != null)
                .max(Comparator.comparing(Wish::getPriorityGroup))
                .map(e -> e.getPriorityGroup() + 1)
                .orElse(1);
    }

    /*private void addItemInWishDTOListForNullPriorityWishes(List<GroupOfWishesForOneMonth> wishDTOListGlobal,
                                                           List<WishRs> wishDTOListFiltered,
                                                           int maxPrior,
                                                           ArNoteUser user) {
        wishDTOListGlobal.add(WishDTOList.builder()
                .wishList(wishDTOListFiltered)
                .monthNumber(computerMonthNumber(maxPrior + 1 > 12 ? (maxPrior + 1 - 12) : maxPrior + 1))
                .monthName(computerMonth(maxPrior))
                .year(String.valueOf(getCurrentYear(maxPrior)))
                .colspan(2)
                .sum(wishDTOListFiltered.stream().map(WishRs::getPrice).reduce(0, ArithmeticUtils::addAndCheck))
                .overflow((wishDTOListFiltered.stream().map(WishRs::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user).getResidualSalary())
                .colorClass(getClassColorByMonth(0, (wishDTOListFiltered.stream().map(WishRs::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user).getResidualSalary()))
                .expanded(true)
                .build());
    }*/



    /**
     * Получить все желания с помесячной группировкой и детализованным наполнением.
     */
    @Override
    public List<ListOfGroupedWishesRs> getAllWishesWithGroupPriority(ArNoteUser user) { //todo: надо сесть и подумать как бы все это получше отрефакторить

        //  if (!wishRepository.getAllWithGroupOrder(user).isEmpty()) {
        int maxPrior = getMaxPriority(user);
        List<ListOfGroupedWishesRs> wishDTOListGlobal = new ArrayList<>();

      //  if (maxPrior - 1 > 0) { // есть задачи с приоритетами
            /*int currentMonth = 1;
            Integer amountForAllMonths = 0; // набегающий баланс

            while (currentMonth < maxPrior) {

                int finalCurrentMonth = currentMonth;
                List<WishRs> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                        .stream()
                        .filter(wish -> wish.getPriorityGroup() != null)
                        .filter(wish -> wish.getPriorityGroup() == finalCurrentMonth)
                        .map(w -> prepareWishDTO(w, maxPrior))
                        .collect(Collectors.toList());

                Integer sum = wishDTOListFiltered.stream().map(WishDTO::getPrice).reduce(0, ArithmeticUtils::addAndCheck);
                amountForAllMonths = (getLastSalary(user).getResidualSalary() - sum) + amountForAllMonths; //считаем набегающий баланс

                wishDTOListGlobal.add(GroupOfWishesForOneMonth.builder()
                        .wishList(wishDTOListFiltered)
                        .monthNumber(computerMonthNumber(currentMonth))
                        .monthName(computerMonth(currentMonth))
                        .year(String.valueOf(getCurrentYear(currentMonth)))
                        .colspan(2)
                        .sum(sum)
                        .overflow((wishDTOListFiltered.stream().map(WishRs::getPrice)
                                .reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user).getResidualSalary())
                        .colorClass(getClassColorByMonth(computerMonthNumber(currentMonth), (wishDTOListFiltered.stream()
                                .map(WishRs::getPrice).reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user)
                                .getResidualSalary()))
                        .expanded(true)
                        .balance(amountForAllMonths)
                        .build());

                currentMonth++;
            }

            List<WishRs> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                    .stream()
                    .filter(wish -> wish.getPriorityGroup() == null)
                    .map(w -> prepareWishDTO(w, maxPrior))
                    .collect(Collectors.toList());

            addItemInWishDTOListForNullPriorityWishes(wishDTOListGlobal, wishDTOListFiltered, maxPrior, user);*/

     //   } else { // есть задачи, но у них у всех приоритеты не проставлены (месячная сортировка)

            List<GroupedWishRs> wishList = wishRepository.getAllWithGroupOrder(user)
                    .stream()
                    .map(w -> mapper.mapWishForGroupedList(w, maxPrior))
                    .collect(Collectors.toList());

            wishDTOListGlobal.add(ListOfGroupedWishesRs.builder() //todo: в отдельный метод
                    .wishList(wishList)
                    .monthNumber(computerMonthNumber(maxPrior + 1 > 12 ? (maxPrior + 1 - 12) : maxPrior + 1)) //todo: вот с этим +1 надо тоже разобраться.
                    .monthName(computerMonthName(maxPrior))
                    .year(String.valueOf(getCurrentYear(maxPrior)))
                    .colspan(2) //todo: в константы
                    .sum(wishList.stream().map(GroupedWishRs::getPrice).reduce(0, ArithmeticUtils::addAndCheck))
                    .overflow((wishList.stream().map(GroupedWishRs::getPrice)
                            .reduce(0, ArithmeticUtils::addAndCheck)) >
                            salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst()
                                    .map(Salary::getResidualSalary).orElse(0)) //todo: все-таки в отдельный метод
                    .colorClass(getClassColorByMonth(0, (wishList.stream().map(GroupedWishRs::getPrice)
                            .reduce(0, ArithmeticUtils::addAndCheck)) >
                            salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst()
                                    .map(Salary::getResidualSalary).orElse(0)))
                    .expanded(true)
                    .build());
     //   }

        wishDTOListGlobal.forEach(wl -> wl.getWishList().sort(checkTreeViewModeForUpdate(user, sortType))); // сортируем
        return wishDTOListGlobal;
        /*} else {
            return Optional.empty();
        }*/
    }


    /**
     * Проверить нужно ли менять тип сортировки для древовидного представления данных и если не нужно (sortType = DEFAULT),
     * то проверяется, что выставлено у пользователя в качестве предыдущего типа сортировки.
     *
     * @return
     */
    private Comparator<WishRs> checkTreeViewModeForUpdate(ArNoteUser user, SortMode sortType) {
        if ((sortType == SortMode.DEFAULT)) { //todo: надо смотреть что в ДЕВе сейчас (в main ????)
            sortType = (user.getTableSortMode() != null) ? user.getTreeSortMode() : SortMode.ALL;
        } else {
            user.setTreeSortMode(sortType);
            usersRepo.saveAndFlush(user);
        }
        return sortType.getWishResponseComparator();
    }

    @Override
    public SalaryRs addSalary(SalaryRq request, Principal principal) {
        ArNoteUser user = userService.getUserFromPrincipal(principal);
        return mapper.mapSalaryRs(salaryRepository.saveAndFlush(mapper.mapSalaryRq(request, user)));
    }

    /**
     * Список желаний.
     *
     * @param principal - пользак.
     * @return
     */
    @Override
    public WishListRs getAllWishesByUserId(Principal principal, String filter, String sort) {

        ArNoteUser user = userService.getUserFromPrincipal(principal);
        user = userService.checkAndSaveUserSettings(user, new HashMap<UserSettingType, String>() {{
            put(UserSettingType.FILTER, filter);
            put(UserSettingType.SORT, sort);
        }});

        List<Wish> resultList = wishRepository.findAllSortedByPriority(user).stream()
                .filter(user.getFilterMode().getFilterPredicate())
                .sorted(user.getSortMode().getWishComparator())
                .collect(Collectors.toList());

        if (resultList.size() < 1) {
            throw new NoDataYetException(ErrorCodes.ERR_O1);
        }

        return WishListRs.builder()
                .list(mapper.mapWishList(resultList))
                .build();
    }

    /**
     * Поиск желаний по имени.
     *
     * @param name
     * @param principal
     * @return
     */
    @Override
    public WishListRs findWishesByName(String name, Principal principal) {

        ArNoteUser user = userService.getUserFromPrincipal(principal);
        List<Wish> resultList = wishRepository.findAllByUser(user).stream()
                .filter(w -> ((w.getRealized() == null || !w.getRealized()) && (w.getArchive() == null || !w.getArchive())))
                .filter(notArchivedWish -> notArchivedWish.getWishName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return WishListRs.builder()
                .list(mapper.mapWishList(resultList))
                .build();
    }

    @Override
    public WishRs addWish(WishRq wish, Principal principal) {
        ArNoteUser user = userService.getUserFromPrincipal(principal);
        Wish wishAsDBEntity = mapper.map(wish);
        wishAsDBEntity.setUser(user);
        wishAsDBEntity.setCreationDate(new Date());
        return mapper.mapWish(wishRepository.saveAndFlush(wishAsDBEntity));
    }


    @Override
    public WishRs updateWish(Principal principal, WishRq newWish) {
        ArNoteUser user = userService.getUserFromPrincipal(principal);
        Wish wishAsDBEntity = mapper.map(newWish);
        wishAsDBEntity.setUser(user);
        return mapper.mapWish(wishRepository.saveAndFlush(wishAsDBEntity));
    }

    @Override
    public WishRs deleteWish(String id) {
        Wish wish = wishRepository.findById(Long.parseLong(id)).orElseThrow(() -> new BadIncomeParameter(ErrorCodes.ERR_10));
        wish.setArchive(true);
        return mapper.mapWish(wishRepository.saveAndFlush(wish));
    }

    @Override
    public WishAnalyticsRs getWishAnalytics(Principal principal) {

        ArNoteUser user = userService.getUserFromPrincipal(principal);
        List<Wish> realizedWishList = wishRepository.getAllRealizedWishes(user);
        Optional<Salary> salary = salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst();
        Integer sumOfAllWishes = wishRepository.findAllSortedByPriority(user).stream().map(Wish::getPrice).reduce(0,
                ArithmeticUtils::addAndCheck);
        Integer sumOfPriorityWishes = wishRepository.getPriorityWishes(user).stream().map(Wish::getPrice).reduce(0,
                ArithmeticUtils::addAndCheck);

        List<Long> averagePeriodsOfRealizationList = realizedWishList.stream()
                .filter(wf -> wf.getRealizationDate() != null && wf.getCreationDate() != null)
                .map(w -> wishLifeTime(w.getCreationDate(), w.getRealizationDate()))
                .collect(Collectors.toList());

        if (!salary.isPresent()) {
            throw new NoDataYetException(ErrorCodes.ERR_O2);
        }

        return WishAnalyticsRs.builder()
                .sumOfAllWishes(wishRepository.findAllSortedByPriority(user).stream().map(Wish::getPrice).reduce(0,
                        ArithmeticUtils::addAndCheck))
                .timeForRealizationAllWishes(salary.map(s -> sumOfAllWishes / s.getResidualSalary()).orElse(0))
                .priorityPeriodForImplementation(salary.map(s -> sumOfPriorityWishes / s.getResidualSalary()).orElse(0))
                .lastSalary(salary.map(Salary::getResidualSalary).orElse(0))
                .sumOfPriorityWishes(sumOfPriorityWishes)
                .averageImplementationTime(averagePeriodsOfRealizationList.stream().mapToDouble(Long::doubleValue)
                        .average()
                        .orElse(0))
                .sumOfImplementedWishesFromTheBeginning(wishRepository.getSumOfRealizedWishesForWholePeriod(user.getId()).orElse(0))
                .sumOfImplementedWishesForCurrentMonth(wishRepository.getSumOfRealizedWishesForCurrentMonth(user.getId()).orElse(0))
                .build();
    }

    @Override
    public LocalUserRs getCurrentUser(Principal principal) {
        return mapper.mapArnoteUser(userService.getUserFromPrincipal(principal)); //todo: убрать из маппера и перенести в другой
    }

    @Override
    public LocalUserRs toggleUserMode(Principal principal, ToggleUserModeRq mode) {
        ArNoteUser user = userService.getUserFromPrincipal(principal);
        user.setViewMode(mode.getUserViewMode().name()); // todo: в энтити поменять на ENUM
        return mapper.mapArnoteUser(userService.saveUser(user)); //todo: убрать из маппера и перенести в другой
    }

    @Override
    public LocalUserRs addUser(LocalUserRq request) {

        if (userService.findByLogin(request.getLogin()).isPresent()) {
            throw new BadIncomeParameter(ErrorCodes.ERR_11);
        } else {
            ArNoteUser newUser = mapper.mapLocalUserRq(request, passwordEncoder.encode(request.getPwd()));
            return mapper.mapArnoteUser(userService.saveUser(newUser)); //todo: убрать из маппера и перенести в другой
        }
    }

    @Override
    public GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, String sortType) {

        List<ListOfGroupedWishesRs> wishListWithMonthOrder;

           /* ArNoteUser localUser = getUserFromPrincipal(principal);

            if (mainService.getAllWishesByUserId(localUser).size() > 0) {*/

        //   GroupedByMonthWishListRs dtOwithOrder = new GroupedByMonthWishListRs(); //todo: билдер
        //   String result = "";
        //   String finalSortType = sortType; //todo: очень не красивое решение - надо что-то с этим делать.
        wishListWithMonthOrder = mainService.getAllWishesWithGroupPriority(localUser);

        dtOwithOrder.list.addAll(wishListWithMonthOrder); //todo: почему .list, а не getlist() ????

        if (("all".equalsIgnoreCase(finalSortType))
                && (localUser.getSortMode() != SortMode.ALL)
                && (localUser.getSortMode() != null)) { //todo: проверяем не сохранен ли до этого режим отображения и если сохранен - выбираем его. Но  вообще это костылище и код не красивый - надо разбираться с этим
            finalSortType = localUser.getSortMode().getUiValue();
        }

        if ("name".equalsIgnoreCase(finalSortType)) { // todo: обработать какой-нить мапой ИФы и сделать все в функциональном стиле
            dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(Comparator.comparing(GroupedWishRs::getWish))); //todo: почему .list, а не getlist() ????
            localUser.setSortMode(SortMode.NAME);
            usersRepo.saveAndFlush(localUser);
        } else if ("price-asc".equalsIgnoreCase(finalSortType)) {
            dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(Comparator.comparing(GroupedWishRs::getPrice)));
            localUser.setSortMode(SortMode.PRICE_ASC);
            usersRepo.saveAndFlush(localUser);
        } else if ("all".equalsIgnoreCase(finalSortType)) {
            localUser.setSortMode(SortMode.ALL);
            usersRepo.saveAndFlush(localUser);
        } else if ("price-desc".equalsIgnoreCase(finalSortType)) {
            Comparator<GroupedWishRs> comparator = Comparator.comparing(GroupedWishRs::getPrice);
            dtOwithOrder.list.forEach(wl -> wl.getWishList().sort(comparator.reversed()));
            localUser.setSortMode(SortMode.PRICE_DESC);
            usersRepo.saveAndFlush(localUser);
        }

        result = createNullableGsonBuilder().toJson(dtOwithOrder);

        return $prepareResponse(result);
            /*} else {
                return $prepareNoDataYetErrorResponse(ErrorCodes.ERR_O1);
            }*/

    }

  /*  @Override
    public Wish updateMonthGroup(Wish wish) throws BadIncomeParameter {
        Wish searchedWish = wishRepository.findById(wish.getId()).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
        wish.setPriorityGroup(searchedWish.getPriorityGroup());
        return wish;
    }

    @Override
    public Integer getSumm4Prior(ArNoteUser user) {
    public Integer getSumForPriorityWishes(LocalUser user) {
        return wishRepository.getAllWithPriority1(user).stream().map(Wish::getPrice).reduce(0, ArithmeticUtils::addAndCheck);
    }

    @Override
    public Optional<Integer> getImplementedSum(ArNoteUser user, int period) {
        if (period == 1) {
            return wishRepository.getImplementedSum4AllPeriod(user.getId());
        } else {
            return wishRepository.getImplementedSum4Month(user.getId());
        }
    }
}
