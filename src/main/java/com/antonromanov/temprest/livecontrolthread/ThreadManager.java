package com.antonromanov.temprest.livecontrolthread;

import com.antonromanov.temprest.service.MainService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Обертка-компонент, в котором Пост-конструкт
 * запускает через пул потоков единственный поток, мониторящий жизнедеятельность датчиков (просто по таймаутам).
 */
@Component
public class ThreadManager {

    /**
     * Основной сервис.
     */
    @Autowired
    MainService mainService;

    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

    /**
     * Запускаем в момент инициализации приложения поток.
     */
    @PostConstruct
    public void init() {

        /**
         * Пул потоков.
         */
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        if (mainService!=null){ // проверяем, чтобы сервис не нол был.
            IsAliveController task = new IsAliveController(mainService); //наш класс-поток
            mainService.getMainParametrs().setLogged(false); // снимаем флаг ЗАЛОГИРОВАНО
            mainService.getMainParametrs().setJustStartedSituation(true); // ставим флаг, что мы только стартонули
            mainService.getMainParametrs().setTimeIsOver(true); //включаем флаг, что время кончилось  - по умолчанию стартуем в положении "все выключено"
            mainService.getMainParametrs().setLanStatus(false); // соответственно, "выключаем сеть"
            mainService.getMainParametrs().setAcStatus(false); // соответственно, "выключаем 220"

            LOGGER.warn("THREAD MANAGER HAS STARTED. T I M E ====== " + mainService.getLastContactTime());

            // Надо проверить, успели ли мы что-то уже отлогировать, поэтому если успели, то...
            if (mainService.getLastContactTime()!=null) {
                mainService.getMainParametrs().setLastPingTime(mainService.getLastContactTime()); // проставляем время последнего пинга
            }

            executor.execute(task); // запускаем поток

        } else {
            LOGGER.warn("mainService==null (Manager)");
        }
    }
}
