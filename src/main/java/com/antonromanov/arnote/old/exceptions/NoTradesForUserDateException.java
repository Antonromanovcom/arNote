package com.antonromanov.arnote.old.exceptions;

import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;

/**
 * Ошибка возникающая, если пользак запрашивает данные торгов на выходной, праздник или просто на этот день нет торгов.
 */
public class NoTradesForUserDateException extends Exception {


	private ErrorCodes code;

	public NoTradesForUserDateException(ErrorCodes code) {
		this.code = code;
	}


	public NoTradesForUserDateException() {
		super();
	}

	public ErrorCodes getCode() {
		return code;
	}
}
