
# arNote
1. Сервис хранения желаний, планирования трат.
2. Сервис анализа инвестиций
3. Сервис финансового планирования

# Сервис хранения желаний
Доступен в сайдбаре в пункте "Список желаний". Предоставляет возможность добавления желаний (они же  - цели, финансовые планы, хотелки). Добавление организовано для каждого конкретного пользователя. Желание представляет собой финансовую цель с наименованием, подробным описанием, ссылкой URL, где можно посмотреть или купить, стоимостью и приоритетом. Желания представлены в общей таблице. Можно фильтровать по приоритету (Первичный или общий) и сортировать по цене или имени. Желания можно удалять и редактировать. 
Существует возможность задать зарплату (причем задается и зарплата и реальная сумма, сколько пользователь может тратить в месяц) и тем самым получить расчет, сколько времени потребуется на реализацию всех задач / приоритетных.
Так же в меню фильтрация добавлен отдельный пункт "помесячная группировка". Этот пункт позволяет задать альтернативную группировку, с помощью которой система сортирует желания по месяцам. Можно двигать желания туда-сюда и понимать, сколько всего в том или ином месяце вы собираетесь потратить. 

# Сервис анализа инвестиций 
Сервис, который я выкатил в прод только 10.02.2021. Сервис "ходит" с помощью REST-запросов в API Московской биржи и буржуйские API (AlphaAdvantag, Yahoo), запрашивает оттуда все доступные данные: котировки акции с промежутком 15 минут, описание бумаги, купоны, дивиденды, исторические данные по годам. Можно добавлять бумаги по тикеру, добавлять даты и объем покупки. После чего можно мониторить рост бумаги, прогнозировать потребные затраты в зависимости от текущего дохода и желаемого, получать календарь ближайших выплат. 
 

# Долгосрочное планирование бюджетов и трат
Сервис, который позволяет планировать сроки выплат кредитов и рассчитывать свои финансовые планы. Грубо говоря - сколько лет понадобится на реализацию тех или иных личных проектов с учетом каждодневных трат, вашей зарплаты и платежам по кредитам.


# URL сервиса
http://84.201.163.22:8080/

# Запуск на проде
-Dspring.profiles.active=prod

# Сборка на локале
 ~/Maven/bin/mvn package -P local

# Что сделали:
* Теперь если выбрана дата по которой нет продаж - выходной или праздник - бумагу добавить нельзя.
* Еще раз проверить, что точно работает изменение по месяацам


# Баги

## Максимальный приоритет:

* Телеграмм-Бот
* Проверить кейс - бумага была в Плане, а я решил ее купить - как онО отыграет?
* НоваяТиньковскаяДельта - для иностранных бумаг
* getClosePositionForTomorrow - в ArNoteUtils - а если вчера было воскресенье?????
* Если вместо "." указать "," в сумме при добавлении бумаги - запрос валится. Нужно валидировать это.
* Подсчет дельты для наших и иностранных бумаг - может унифицировать и вынести в Утилс?
* Календарь покупок с фильтром / поиском по бумагам
* Если я бумагу удаляю, покупки по ней тоже удаляются? Может быть, сделать статус (ПРОДАНА?)
* Нужно продумать комментарии к бумаге и как отдельно выделять бумаги, который я коплю на налог (цвет, флажк, группы?)
* Почему-то при поиске бумаги делается 2 одинаковых запроса к альфа-адвантедж 
* Место, где я стучусь в буржуйские АПИ и если они не отвечают, возвращаю null - не красиво сделано. Надо порефакторить, подумать как исправить. Может быть, сделать типа запрос-пинг. И если он вернул false, то просто не дергать все остальные запросы буржуйские.
* Обновить данные по бумагам
* Вообще надо решить вопрос со столбцами Минимальный Лот / Куплено. Например для ПЛАН не нужно показывать сколько куплено. Это путает. Нужно подумать как это лучше сделать.
* Когда стучишься из под корпоративной сети, он не может достучаться до Яху - нужен серт. И валится весь консолидированный запрос. Такого быть не должно. Такие бумаги по которым запрос свалился надо уметь просто пропускать.
* Вот я добавляю продажу по бумаге. А в какой валюте сумма?????
* Уже нужен фильтр/поиск по бумагам
* Я хочу видеть формулу расчета дельты прямо на фронте
* Нужно убрать доступы к БД из Гита. Сменить пароли в удаленной БД. Локальные все эти пароли, адреса хранить на локальной машине только.
* Свечи с буржуйских сайтов
* Я хочу либо отключать пока предыдущих лет в Финпланах, либо регулировать диапазон
* Я хочу менять размер выплат по кредиту после досрочных погашений



