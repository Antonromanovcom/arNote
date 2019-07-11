package com.antonromanov.arnote.service;

import com.antonromanov.arnote.model.Salary;
import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.repositoty.SalaryRepository;
import com.antonromanov.arnote.repositoty.WishRepository;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class MainServiceImpl implements MainService {


    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private SalaryRepository salaryRepository;


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



}
