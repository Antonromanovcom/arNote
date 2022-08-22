package com.antonromanov.arnote.exceptions;


import static com.antonromanov.arnote.exceptions.BadIncomeParameter.ParameterKind.*;

public class BadIncomeParameter extends Exception {

	private String message;

	public enum ParameterKind {PRIORITYCHANGE, WISH_ID_SEARCH, WRONG_ID, SUCH_USER_EXIST, SUCH_USER_NO_EXIST,
		NOTHING_FOUND, WRONG_MONTH, WRONG_SORT_TYPE, WRONG_PARAMETER} //todo: вынести в отдельный класс и типа ответных сообщений засунуть сюда.
	// public enum ParameterKind {PRIORITYCHANGE, WISH_ID_SEARCH, WRONG_ID, SUCH_USER_EXIST, SUCH_USER_NO_EXIST, NOTHING_FOUND, WRONG_MONTH, WRONG_PARAMETER}

	public BadIncomeParameter(ParameterKind parameter) {

		if (parameter == PRIORITYCHANGE) { //todo: вот этот весь пиздец конечно поменять надо!!!!
			this.message = "Ошибка изменения приоритета: тип повышения может быть только 'up' или 'down'";
		} else if (parameter == WISH_ID_SEARCH) {
				this.message = "Ошибка поиска желания: желания с таким id не существует";
		} else if (parameter == WRONG_ID) {
			this.message = "ID должен быть цифрой и не пустым заначением!";
		} else if (parameter == SUCH_USER_EXIST) {
			this.message = "SUCH_USER_EXIST";
		} else if (parameter == SUCH_USER_NO_EXIST) {
			this.message = "Пользователя с таким ID не существует!";
		} else if (parameter == WRONG_MONTH) {
			this.message = "Неверно задан месяц при изменении помесячной группировки!";
		} else if (parameter == NOTHING_FOUND) {
			this.message = "NOTHING_FOUND";
		} else if (parameter == WRONG_SORT_TYPE) {
			this.message = "Ошибочный тип сортировки!";
		} else {
			this.message = "Ошибочный входной параметр";
		}
	}

	public String getMessage() {
		return message;
	}
}