## Ближайшие срочные мелкие косяки и баги:

* Я хочу видеть баланс портфеля при работе с бумагами в основном окне (сколько куплено, динамика)
* Нужно будет сделать где-то на фронте кнопочку, по которой отображается подсвет формул расчета
* Мое самописное кеширование попробовать запихать в аннотацию 
* В классе com.antonromanov.arnote.model.investing.response.DeltaRs (+ И НА ФРОНТЕ!) переименовать поля deltaInRubles, tinkoffDelta, tinkoffDeltaPercent согласно их реально назначения
* Кнопка "Доходы" должна быть заблочена если идет отрицательная прибыль или купленых бумаг нет
* С ArNoteUtils надо что-то делать - там под 1000 строк уже
* Календарь заблочен должен быть если нет купленых бумаг
* Я хочу посмотреть деталку по бумаге и свои покупки по ней.
* Добавить Телеграмм Бот (https://habr.com/ru/post/528694/, https://github.com/taksebe-official/mentalCalculationBot/blob/master/src/main/java/ru/taksebe/telegram/mentalCalculation/MentalCalculationApplication.java, https://github.com/PauloGaldo/telegram-bot/blob/master/telegram-bot/src/main/java/de/simonscholz/bot/telegram/Configuration.java, https://github.com/xabgesagtx/telegram-spring-boot-starter/blob/master/src/main/java/com/github/xabgesagtx/bots/TelegramBotStarter.java, https://habr.com/ru/post/655329/, https://javarush.ru/groups/posts/2959-sozdaem-telegram-bota-s-ispoljhzovaniem-spring-boot, https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter/src/main/java/org/telegram/telegrambots/starter)
* Бага: я решил добавить бумагу. Произошел поиск на фронте, заполнился список, а потом второй раз я захожу добавлять новую бумагу - а там опять этот же заполненный список. Его надо чистить!
* Я хочу чтобы тот факт, что фильтр включен по бумагам как то отображалось на фронте
* возможно есть какой-то компонент с драг-энд-дропом. Может его использовать в планировании по месяцам? Так же туда может быть добавить филтры и поиск, управление сортировкой? Ну и вообще это надо комплексно переделывать. Сейчас этим пользоваться откровенно не удобно
* Я хочу чтобы где-то (подумать как лучше) отображалось: сколько у меня валюты / сколько акций / сколько облиг / сколько ETF
* Я хочу, чтобы мы умели точно определять отрицательный баланс портфеля и это как как-то отображалось каким-то алармом и не работал при этом прогноз дохода
* Я хочу видеть курс бакса в основном окне
* По ETF надо не 0 отображать в дивах, а что-то другое. И соответственно с бека возможно не надо этот блок передавать вообще?
* Я хочу видеть отчеты. Напрмиер такие: диаграмма состава буржуйских бумаг к российским, диаграмма сколько процентов у меня акций, сколько облиг, сколько ETF, баланс портфеля по годам и месяцам.
* Слишком долго грузятся инвестиции. Надо пихать в поток 
* Группы
* ФРОНТ: При изменении кол-ва записей на странице любое добавление рушит табличку - она приходит пустой.
* Прогресс бар при загрузке бумаг
* Графики бумаг
* Статус кеша при загрузке бумаг
* Статус кешей/мап при подсчете консолидированной таблице по фин-планам на фронте
* Почему я все время должен отключать поиск?
* Все модалБоксы на фронте почикать
* Все на фронте на асинхрон
* Консолидированная табла по фин-планам в потоке с прогрессбаром на фронте
* Надпись ArNote в заголовке для мобилки убрать - не лезет
* Не пишется дата создания при создании желания
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
* Перестали сохранять последнюю операцию. Почему?
* Почему бы не кешировать поиск (речь о ценных бамагах и вкладке Инвестиции)(подумать как)? И вообще подумать на счет поиска. Варианты: 

1) С кнопкой.
    1.1 самый тупой - кнопка внизу
    1.2 посложнее - ставим новый Ангуляр и пробуем новые поля с кнопкой


