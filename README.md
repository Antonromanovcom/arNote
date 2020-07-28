
# arNote
Сервис хранения желаний, планирования трат, хранения заметок, ссылок и прочего. Сервис обмена.

#Сам сервис
http://84.201.163.22:8080/

# Запуск на проде
-Dspring.profiles.active=prod

#Что сделали:

* Добавил набегающий баланс для помесячной группировки

#Ближайшие срочные мелкие косяки и баги:

* Косяк с перемещением желаний по месяцам так и не починил - надо сделать
* Сохранение сортировки и режимов отображения
* Группы
* Подлить коллекцию Постмана в репозиторий, добавить туда энваронменты
* Сделать кнопку зануления месячной группировки
* Надо убрать РеспонсЭнтити и возвращать налормальные ДТО-объекты а не этот пиздец
* Сделать чтобы в помесячной группировке показывалось сколько денег переносится на следующий месяц
* Сделайть какой-то учет текушей суммы
* Вынести вкладку помесячной группировки на сайдбар
* Добавить вкладку групп (так же вынести в сайдбар)
* Написать тесты
* Прокинуть интерцепторы
* Общий ЭксепшнХендлер
* Сделать само-отключение поиска
* Нормальный DNS для сайта
* Показывать более развернутую статистику: реализованное и все такое
* При щелчке на желании показывать в каком оно месяце при помесячном отображении
* Надо организовать еще списки подсчетов с исключением определенных записей.
* Надо чтобы нельзя было добавлять одинаковые желания
* Написать статью на AntonRomanov.com о сервисе
* Починить фигню с тем что надо щелкнуть чтобы табличка подгрузилась
* Починить фигню с 401-й странице
* Подсмотреть как сделан шедулинг проверки протухания токена с выводом алерта.
* Фавикон
* Выделение и перемещение по месяцам нескольких желаний
* Кнопочки Реализовать / Удалить / Редактировать при нажатии на желание в помесячно-групповом режиме
* Надо юзеру postgres разрешить доступ только с локальной машины
* Кнопочки "Вернуться в табличный режим" и "Обновить" заменить для мобилы
* Избавиться от $Do и всего такого.


#Что надо сделать еще:

* Надо что-то делать с датой, если она пуста при реализации желания
* Убрать ненужные кнопки не с админской странички. И выключать кнопки когда не нужно (в т.ч. в диалоговых окнах)
* В фильтрацию добавить < / > какой-то суммы, и так же добавить фильтрацию по дате
* Привести в порядок форму парсинга csv
* Ролевые модели
* Нормальный поиск (эвент при нажатии каждой клавиши)
* Надо подумать как сделать тему, какие юзеры ща онлайн.
* Надо подумать на хера мне вообще MainService. Что он делает?
* Прикрутить фотки юзеров и отображение / обрезку в круге (а также желаний)
* Отдельные особенности интерфейса для админа
* Сделать админскую страничку с дашбордом
* Подумать над реализации Калькулятора строительства, то есть отображения как в Гугл-Таблицах
* Надо сделать чтобы можно было сметы сохранять (типа хранение таких как бы табличек, с разными ячейками, кодом ячейки, на который можно ссылаться, ну и так далее...)
* Учет зп/аванса
* Возможность просматривать реализованные желания и проводить какие-то операции с ними
* Сделать обрезку wish-name в датагриде на фронте по длине и еще и наложить ограничения при добавлении по длине
* Надо бы обработать на беке тему с протуханием токенов (примеры я видел)
* Сделать чтобы уже при вводе логина при редактировании пользователя осуществлялся запрос при каждом нажатии и поиск - есть такой юзер или нет
* Подумать что делать если куки запрещены. Может в сессии еще хранить
* Надо запретить юзеру вообще делать какие-либо действия если у него крипто-мод, а крипто-кей не задан или не подгружен.
* Домик и сайдбар почистили, но теперь "оно" вообще никогда не отображается.
* Проблема протухания токена и проблема отображения старого меню юзера для протухшего токена
* Надо все правки желаний утащить в один метод
* В перспективе надо сделать такой дашбоард с виджетом статистики. Типа добавлено в этом месяце, реализовано, удалено, изменено, добавлено на сумму и так далее....
* Почистить код на фронте. Там пиздец просто....

#Отдаленные планы:

* Ссылки
* Публичные списки
* Блог
* Чат
* Разные списки
* Чат-бот для Телеграмма
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





 
