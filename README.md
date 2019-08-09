
# arNote
Сервис хранения желаний, планирования трат, хранения заметок, ссылок и прочего. Сервис обмена.


#Что сделали:

* Причесал дизайн
* Сделал реакцию на нажатие кнопок изменения приоритета
* Сделал авторизацию
* Сделал шифрование на фронте (пока только самих желаний, без стоимости, дескрипшена и урла)
* ПЕРЕБРОС НА СТРАНИЦУ НЕ ЗАЛОГИНЕННЫХ ЮЗЕРОВ при разлогировании
* Сделал обработку таких событий как пустой список желаний у юзера или незаполненная зп.
* Скрыл кнопку "ПАРСИНГ CSV" для всех кроме меня.
* Сделал поиск
* Сделал версию для мобильников
* Разобрался, почему грид нормально не отображается на мобиле
* Отказался от сортировки
* Авторизацию в GoogleDoc решил не делать
* Поиск, фильтрация
* Научился сохранять режимы отображения
* Некорректно дома на монике отображалась ширина таблицы - поправил.
* Сделал распределение по группам, месяцам, учет аванса/зп
* Раскраску приоритета решил не делать
* Сделал отключение кнопки помесячной группировки при незаполненной зарплате
* Убрал кнопку Войти при разлогировании
* Добавлена кнопки возврата в нормальный режим после "дерева" помесячного


#Ближайшие срочные мелкие косяки и баги:

* Дату добавления
* Добавить шифрование стоимости, дискрипшена и урла
* Добавить разделение разрядов в цифры (!!!!)
* Нужно убрать id при добавлении желания
* Если юзер не залогинен кнопку домика и "Главная" вообще на хер убрать надо
* Убрать отображаемый сайднав для мобилок
* Прикрутить шифрование к цене, ссылке, описанию
* Перенести обрезку длины на фронт
* Прикрутить шифрование к помесячному дереву
* Сделать стирание ключа шифрования при смене пользователя


#Что надо сделать еще:

* Вместо удаления - помещение в архив
* Через сколько реализована задача после добавление, сколько дней висит, средняя скорость реализации, динамику скорости реализации
* Добавить флажок "Реализована" со всеми вытекающими
* Учет зп/аванса
* Добавить кнопку (удалить все)
* Добавить группы (ну, например, одежда)
* Сделать обрезку wish-name в датагриде на фронте по длине и еще и наложить ограничения при добавлении по длине
* Ролевые модели
* Отдельные особенности интерфейса для админа
* Надо бы обработать на беке тему с протуханием токенов (примеры я видел)
* Сделать чтобы уже при вводе логина при редактировании пользователя осуществлялся запрос при каждом нажатии и поиск - есть такой юзер или нет
* Подумать что делать если куки запрещены. Может в сессии еще хранить
* Сделать админскую страничку
* Убрать ненужные кнопки не с админской странички. И выключать кнопки когда не нужно (в т.ч. в диалоговых окнах)
* В фильтрацию добавить < / > какой-то суммы, и так же добавить фильтрацию по дате
* Привести в порядок форму парсинга csv
* Надо бы еще подумать как сохранять дату и время последнего захода за юзером
* Надо запретить юзеру вообще делать какие-либо действия если у него крипто-мод, а крипто-кей не задан или не подгружен.

#Отдлаенные планы (после-релизные):

* Ссылки
* Чат
* Разные списки
* Желательно бы логировать при неверной аутентификации
* Прикрутить фотки юзеров и отображение / обрезку в круге




 
