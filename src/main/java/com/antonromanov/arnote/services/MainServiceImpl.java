package com.antonromanov.arnote.services;

import au.com.bytecode.opencsv.CSVReader;
import com.antonromanov.arnote.entity.common.Salary;
import com.antonromanov.arnote.dto.response.ResponseParseResult;
import com.antonromanov.arnote.dto.response.WishResponse;
import com.antonromanov.arnote.dto.response.monthgroupping.GroupOfWishesForOneMonth;
import com.antonromanov.arnote.entity.LocalUser;
import com.antonromanov.arnote.entity.Salary;
import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.enums.FilterMode;
import com.antonromanov.arnote.enums.SortMode;
import com.antonromanov.arnote.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.model.*;
import com.antonromanov.arnote.model.wish.*;
import com.antonromanov.arnote.repositoty.SalaryRepository;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.repositoty.WishRepository;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.utils.ArNoteUtils.*;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UsersRepo usersRepo;


    Integer addCount = 0;  //todo: почему это здесь???? (// Количество добавлений)

    @Override
    public List<Wish> getAllWishesWithPriority(LocalUser user) {
        return wishRepository.getAllWithPriority1(user); //todo: переименовать
    }

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

    /**
     * Берем максимальный priorityGroup, добавляем +1 и возвращаем.
     *
     * @param user
     * @return
     */
    @Override
    public int getMaxPriority(ArNoteUser user) { //todo: описание!!!!!
        List<Wish> wishDTOList = wishRepository.getAllWithGroupOrder(user);
        Comparator<Wish> comparator = Comparator.comparing(Wish::getPriorityGroup);

        return wishDTOList.stream().filter(wish -> wish.getPriorityGroup() != null)
                .max(comparator)
                .map(e -> e.getPriorityGroup() + 1)
                .orElse(1);

    }

    private void addItemInWishDTOListForNullPriorityWishes(List<GroupOfWishesForOneMonth> wishDTOListGlobal,
                                                           List<WishResponse> wishDTOListFiltered,
                                                           int maxPrior,
                                                           ArNoteUser user) {
        wishDTOListGlobal.add(WishDTOList.builder()
                .wishList(wishDTOListFiltered)
                .monthNumber(computerMonthNumber(maxPrior + 1 > 12 ? (maxPrior + 1 - 12) : maxPrior + 1))
                .monthName(computerMonth(maxPrior))
                .year(String.valueOf(getCurrentYear(maxPrior)))
                .colspan(2)
                .sum(wishDTOListFiltered.stream().map(WishResponse::getPrice).reduce(0, ArithmeticUtils::addAndCheck))
                .overflow((wishDTOListFiltered.stream().map(WishResponse::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user).getResidualSalary())
                .colorClass(getClassColorByMonth(0, (wishDTOListFiltered.stream().map(WishResponse::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user).getResidualSalary()))
                .expanded(true)
                .build());
    }

    /**
     * Получить все желания с помесячной группировкой и детализованным наполнением.
     */
    @Override
    public List<WishDTOList> getAllWishesWithGroupPriority(ArNoteUser user) {

        if (!wishRepository.getAllWithGroupOrder(user).isEmpty()) {
            int maxPrior = getMaxPriority(user);
            List<GroupOfWishesForOneMonth> wishDTOListGlobal = new ArrayList<>();

        if (maxPrior - 1 > 0) { // есть задачи с приоритетами
            int currentMonth = 1;
            Integer amountForAllMonths = 0; // набегающий баланс

            while (currentMonth < maxPrior) {

                    int finalCurrentMonth = currentMonth;
                    List<WishResponse> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
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
                            .overflow((wishDTOListFiltered.stream().map(WishResponse::getPrice)
                                    .reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user).getResidualSalary())
                            .colorClass(getClassColorByMonth(computerMonthNumber(currentMonth), (wishDTOListFiltered.stream()
                                    .map(WishResponse::getPrice).reduce(0, ArithmeticUtils::addAndCheck)) > getLastSalary(user)
                                    .getResidualSalary()))
                            .expanded(true)
                            .balance(amountForAllMonths)
                            .build());

                    currentMonth++;
                }

                List<WishResponse> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                        .stream()
                        .filter(wish -> wish.getPriorityGroup() == null)
                        .map(w -> prepareWishDTO(w, maxPrior))
                        .collect(Collectors.toList());

                addItemInWishDTOListForNullPriorityWishes(wishDTOListGlobal, wishDTOListFiltered, maxPrior, user);

            } else {

                List<WishResponse> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                        .stream()
                        .map(w -> prepareWishDTO(w, maxPrior))
                        .collect(Collectors.toList());

                addItemInWishDTOListForNullPriorityWishes(wishDTOListGlobal, wishDTOListFiltered, maxPrior, user);
            }

            wishDTOListGlobal.forEach(wl -> wl.getWishList().sort(checkTreeViewModeForUpdate(user,  sortType))); // сортируем
            return Optional.of(wishDTOListGlobal);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Проверить нужно ли менять тип сортировки для древовидного представления данных и если не нужно (sortType = DEFAULT),
     * то проверяется, что выставлено у пользователя в качестве предыдущего типа сортировки.
     *
     * @param user
     * @param sortType
     * @return
     */
    private Comparator<WishResponse> checkTreeViewModeForUpdate(LocalUser user, SortMode sortType) {
        if ((sortType == SortMode.DEFAULT)) {
            sortType = (user.getTableSortMode() != null) ? user.getTreeSortMode() : SortMode.ALL;
        } else {
            user.setTreeSortMode(sortType);
            usersRepo.saveAndFlush(user);
        }
        return sortType.getWishResponseComparator();
    }

    @Override
    public List<Wish> getAllWishesByUserId(ArNoteUser user) {
        return wishRepository.findAllByIdSorted(user);
    }

    /**
     * Поиск желаний по имени.
     *
     * @param request
     * @param user
     * @return
     */
    @Override
    public List<Wish> findAllWishesByWishName(SearchRq request, ArNoteUser user) {

        return wishRepository.findAllByUser(user).stream()
                .filter(w -> ((w.getRealized() == null || !w.getRealized()) && (w.getAc() == null || !w.getAc())))
                .filter(notArchivedWish -> notArchivedWish.getWish().toLowerCase().contains(request.getWishName().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<List<Wish>> getAllRealizedWishes(ArNoteUser user) {
        return Optional.of(wishRepository.getAllRealizedWishes(user));
    }

    @Override
    public Wish updateMonthGroup(Wish wish) throws BadIncomeParameter {
        Wish searchedWish = wishRepository.findById(wish.getId()).orElseThrow(() -> new BadIncomeParameter(BadIncomeParameter.ParameterKind.WISH_ID_SEARCH));
        wish.setPriorityGroup(searchedWish.getPriorityGroup());
        return wish;
    }

    @Override
    public Wish saveWish(Wish wish) {
        return wishRepository.saveAndFlush(wish);
    }

    @Override
    public Wish updateWish(Wish wish) {
        return wishRepository.saveAndFlush(wish);
    }

    @Override
    public Wish updateAndFlushWish(Wish wish) {
        return wishRepository.saveAndFlush(wish);
    }

    @Override
    public Wish addWish(Wish wish) {
        return wishRepository.saveAndFlush(wish);
    }

    @Override
    public Optional<Wish> getWishById(long id) {
        return wishRepository.findById(id);
    }

    @Override
    public Integer getSumForAllWishes(LocalUser user) {
    public Integer getSumm4All(ArNoteUser user) {
        return wishRepository.findAllByIdSorted(user).stream().map(Wish::getPrice).reduce(0, ArithmeticUtils::addAndCheck);
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

    @Override
    public Salary saveSalary(Salary salary) {
        return salaryRepository.saveAndFlush(salary);
    }

    @Override
    public Salary getLastSalary(ArNoteUser user) {
        return (salaryRepository.getLastSalaryListByUserDesc(user)).size() < 1 ? null :
                (salaryRepository.getLastSalaryListByUserDesc(user)).get(0);
    }

    @Override
    public Integer calculateImplementationPeriod(Integer summ, ArNoteUser ArNoteUser) {
        return summ / (getLastSalary(ArNoteUser).getResidualSalary());
    }


    @Override
    public ResponseParseResult parseCsv(MultipartFile file, ArNoteUser user) throws IOException {

        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), "UTF-8"),
                ',', '"', 1);
        List<String[]> allRows = reader.readAll();
        Pattern pattern = Pattern.compile("^\\d{1,3}\\,");

        allRows.stream()
                .map(strings -> String.join(",", strings))
                .filter(pattern.asPredicate())
                .forEach(f -> {

                    Pattern p = Pattern.compile("^\\d{1,3},(.*)(?=\\,,\\d)");
                    Pattern p2 = Pattern.compile("(?:,,)(\\d.*)(р.)");
                    Matcher m = p.matcher(f);
                    Matcher m2 = p2.matcher(f);
                    String localWish = "";
                    Integer localPrice = 0;

                    if (m.find()) {
                        localWish = m.group(1);
                    }

                    if (m2.find()) {
                        localPrice = Integer.parseInt(m2.group(1).replace(",", "").trim());
                    }

                    List<Wish> wishes = wishRepository.getWishesByName(localWish).orElseGet(ArrayList::new);

                    if (wishes.size() < 1) {
                        //нету? добавляем
                        wishRepository.save(new Wish(localWish, localPrice, 1, 1,
                                false, "from csv", "", user, new Date()));
                        addCount++;
                    }
                });

        return ResponseParseResult.builder().itemsAdded(addCount).status("Ok").okMessage("Парсинг успешно выполнен").build();
    }
}
