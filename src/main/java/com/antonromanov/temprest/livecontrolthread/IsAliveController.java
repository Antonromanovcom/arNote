package com.antonromanov.temprest.livecontrolthread;

import com.antonromanov.temprest.model.Logs;
import com.antonromanov.temprest.model.Status;
import com.antonromanov.temprest.service.MainService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Service;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.antonromanov.temprest.utils.Utils.checkTimeout;

/**
 * Поток, мониторящий состояние датчиков по таймаутам.
 */
@Service
public class IsAliveController implements Runnable {

	/**
	 * Инжектим наш сервис.
	 */
	MainService mainService;



	/**
	 * Логгер файловый
	 */
	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	/**
	 * Конструктор.
	 *
	 * @param mainService
	 */
	public IsAliveController(MainService mainService) {
		this.mainService = mainService;
	}




	/**
	 * Собсна, основной метод, где вся логика.
	 */
	@Override
	public void run() {

		boolean ret = true;

		if (mainService != null) { // проверяем, чтобы сервис вообще доступен был

			while (ret) { // бесконечный цикл.
				try {


				//	mainService.errorPushToBot("PROBLEM! SITUATION 2: TIMEOUT - DELAYED | NOT LOGGED | AC & LAN WERE ON");

					LOGGER.warn("TIMEOUT TEST:  " + checkTimeout(mainService.getLastContactTime()));
					Date date = new Date();
					Time time = new Time(date.getTime()); // берем текущее время.

					LOGGER.info("MainParametrs: AC {" + mainService.getLastContact220() + "},LAN {" +
							mainService.getLastContactLan() + "}, IS LOGGED {" +
							mainService.getLastContactLogged() + "}");


					/**
					 *  Первое условие:
					 *
					 *  + Таймаут просрочен (false)
					 *  + Не логгировали это раньше
					 *  + АС или LAN по последним логам включен.
					 */
					if (!checkTimeout(mainService.getLastContactTime()) && (!mainService.getLastContactLogged()) &&
							(mainService.getLastContact220() || mainService.getLastContactLan())) { // Хьюстон, у нас проблемы.....

						// Хьюстон, у нас проблемы....

						LOGGER.warn("============ У НАС ПРОБЛЕМЫ ============");
						LOGGER.warn("------------- Первое условие (ситуация) -------------");
						LOGGER.warn("		+ Таймаут просрочен (false)");
						LOGGER.warn("		+ Не логгировали это раньше");
						LOGGER.warn("		+ АС или LAN по последним логам включен.");


						/**
						 * Пишет в БД запись, что связи нет, чтобы ее фронтенд прочитать мог.
						 */
						Status log = new Status("REST: NO PING", false, false, 0, 0, time, time,
								0, 0, 0, 0, new Date(), true);

						// Хьюстон, у нас проблемы....
						LOGGER.info("ENTRY IN DB CREATED:" + log.toString());

						try {
							List<Logs> tempLogs = mainService.addLog(log); // пишем через сервис.
						} catch (ParseException e) { // todo: Почему здесь ПарсЭксепшен????
							e.printStackTrace();
						}


						/**
						 * Следующая ситуация - проблема или решилась или ее не было. Условия:
						 *
						 * + АС - включено
						 * + LAN - включен
						 */
					} else if ((mainService.getLastContact220() || mainService.getLastContactLan())) {

						LOGGER.warn("PROBLEM WAS SOLVED OR HAS NOT BEEN OCCURED");
						LOGGER.warn("------------- Второе условие (ситуация) -------------");
						LOGGER.warn("		+ АС или LAN по последним логам включен.");
						LOGGER.warn("RESULT: НИЧЕГО НЕ ДЕЛАЕМ");

						/**
						 * Стартовая ситуация:
						 *
						 * + Таймаут по пингу просрочен
						 * + незалогированы
						 * + статусы сети и 220 отрицательные
						 */
					} else if (!checkTimeout(mainService.getLastContactTime()) && !mainService.getLastContactLogged() &&
							(!mainService.getLastContact220() || !mainService.getLastContactLan())
					) { // стартовая ситуация
					}

					/**
					 * ждем 60 секунд.
					 */
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} catch (BeanCreationException exception) { //todo: заменить на просто exception
				}
			}
		}
	}

}
