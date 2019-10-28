
# arNote
Сервис хранения желаний, планирования трат, хранения заметок, ссылок и прочего. Сервис обмена.


#Что сделали:

* Исправил косяк с тем, что по сути сервис вообще был мобильно-не-адаптивным
* Косяк с изменением приоритета исчез сам по себе
* Разобрался с размером на ноуте и мобиле
* Поправил косяк, при котором в списке месяцов при перемещении на фронте везде был 2019 год
* Для мобилы теперь выводится отдельная минюшка-модалка с итогами, суммами и почим
* Прикрутил регистрацию

#Ближайшие срочные мелкие косяки и баги:

* Убрать ненужные кнопки не с админской странички. И выключать кнопки когда не нужно (в т.ч. в диалоговых окнах)
* В фильтрацию добавить < / > какой-то суммы, и так же добавить фильтрацию по дате
* Привести в порядок форму парсинга csv
* Поправить косяк, что когда ты только зашел, создал новую запись, у тебя косяки с помесячной группировкой.
* Надо добавлять дату создания при парсинге csv
* Надо что-то делать с датой, если она пуста при реализации желания
* Надо чтобы сортировка сохранялась, а не менялась каждый раз при перемещении записи
* Надо чтобы нельзя было добавлять одинаковые желания
* Написать статью на AntonRomanov.com о сервисе
* Глюк при протухании токена: изменение содержания верхнего меню ("войти/выйти")
* Надо с адмиминской страницы управлять  быкапами базы
* Фавикон
* Выделение и перемещение по месяцам нескольких желаний
* Кнопочки Реализовать / Удалить / Редактировать при нажатии на желание в помесячно-групповом режиме
* Надо юзеру postgres разрешить доступ только с локальной машины
* Помесячное отображение на мобиле глючит из-за слишком длинных имен желаний. Нужно для мобилы это исправить.
* Кнопочки "Вернуться в табличный режим" и "Обновить" заменить для мобилы
* В итоговой модалке надо выводить так же: сколько в архиве, сколько реализовано вообще, сколько реализовано в этом месяце, на какую сумму реализовано всего и на какую сумму в этом месяце


#Что надо сделать еще:

* Изменение курсора мыши при наведении
* Ролевые модели
* Нормальный поиск (эвент при нажатии каждой клавиши)
* Добавить группы (ну, например, одежда)
* Надо подумать как сделать тему, какие юзеры ща онлайн.
* Надо подумать на хера мне вообще MainService. Что он делает?
* Прикрутить фотки юзеров и отображение / обрезку в круге (а также желаний)
* Отдельные особенности интерфейса для админа
* Сделать админскую страничку
* Надо сделать чтобы можно было сметы сохранять (типа хранение таких как бы табличек, с разными ячейками, кодом ячейки, на который можно ссылаться, ну и так далее...)
* Учет зп/аванса
* Возможность просматривать реализованные желания и проводить какие-то операции с ними
* Добавить кнопку (удалить все)
* Сделать обрезку wish-name в датагриде на фронте по длине и еще и наложить ограничения при добавлении по длине
* Надо бы обработать на беке тему с протуханием токенов (примеры я видел)
* Сделать чтобы уже при вводе логина при редактировании пользователя осуществлялся запрос при каждом нажатии и поиск - есть такой юзер или нет
* Подумать что делать если куки запрещены. Может в сессии еще хранить
* Надо запретить юзеру вообще делать какие-либо действия если у него крипто-мод, а крипто-кей не задан или не подгружен.
* Домик и сайдбар почистили, но теперь "оно" вообще никогда не отображается.
* Проблема протухания токена и проблема отображения старого меню юзера для протухшего токена
* Надо все правки желаний утащить в один метод
* Куда делся фавикон?
* В перспективе надо сделать такой дашбоард с виджетом статистики. Типа добавлено в этом месяце, реализовано, удалено, изменено, добавлено на сумму и так далее....

#Отдаленные планы:

* Ссылки
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





 
