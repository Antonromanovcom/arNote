
# arNote
Сервис хранения желаний, планирования трат, хранения заметок, ссылок и прочего. Сервис обмена.


#Что сделали:

* Починил косяк с нулевыми датами (вываливался gson-builder)
* Починил косяк стирания месячной группировки при правке желания
* Сделал сортировку 
* Cохранение даты и времени последнего захода за юзером
* Возможность быстрого изменения помесячного порядка желания
* Добавил отображение даты создания желания (или в табличке или в редактировании) 

#Ближайшие срочные мелкие косяки и баги:

* Нормальный поиск (эвент при нажатии каждой клавиши)
* Убрать ненужные кнопки не с админской странички. И выключать кнопки когда не нужно (в т.ч. в диалоговых окнах)
* В фильтрацию добавить < / > какой-то суммы, и так же добавить фильтрацию по дате
* Привести в порядок форму парсинга csv
* Прикрутить регистрацию


#Что надо сделать еще:

* Изменение курсора мыши при наведении
* Ролевые модели
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

#Отдлаенные планы (после-релизные):

* Ссылки
* Чат
* Разные списки





 
