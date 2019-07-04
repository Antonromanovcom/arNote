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
    public List<Wish> getAllLogs() {

        return logsRepository.findAll();
    }


    /**
     * Выдать время последнего пинга.
     *
     * @return
     */
    @Override
    public Time getLastContactTime() {

        // Распечатаем всю выдачу
        return (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime"))).get(0).getLastсontacttime();
    }


    /**
     * Выдать статус сети 220 последнего пинга.
     *
     * @return - Boolean
     */
    @Override
    public Boolean getLastContact220() {


        return (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime"))).get(0).getAc();
    }


    /**
     * Выдать статус LAN последнего пинга.
     *
     * @return - Boolean
     */
    @Override
    public Boolean getLastContactLan() {


        return (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime"))).get(0).getLan();
    }


     /**
     * Пинг залогирован?
     *
     * @return - Boolean
     */
    @Override
    public Boolean getLastContactLogged() {
        if (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime")).get(0).isLogged()==null){
            return false;
        } else {
            return (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime"))).get(0).isLogged();
        }


    }


    /**
     * Пинг залогирован?
     *
     * @return - Logs
     */
    @Override
    public Wish getLastLog() {
        return (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime"))).get(0);
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
