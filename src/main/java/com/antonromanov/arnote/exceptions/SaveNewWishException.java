package com.antonromanov.arnote.exceptions;

public class SaveNewWishException extends Exception {

	private String message;

	public SaveNewWishException(String json) {
		this.message = "Ошибка сохранения / редактирования нового желания с параметрами: " + json;
	}

	public SaveNewWishException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public SaveNewWishException() {
		super();
	}

	public String getMessage() {
		return message;
	}
}
