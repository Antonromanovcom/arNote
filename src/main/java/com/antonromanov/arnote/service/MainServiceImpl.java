package com.antonromanov.arnote.service;


import com.antonromanov.arnote.model.DailyReport;
import com.antonromanov.arnote.model.Logs;
import com.antonromanov.arnote.model.Temperature;
import com.antonromanov.arnote.repositoty.LogsRepository;
import com.antonromanov.arnote.repositoty.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.util.*;

import static com.antonromanov.arnote.utils.Utils.checkDayNight;


/**
 * Основной сервис. Тут вся бизнес логика (кроме потока мониторинга).
 */
@Service
public class MainServiceImpl implements MainService {



    /**
     * Репозиторий температуры
     */
    @Autowired
    private TemperatureRepository usersRepository;

    /**
     * Репозиторий логов живучести
     */
    @Autowired
    private LogsRepository logsRepository;


    /**
     * Получить все логи температур.
     * @return
     */
    @Override
    public List<Temperature> getAll() {
        return usersRepository.findAll();
    }

    /**
     * Выплюнуть все логи живучести.
     *
     * @return
     */
    @Override
    public List<Logs> getAllLogs() {

        return logsRepository.findAll();
    }

	/**
	 * Добавить температуру.
	 *
	 * @param temp
	 * @param status
	 * @return
	 */
	@Override
    public List<Temperature> addMeasure(Double temp, String status) {
        usersRepository.save(new Temperature(temp, status));
        return usersRepository.findAll();
    }


    /**
     * Выдать стаистику по сегодня.
     *
     * @return
     * @throws ParseException
     */
    @Override
    public List<Temperature> getTodayMeasures() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return usersRepository.getTodayMeasures(new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat.format(date)));
    }

    /**
     * Выдать статистику по неделе текущей.
     *
     * @return
     * @throws ParseException
     */
    @Override
    public List<DailyReport> getWeeklyDayReport() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, -7);
        Date currentDateWeekAgo = c.getTime();
        ArrayList<Temperature> temperaturesForWeek = new ArrayList<>();
        temperaturesForWeek.addAll(usersRepository.getWeekMeasures(new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat.format(currentDateWeekAgo))));
        return checkDayNight(temperaturesForWeek);
    }

    /**
     * Выдать статистику за этот месяц.
     *
     *
     * @return
     * @throws ParseException
     */
    @Override
    public List<DailyReport> getMonthDayReport() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, -30);
        Date currentDateWeekAgo = c.getTime();
        ArrayList<Temperature> temperaturesForMonth = new ArrayList<>();
        temperaturesForMonth.addAll(usersRepository.getWeekMeasures(new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat.format(currentDateWeekAgo))));

        return checkDayNight(temperaturesForMonth);
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
    public Logs getLastLog() {
        return (logsRepository.getLastPingedEntry3(new PageRequest(0, 1, Sort.Direction.DESC, "servertime"))).get(0);
    }

    /**
     * Получить последнюю записанную в БД температуру.
     *
     * @return
     */
    @Override
    public Temperature getLastTemp() {
        return (usersRepository.getLastPingedEntry(new PageRequest(0, 1, Sort.Direction.DESC, "timeCreated" ))).get(0);
    }


    /**
     * Обновить последний пинг (лог) новыми данными
     *
     */
    @Override
    public void updateLastLog(Logs log) {
      logsRepository.save(log);
    }


}
