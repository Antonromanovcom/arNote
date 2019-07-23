package com.antonromanov.arnote.Exceptions;

import static com.antonromanov.arnote.Exceptions.BadIncomeParameter.ParameterKind.*;

public class BadIncomeParameter extends Exception {

	private String message;

	public enum ParameterKind {PRIORITYCHANGE, WISH_ID_SEARCH, WRONG_ID, SUCH_USER_EXIST}

	public BadIncomeParameter(ParameterKind parameter) {

		if (parameter == PRIORITYCHANGE) {
			this.message = "Ошибка изменения приоритета: тип повышения может быть только 'up' или 'down'";
		} else if (parameter == WISH_ID_SEARCH) {
				this.message = "Ошибка поиска желания: желания с таким id не существует";
		} else if (parameter == WRONG_ID) {
			this.message = "ID должен быть цифрой и не пустым заначением!";
		} else if (parameter == SUCH_USER_EXIST) {
			this.message = "Этот логин уже занят!";
		} else {
			this.message = "Ошибочный входной параметр";
		}
	}


	public String getMessage() {
		return message;
	}


}
