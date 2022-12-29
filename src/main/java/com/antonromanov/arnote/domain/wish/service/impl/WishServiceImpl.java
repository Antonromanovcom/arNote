package com.antonromanov.arnote.domain.wish.service.impl;

import com.antonromanov.arnote.domain.salary.service.SalaryService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rq.ChangePriorityRq;
import com.antonromanov.arnote.domain.wish.dto.rq.ChangeTargetMonthRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishTransferRq;
import com.antonromanov.arnote.domain.wish.dto.rs.*;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.domain.wish.mapper.WishMapper;
import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.old.exceptions.NoDataYetException;
import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.old.model.ArNoteUser;
import com.antonromanov.arnote.domain.wish.entity.Wish;
import com.antonromanov.arnote.old.repositoty.WishRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.domain.wish.enums.SortMode.searchByUiValue;
import static com.antonromanov.arnote.old.utils.ArNoteUtils.*;
import static com.antonromanov.arnote.old.utils.Utils.wishLifeTime;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final WishMapper mapper;
    private final UserService userService;
    private final SalaryService salaryService;

    /**
     * Берем максимальный priorityGroup, добавляем +1 и возвращаем.
     *
     * @param user
     * @return
     */
    public int getMaxPriority(ArNoteUser user) {
        List<Wish> wishDTOList = wishRepository.getAllWithGroupOrder(user);

        return wishDTOList.stream()
                .filter(wish -> wish.getPriorityGroup() != null)
                .max(Comparator.comparing(Wish::getPriorityGroup))
                .map(e -> e.getPriorityGroup() + 1)
                .orElse(1);
    }

    /**
     * Получить все желания с помесячной группировкой и детализованным наполнением.
     */
    //todo: перевести в private и тестирование делать по этой схеме - https://www.baeldung.com/java-unit-test-private-methods
    public List<ListOfGroupedWishesRs> getAllWishesWithGroupPriority(ArNoteUser user) {

        int maxPrior = getMaxPriority(user);
        List<ListOfGroupedWishesRs> gropedWishList = new ArrayList<>();

        if (maxPrior > 1) { // есть задачи с приоритетами
            int currentMonth = 1;
            int amountForAllMonths = 0; // набегающий баланс

            while (currentMonth < maxPrior) {

                int finalCurrentMonth = currentMonth;

                List<GroupedWishRs> wishList = prepareGroupedWishList(user,
                        ((((Predicate<Wish>) w -> w.getPriorityGroup() != null)
                                .and(w -> w.getPriorityGroup() == finalCurrentMonth))));

                amountForAllMonths = (salaryService.getLastSalary(user) - (calculateSum(wishList))) + amountForAllMonths; //считаем набегающий баланс
                gropedWishList.add(buildGroupedWish(wishList, currentMonth, user, computerMonthNumber(currentMonth),
                        amountForAllMonths));
                currentMonth++;
            }
        }
        List<GroupedWishRs> wishList = prepareGroupedWishList(user, w -> w.getPriorityGroup() == null);
        int monthNumber = computerMonthNumber(maxPrior + 1);
        if (!wishList.isEmpty()) {
            gropedWishList.add(buildGroupedWish(wishList, maxPrior, user, monthNumber, 0));
        }
        return gropedWishList;
    }

    private ListOfGroupedWishesRs buildGroupedWish(List<GroupedWishRs> list, int currentMonth, ArNoteUser user,
                                                   int monthNumber, int balance) {

        return ListOfGroupedWishesRs.builder()
                .wishes(list)
                .monthNumber(monthNumber)
                .monthName(computerMonthName(currentMonth))
                .year(String.valueOf(getCurrentYear(currentMonth)))
                .colspan(2) //todo: в константы
                .sum(calculateSum(list))
                .overflow((list.stream().map(GroupedWishRs::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck)) > salaryService.getLastSalary(user))
                .colorClass(getClassColorByMonth(0, (list.stream().map(GroupedWishRs::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck)) > salaryService.getLastSalary(user)))
                .expanded(true)
                .balance(balance)
                .build();
    }


    private int calculateSum(List<GroupedWishRs> list) {
        return list.stream().map(GroupedWishRs::getPrice).reduce(0, ArithmeticUtils::addAndCheck);
    }


    private List<GroupedWishRs> prepareGroupedWishList(ArNoteUser user, Predicate<Wish> filterPredicate) {
        return wishRepository.getAllWithGroupOrder(user)
                .stream()
                .filter(filterPredicate)
                .sorted(user.getSortMode().getWishComparator())
                .map(mapper::mapWishForGroupedList)
                .collect(Collectors.toList());
    }

    /**
     * Список желаний.
     *
     * @return
     */
    @Override
    public WishListRs getAllWishesByUserId(String filter, String sort) {
        ArNoteUser user = userService.getUserFromPrincipal();
        user = userService.checkAndSaveUserSettings(user, new HashMap<UserSettingType, String>() {{
            put(UserSettingType.FILTER, filter);
            put(UserSettingType.SORT, sort); //todo: считаю это нужно вынести в отдельный метод
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
     * @return
     */
    @Override
    public WishListRs findWishesByName(String name) {

        ArNoteUser user = userService.getUserFromPrincipal();
        List<Wish> resultList = wishRepository.findAllByUser(user).stream()
                .filter(w -> ((w.getRealized() == null || !w.getRealized()) && (w.getArchive() == null || !w.getArchive())))
                .filter(notArchivedWish -> notArchivedWish.getWishName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return WishListRs.builder()
                .list(mapper.mapWishList(resultList))
                .build();
    }

    @Override
    public WishRs addWish(WishRq wish) {
        ArNoteUser user = userService.getUserFromPrincipal();
        Wish wishAsDBEntity = mapper.map(wish);
        wishAsDBEntity.setUser(user);
        wishAsDBEntity.setCreationDate(new Date());
        return mapper.mapWish(wishRepository.saveAndFlush(wishAsDBEntity));
    }

    @Override
    public WishRs updateWish(WishRq newWish) {
        ArNoteUser user = userService.getUserFromPrincipal();
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
    public WishAnalyticsRs getWishAnalytics() {

        ArNoteUser user = userService.getUserFromPrincipal();
        List<Wish> realizedWishList = wishRepository.getAllRealizedWishes(user);
        Optional<Salary> salary = salaryService.getLastSalaryListByUserDesc(user);
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
    public GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, String sortType) {

        ArNoteUser user = userService.getUserFromPrincipal();
        if (wishRepository.findAllByUser(user).isEmpty()) {
            throw new NoDataYetException(ErrorCodes.ERR_O1);
        } else {

            user = userService.checkAndSaveUserSettings(user, new HashMap<UserSettingType, String>() {{
                put(UserSettingType.SORT, isBlank(sortType) ? SortMode.ALL.name() : searchByUiValue(sortType).name());
            }});

            return GroupedMonthListRs.builder()
                    .months(getAllWishesWithGroupPriority(user))
                    .build();
        }
    }

    @Override
    public WishRs transferWish(WishTransferRq request) {
        return wishRepository.findById(request.getId())
                .map(w -> {
                    w.setPriorityGroup(parseMonthAndCalculatePriority(request.getMonthAndYear()));
                    return mapper.mapWish(wishRepository.saveAndFlush(w));
                })
                .orElseThrow(() -> new BadIncomeParameter(ErrorCodes.ERR_10));
    }

    @Override
    public WishRs oneStepChangePriority(ChangePriorityRq request) {
        return wishRepository.findById(request.getId())
                .map(w -> mapper.mapWish(wishRepository.saveAndFlush(request.getType().act(w, this))))
                .orElseThrow(() -> new BadIncomeParameter(ErrorCodes.ERR_10));
    }

    @Override
    public WishRs oneStepChangeTargetMonth(ChangeTargetMonthRq request) {
        return wishRepository.findById(request.getId())
                .map(w -> mapper.mapWish(wishRepository.saveAndFlush(request.getType().act(w, this))))
                .orElseThrow(() -> new BadIncomeParameter(ErrorCodes.ERR_10));
    }
}