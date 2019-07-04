package com.antonromanov.temprest.service;


import com.antonromanov.temprest.livecontrolthread.MainParameters;
import com.antonromanov.temprest.model.DailyReport;
import com.antonromanov.temprest.model.Logs;
import com.antonromanov.temprest.model.Status;
import com.antonromanov.temprest.model.Temperature;
import com.antonromanov.temprest.repositoty.LogsRepository;
import com.antonromanov.temprest.repositoty.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.util.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static com.antonromanov.temprest.utils.Utils.checkDayNight;


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
     * Основные параметры живучести.
     */
    MainParameters mainParametrs = new MainParameters();


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
     * Выдать состояние мониторинга.
     *
     *
     * @return
     */
    @Override
    public Status getGlobalStatus() {

        Status status = new Status(getLastContact220(), getLastContactLan(),
                    getLastLog().getLasttemperature(),
                    getLastLog().getLasthumidity(),
                    getLastLog().getServertime(),
                    mainParametrs.getLastPingTime(),
                    0,
                    0,
                    0,
                    0,
                    getLastLog().getLastсontactdate());

        return status;
    }

    /**
     * Выдать глобальные параметры текущего мониторинга.
     *
     *
     * @return
     */
    @Override
    public MainParameters getMainParametrs() {
        return this.mainParametrs;
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





    /**
     * Добавить статус (это будет делать Ардуина).
     *
     * @param log
     * @return
     */
    @Override
    public List<Logs> addLog(Status log) throws ParseException {

        //Логгируем (добавляем) время сервера
        Date date = new Date();
        Time time = new Time(date.getTime());
        log.setServerTime(time);

        // todo: этот пиздец переделать надо
        if (log.isLogged()){
            log.setLogged(true);
        } else {
            log.setLogged(false);
        }


        mainParametrs.setJustStartedSituation(false);
        mainParametrs.setLastPingTime(time);
        mainParametrs.setStatus(log);
        mainParametrs.setAcStatus(true);
        mainParametrs.setLanStatus(true);
        if (log.getWho()==null) log.setWho("REST");
        if (isBlank(log.getWho())) log.setWho("REST");

        // TODO:  нам вот здесь хорошо бы проверить на ноль что-нибудь (надо подумать что: скорее всего даты)
        // TODO:  у нас два одинаковых энтити - лог и статус - на хера????? Возникает путаница

        logsRepository.save(new Logs(log));
        return logsRepository.findAll();
    }

	@Override
	public void addLog2(Status log) {
		//Логгируем (добавляем) время сервера
		Date date = new Date();
		Time time = new Time(date.getTime());
		log.setServerTime(time);
		mainParametrs.setJustStartedSituation(false);
		mainParametrs.setLastPingTime(time);
		mainParametrs.setStatus(log);
		mainParametrs.setAcStatus(true);
		mainParametrs.setLanStatus(true);
		if (log.getWho()==null) log.setWho("REST");
		if (isBlank(log.getWho())) log.setWho("REST");

		// TODO:  нам вот здесь хорошо бы проверить на ноль что-нибудь (надо подумать что: скорее всего даты)
		logsRepository.save(new Logs(log));
	}
}
