package com.antonromanov.arnote.service;

import au.com.bytecode.opencsv.CSVReader;
import com.antonromanov.arnote.model.ResponseParseResult;
import com.antonromanov.arnote.model.Salary;
import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.repositoty.SalaryRepository;
import com.antonromanov.arnote.repositoty.WishRepository;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public List<Wish> getAllWishesWithPriority1() {
        return wishRepository.getAllWithPriority1();
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
    public Integer getSumm4All() {
        return wishRepository.findAll().stream().map(w -> w.getPrice()).reduce(0, ArithmeticUtils::addAndCheck);
    }

    @Override
    public Integer getSumm4Prior() {
        return wishRepository.getAllWithPriority1().stream().map(w -> w.getPrice()).reduce(0, ArithmeticUtils::addAndCheck);
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
    public Salary getLastSalary() {
        return (salaryRepository.getLastSalary(new PageRequest(0, 1, Sort.Direction.DESC, "salarydate"))).get(0);
    }

    @Override
    public Integer calculateImplementationPeriod(Integer summ) {
        return summ / getLastSalary().getResidualSalary();
    }



    @Override
    public  ResponseParseResult parseCsv(MultipartFile file) throws IOException {

      //  try {

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()), ',', '"', 1);
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
                            wishRepository.save(new Wish(localWish, localPrice, 1, false, "from csv", ""));
                            addCount++;
                        }
                    });

        /*} catch (Exception e){
            return ResponseParseResult.builder().itemsAdded(0).status("Error").errorMessage("Парсинг не удался: " + ).build();
        }*/

        return ResponseParseResult.builder().itemsAdded(addCount).status("Ok").okMessage("Парсинг успешно выполнен").build();
    }

    @Override
    public void clearCounter() {
        addCount = 0;
    }
}
