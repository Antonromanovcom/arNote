
# arNote
Сервис хранения желаний, планирования трат, хранения заметок, ссылок и прочего. Сервис обмена.

#Сам сервис
http://84.201.163.22:8080/

# Запуск на проде
-Dspring.profiles.active=prod

#Что сделали:

* Влил инвестиции. Пока только Московскую Биржу
* Добавил персистентную фильтрацию и сортировку для основной таблицы желаний

#Ближайшие срочные мелкие косяки и баги:

* Группы
* Вынести пользовательские методы в отдельный контроллер и сделать отдельный метод для сохранения режима получения, отдельный для получения
* Придумать и написать тест для перемещения
* Сделать бейдж NEW в помесячной группировке для только что добавленных желаний
* Сделать сохранение фильтрации и сортировки для помесячной грппировки
* Сделать поиск в помесячной группировке
* Надо убрать РеспонсЭнтити и возвращать налормальные ДТО-объекты а не этот пиздец
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


#Что надо сделать еще:

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

#Отдаленные планы:

* Ссылки
* Публичные списки
* Блог
* Чат
* Разные списки
* Файловое облако



#Вспомогательные SQL:

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





 
