package com.antonromanov.arnote.service;

import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.repositoty.WishRepository;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class MainServiceImpl implements MainService {


    @Autowired
    private WishRepository wishRepository;


    @Override
    public List<Wish> getAllWishes() {
        return wishRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
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
        return wishRepository.getAllNotWithPriority1().stream().map(w -> w.getPrice()).reduce(0, ArithmeticUtils::addAndCheck);
    }

    @Override
    public void deleteWish(String id) {
        wishRepository.deleteByLongId(Long.parseLong(id));
    }


}
