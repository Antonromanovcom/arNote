package com.antonromanov.arnote.service;

import au.com.bytecode.opencsv.CSVReader;
import com.antonromanov.arnote.model.*;
import com.antonromanov.arnote.repositoty.SalaryRepository;
import com.antonromanov.arnote.repositoty.WishRepository;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.utils.Utils.*;


@Service
public class MainServiceImpl implements MainService {


    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    Integer addCount = 0; // Количество добавлений


    @Override
    public List<Wish> getAllWishes() {
        return wishRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
    }

    @Override
    public List<Wish> getAllWishesWithPriority1(LocalUser user) {
        return wishRepository.getAllWithPriority1(user);
    }

    @Override
    public int getMaxPriority(LocalUser user) {
        List<Wish> wishDTOList = wishRepository.getAllWithGroupOrder(user);
        Comparator<Wish> comparator = Comparator.comparing( Wish::getPriorityGroup);

        if ((wishDTOList.stream().filter(wish -> wish.getPriorityGroup()!=null).max(comparator).isPresent())) {
            return (wishDTOList.stream().filter(wish -> wish.getPriorityGroup() != null).max(comparator).get().getPriorityGroup()) + 1;
        } else {
            return 1;
        }
    }

   private void addIteminWishDTOListForNullPriorityWishes(List<WishDTOList> wishDTOListGlobal,
                                                   List<WishDTO> wishDTOListFiltered,
                                                   int maxPrior,
                                                   LocalUser user){
        wishDTOListGlobal.add(WishDTOList.builder()
                .wishList(wishDTOListFiltered)
                .monthNumber(computerMonthNumber(maxPrior+1))
                .monthName(computerMonth(maxPrior))
                .year(String.valueOf(getCurrentYear()))
                .colspan(2)
                .sum(wishDTOListFiltered.stream().map(WishDTO::getPrice).reduce(0, ArithmeticUtils::addAndCheck))
                .overflow((wishDTOListFiltered.stream().map(WishDTO::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck))>getLastSalary(user).getResidualSalary())
                .colorClass(getClassColorByMonth(0, (wishDTOListFiltered.stream().map(WishDTO::getPrice)
                        .reduce(0, ArithmeticUtils::addAndCheck))>getLastSalary(user).getResidualSalary()))
                .expanded(true)
                .build());
    }

    @Override
    public List<WishDTOList> getAllWishesWithGroupPriority(LocalUser user) {

        int maxPrior = getMaxPriority(user);
        List<WishDTOList> wishDTOListGlobal = new ArrayList<>();

        if (maxPrior-1 > 0){
            int currentMonth = 1;
            while (currentMonth < maxPrior){

                int finalCurrentMonth = currentMonth;
                List<WishDTO> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                        .stream()
                        .filter(wish -> wish.getPriorityGroup()!=null)
                        .filter(wish -> wish.getPriorityGroup()== finalCurrentMonth)
                        .map(w -> prepareWishDTO(w, maxPrior))
                        .collect(Collectors.toList());

                wishDTOListGlobal.add(WishDTOList.builder()
                        .wishList(wishDTOListFiltered)
                        .monthNumber(computerMonthNumber(currentMonth))
                        .monthName(computerMonth(currentMonth))
                        .year(String.valueOf(getCurrentYear()))
                        .colspan(2)
                        .sum(wishDTOListFiltered.stream().map(WishDTO::getPrice).reduce(0, ArithmeticUtils::addAndCheck))
                        .overflow((wishDTOListFiltered.stream().map(WishDTO::getPrice).reduce(0, ArithmeticUtils::addAndCheck))>getLastSalary(user).getResidualSalary())
                        .colorClass(getClassColorByMonth(computerMonthNumber(currentMonth), (wishDTOListFiltered.stream().map(WishDTO::getPrice).reduce(0, ArithmeticUtils::addAndCheck))>getLastSalary(user).getResidualSalary()))
                        .expanded(true)
                        .build());

                currentMonth++;
            }

            List<WishDTO> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                    .stream()
                    .filter(wish -> wish.getPriorityGroup()==null)
                    .map(w -> prepareWishDTO(w, maxPrior))
                    .collect(Collectors.toList());

            addIteminWishDTOListForNullPriorityWishes(wishDTOListGlobal, wishDTOListFiltered, maxPrior, user);

        } else {

            List<WishDTO> wishDTOListFiltered = wishRepository.getAllWithGroupOrder(user)
                    .stream()
                    .map(w -> prepareWishDTO(w, maxPrior))
                    .collect(Collectors.toList());

            addIteminWishDTOListForNullPriorityWishes(wishDTOListGlobal, wishDTOListFiltered, maxPrior, user);
        }

        return wishDTOListGlobal;
    }

    @Override
    public List<Wish> getAllWishesByUserId(LocalUser user) {
        return wishRepository.findAllByIdSorted(user);
    }

    @Override
    public Optional<List<Wish>> findAllWishesByWish(String wish, LocalUser user) {
        return wishRepository.findAllByWishAndUser(wish, user.getId());
    }

    @Override
    public void updateWish(Wish log) {
      wishRepository.save(log);
    }

    @Override
    public Wish addWish(Wish wish) {
        return wishRepository.saveAndFlush(wish);
    }

    @Override
    public Optional<Wish> getWishById(int id) {
        return wishRepository.findById(Long.valueOf(id));
    }

    @Override
    public Integer getSumm4All(LocalUser user) {
        return wishRepository.findAllByIdSorted(user).stream().map(w -> w.getPrice()).reduce(0, ArithmeticUtils::addAndCheck);
    }

    @Override
    public Integer getSumm4Prior(LocalUser user) {
        return wishRepository.getAllWithPriority1(user).stream().map(w -> w.getPrice()).reduce(0, ArithmeticUtils::addAndCheck);
    }

    @Override
    public void deleteWish(String id) {
        wishRepository.deleteByLongId(Long.parseLong(id));
    }

    @Override
    public Salary saveSalary(Salary salary) {
        return salaryRepository.saveAndFlush(salary);
    }

    @Override
    public Salary getLastSalary(LocalUser localUser) {
        return (salaryRepository.getLastSalary(localUser)).size()<1 ? null : (salaryRepository.getLastSalary(localUser)).get(0);
    }

    @Override
    public Integer calculateImplementationPeriod(Integer summ, LocalUser localUser) {
        return summ / (getLastSalary(localUser).getResidualSalary());
    }


    @Override
    public  ResponseParseResult parseCsv(MultipartFile file, LocalUser localUser) throws IOException {

      //  try {

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), "UTF-8"), ',', '"', 1);
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
                            System.out.println("Matched m1: " + localWish);
                        } else {
                            System.out.println("No match.");
                        }

                        System.out.println("=========================================");

                        if (m2.find()) {
                            localPrice = Integer.parseInt(m2.group(1).replace(",", "").trim());
                            System.out.println("Matched m2: " + localPrice);
                        } else {
                            System.out.println("No match.");
                        }

                        List<Wish> wishes = wishRepository.getWishesByName(localWish).orElseGet(() -> new ArrayList<>());

                        if (wishes.size() < 1) {
                            //нету? добавляем


                            wishRepository.save(new Wish(localWish, localPrice, 1, false, "from csv", "", localUser));
                            addCount++;
                        }
                    });

        return ResponseParseResult.builder().itemsAdded(addCount).status("Ok").okMessage("Парсинг успешно выполнен").build();
    }

    @Override
    public void clearCounter() {
        addCount = 0;
    }
}
