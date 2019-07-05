package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.repositoty.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;


/**
 * Основной сервис. Тут вся бизнес логика (кроме потока мониторинга).
 */
@Service
public class MainServiceImpl implements MainService {

    /**
     * Репозиторий логов живучести
     */
    @Autowired
    private LogsRepository logsRepository;


    @Override
    public List<Wish> getAllWishes() {
        return logsRepository.findAll();
    }


    @Override
    public void updateWish(Wish log) {
      logsRepository.save(log);
    }

    @Override
    public Wish addWish(Wish wish)  {
        Wish newWish = logsRepository.saveAndFlush(wish);

        return null;
    }


}