2) кеш поиска (поисковых запросов) + блочим поле пока ответ не придет / либо очередь запросов.
* Для инвестиций: подумать над отображением графиков
* Для инвестиций: подумать над добавлением алармов (и сбросов их в инсту)
* Для инвестиций: подумать над добавлением алармов с информацией об удачном времени покупки / продажи, например о том, что бумага идет к ее историческому минимуму / перешла его, пробила новый (то же самое по максимуму). И не плохо было бы вообще показывать где-то исторический минимум / максимум. Например сделать деталку по бумаге.
* Сделать отображение текущих режимов сортировки и фильтрации
* Вынести вкладку помесячной группировки на сайдбар
* Добавить вкладку групп (так же вынести в сайдбар)
* Написать тесты
* Сейчас мы считаем просто сколько вложили за все время и делим на купоны / рост / дивы за все время. А по хорошему надо считать или за прошлый год или вообще какую-то сложную логику по годам или вообще разный подсчет выдавать, например: всего / по последнему году / по среднему / по минимальному / по максимальному.
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
* Провести общий большой рефакторинг
* Пагинация
* Фин-планы слишком долго отвечают. Надо разобраться почему?
* Опять не работает поиск
* Зачем в выдаче списка желаний для каждого желания блок user ????
* По-хорошему надо хранить покупки в разных валютах. Вдруг курс как-то изменится. 
* Не добавляются буржуйские бумаги
* Как быть - если ценник бумаги в баксах и нужно задать ценник на конкретную дату? В каких единицах я задаю на фронте?
* Не запрашиваются цены бумаги по дате
* Нужно сделать расчет для 120 000 в месяц, 200 000 руб.
* TRCN - не нашли цену в LAST - берем откуда-то еще
* При поиске на мобилке соседняя кнопка почему-то загорается то же красным?
* Порешать автовыключение-режима поиска уже
* Добавить мапстракт
* Привести всю тему с ошибками, их перехватом и выдачей на фронт к одному виду по всему проекту
* В календаре отображаются дивы однотипные по 4 раза. Одно и то же. Почему?
* Менять версии в помнике, чтобы скрипт как-то умел их подхватывать и чтобы они в ГИТе отображались
* Надо ходить в историю и смотреть какую самую раннюю дату можем запросить. И как-то блочить возможность запрашивать даты раньше на фронте.
* Не влезающие описания и названия облигаций
* Перенести код настроек Дженкинса сюда
* Может добавление бумаги сделать на беке асинхронно? Типа если свалилось с ошибкой - какое-то время еще долбится
* Если описание не удалось достать - сходить для буржуйской бумаги за описанием бумаги еще куда-нибудь
* Проверить, что  у купонов / дивов в разделе доход (календарь) - туда  не попадает план. И в доходе тоже
* Зачем нам грузить именно всю историю? Для чего?
* Наверное, стоит научиться показывать прогресс загрузки данных. И в идеале надо сначала показать типа только тикеры, а потом в отдельном потоке
   запускать подгрузку в кеш всех остальных данных, чтобы фронт опрашивал поток и он типа отвечал что сделано, какой процент и сколько еще
   осталось, типа сколько бумаг уже загружено, сколько в кеше. Ну и по хорошему, надо типа посмотреть сколько в среднем занимает
   этот процесс, чисто эмпирически и примерно показывать сколько осталось времени.


## Что надо сделать еще:

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
* Сейчас мы считаем просто сколько вложили за все время и делим на купоны / рост / дивы за все время. А по хорошему надо считать или за прошлый год или вообще какую-то сложную логику по годам или вообще разный подсчет выдавать, например: всего / по последнему году / по среднему / по минимальному / по максимальному.
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

## Отдаленные планы:

* Ссылки
* Публичные списки
* Блог
* Чат
* Трекер багов с номерами, чтобы не хранить это в ридми
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

-- Вьюшка для удобного представления таблицы Желания
CREATE VIEW wishes_view_not_realized AS
  SELECT
    w.id,
    w.wish,
    w.price,
    w.priority,
    w.priority_group
  FROM arnote.public.wishes AS w WHERE w.user_id = 2 AND (w.realized  = FALSE OR w.realized ISNULL) ;


# Скрипты

Скрипты для сборки проекта в облаке:

**arnote.sh**

#!/bin/sh

# Copy arNote jar script

<pre><code>
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
</code></pre>  

**arnote-wrapper.sh**

#!/bin/sh
# Restart arNote script-wrapper

<pre><code>
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
</code></pre>


 
