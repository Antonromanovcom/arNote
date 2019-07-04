package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.repositoty.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Time;
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

    /**
     * Выплюнуть все логи живучести.
     *
     * @return
     */
    @Override
    public List<Wish> getAllWishes() {
        return logsRepository.findAll();
    }







    /**
     * Получить последнюю записанную в БД температуру.
     *
     * @return
     */


    /**
     * Обновить последний пинг (лог) новыми данными
     *
     */
    @Override
    public void updateLastLog(Wish log) {
      logsRepository.save(log);
    }


}
