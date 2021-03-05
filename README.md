
# arNote
1. Сервис хранения желаний, планирования трат.
2. Сервис анализа инвестиций

# Сервис хранения желаний
Доступен в сайдбаре в пункте "Главная". Предоставляет возможность добавления желаний (они же  - цели, финансовые планы, хотелки). Добавление организовано для каждого конкретного пользователя. Желание представляет собой финансовую цель с наименованием, подробным описанием, ссылкой URL, где можно посмотреть или купить, стоимостью и приоритетом. Желания представлены в общей таблице. Можно фильтровать по приоритету (Первичный или общий) и сортировать по цене или имени. Желаний можно удалять и редактировать. 
Существует возможность задать зарплату (причем задается и зарплата и реальная сумма, сколько пользователь может тратить в месяц) и тем самым получить расчет, сколько времени потребуется на реализацию всех задач / приоритетных.
Так же в меню фильтрация добавлен отдельный пункт "помесячная группировка". Этот пункт позволяет задать альтернативную группировку, с помощью которой система сортирует желания по месяцам. Можно двигать желания туда-сюда и понимать, сколько всего в том или ином месяце вы собираетесь потратить. 

# Сервис анализа инвестиций 
Сервис, который я выкатил в прод только 10.02.2021. Сервис "ходит" с помощью REST-запросов в API Московской биржи, запрашивает оттуда все доступные данные: котировки акции с промежутком 15 минут, описание бумаги, купоны, дивиденды, исторические данные по годам. Можно добавлять бумаги по тикеру, добавлять даты и объем покупки. После чего можно мониторить рост бумаги, прогнозировать потребные затраты в зависимости от текущего дохода и желаемого, получать календарь ближайших выплат. 
Пока сервис работает только с Московской биржей и только с облигациями и акциями. Фонды и иностранные бумаги будут позже. 



#URL сервис-а
http://84.201.163.22:8080/

# Запуск на проде
-Dspring.profiles.active=prod

# Что сделали:
* Добавили буржуйские бумаги, поправили поиск

# Баги

* При фильтрации по План / Факт, если бумаг не найдено так и надо отображать пустоту, пустую таблицу - а этого не происходит.
* Не работает запрос цены буржуйской бумаги по тикеру (при добавлении)
* В календаре отображаются дивы однотипные по 4 раза. Одно и то же. Почему?
* Менять версии в помнике, чтобы скрипт как-то умел их подхватывать и чтобы они в ГИТе отображались
* Надо ходить в историю и смотреть какую самую раннюю дату можем запросить. И как-то блочить возможность запрашивать даты раньше на фронте.
* Не влезающие описания и названия облигаций
* Перенести код настроек Дженкинса сюда
* Проверить, что  у купонов / дивов в разделе доход (календарь) - туда  не попадает план. И в доходе тоже
* Зачем нам грузить именно всю историю? Для чего?
* Наверное, стоит научиться показывать прогресс загрузки данных.


# Ближайшие срочные мелкие косяки и баги:

* Группы
* Разные помники для Прома и Дева
* Возможность переключения валюты текущей (для все бумаг и всех показателей должна быть одна валюта - все рассчеты в одной валюте)
* Разобраться с фигней, когда переходишь на страничку - а она не отображается (выявить причины: не поднялся сервис, протух токен)
* Вынести пользовательские методы в отдельный контроллер и сделать отдельный метод для сохранения режима получения, отдельный для получения
* Придумать и написать тест для перемещения
* Сделать бейдж NEW в помесячной группировке для только что добавленных желаний
* Сделать сохранение фильтрации и сортировки для помесячной группировки
* Сделать поиск в помесячной группировке
* Надо убрать РеспонсЭнтити и возвращать нормальные ДТО-объекты, а не этот пиздец
* Сделать какой-то учет текущей суммы
* Сделать отображение текущих режимов сортировки и фильтрации
* Вынести вкладку помесячной группировки на сайдбар
* Добавить вкладку групп (так же вынести в сайдбар)
* Написать тесты
* Прокинуть интерцепторы
* Общий ЭксепшнХендлер
* Сделать само-отключение поиска
* Нормальный DNS для сайта
* Показывать более развернутую статистику: реализованное и все такое
* При щелчке на желании показывать в каком оно месяце при всех видах отображения
* Надо организовать еще списки подсчетов с исключением определенных записей (типа микро-сметы)
* Надо чтобы нельзя было добавлять одинаковые желания
* Написать статью на AntonRomanov.com о сервисе
* Починить фигню с тем что надо щелкнуть, чтобы табличка подгрузилась
* Починить фигню с 401-й странице
* Решить тему с протухающим токеном
* Подсмотреть как сделан шедулинг проверки протухания токена с выводом алерта.
* Фавикон
* Выделение и перемещение по месяцам нескольких желаний
* Кнопочки Реализовать / Удалить / Редактировать при нажатии на желание в помесячно-групповом режиме
* Кнопочки "Вернуться в табличный режим" и "Обновить" заменить для мобилы
* Избавиться от $Do и всего такого.


# Что надо сделать еще:

* Надо что-то делать с датой, если она пуста при реализации желания
* Убрать крипторежим
* Убрать ненужные кнопки не с админской странички. И выключать кнопки когда не нужно (в т.ч. в диалоговых окнах)
* В фильтрацию добавить < / > какой-то суммы, и так же добавить фильтрацию по дате
* Привести в порядок форму парсинга csv
* Нормальный поиск (эвент при нажатии каждой клавиши)
* Надо подумать на хера мне вообще MainService. Что он делает?
* Подумать над реализации Калькулятора строительства, то есть отображения как в Гугл-Таблицах
* Надо сделать чтобы можно было сметы сохранять (типа хранение таких как бы табличек, с разными ячейками, кодом ячейки, на который можно ссылаться, ну и так далее...)
* Возможность просматривать реализованные желания и проводить какие-то операции с ними
* Сделать обрезку wish-name в датагриде на фронте по длине и еще и наложить ограничения при добавлении по длине
* Надо бы обработать на беке тему с протуханием токенов (примеры я видел)
* Сделать чтобы уже при вводе логина при редактировании пользователя осуществлялся запрос при каждом нажатии и поиск - есть такой юзер или нет
* Домик и сайдбар почистили, но теперь "оно" вообще никогда не отображается.
* Надо все правки желаний утащить в один метод
* В перспективе надо сделать такой дашбоард с виджетом статистики. Типа добавлено в этом месяце, реализовано, удалено, изменено, добавлено на сумму и так далее....
* Почистить код на фронте. Там пиздец просто....

# Отдаленные планы:

* Ссылки
* Публичные списки
* Блог
* Чат
* Разные списки
* Файловое облако



# Вспомогательные SQL:

-- Сумма мелких и важных желаний без реализованных и архивных
select sum(p.price) from (select * from
  arnote.wishes w WHERE
  (w.id NOT IN (327, 311, 326, 328, 308, 373, 299, 300, 307, 335, 303, 259, 309, 333, 331, 313, 312, 330, 304))) p
WHERE NOT p.archive AND (p.realized=false or p.realized ISNULL );


-- Сумма реализованного
select sum(p.price) from (select * from
  arnote.wishes w WHERE
  (w.id NOT IN (311))) p
WHERE NOT p.archive AND (p.realized=true);


-- Реализованное
select * from (select * from
  arnote.wishes w WHERE
  (w.id NOT IN (311))) p
WHERE NOT p.archive AND (p.realized=true);

-- Сумма реализованного (за месяц)
select sum(p.price) from (select * from
  arnote.wishes w WHERE
  (w.id NOT IN (311) and extract(month FROM w.realization_date) = 10)) p
WHERE NOT p.archive AND (p.realized=true);


#Скрипты

Скрипты для сборки проекта в облаке:

**arnote.sh**

#!/bin/sh

# Copy arNote jar script

#string="Hello world"
echo  "Удаляем директорию..."
sudo rm -Rfv /home/admin/arnote
echo "Директория удалена. Создаем заново...."
sudo mkdir /home/admin/arnote
echo "Директория создана успешно. Копируем файлы...."
sudo cp -a /var/lib/jenkins/workspace/arNote/* /home/admin/arnote/
echo "Подменяем properties-файл для профиля prod"
sudo cp -f /home/admin/application-prod.properties /home/admin/arnote/src/main/resources/
echo "Пытаемся вызвать Maven и собрать проект..."
sudo mvn -f /home/admin/arnote/pom.xml clean package
echo "Скрипт выполнен!!!"

**arnote-wrapper.sh**

#!/bin/sh
# Restart arNote script-wrapper

echo  " * * *   REBUILD AND RESTART ARNOTE SCRIPT   * * * "
echo "Пытаемся остановить unit"
sudo systemctl restart arnote
echo "Unit успешно остановлен"
echo "Пытаемся запустить скрипт удаления старых файлов и пересборки сервиса Maven'ом"
. /home/admin/arnote.sh

echo "========= !!! Скрипт-wrapper успешно выполнен!!! ==========="
echo "Пытемся снова запустить Unit"
sudo systemctl start arnote
echo "Unit успешно запущен!"


 
