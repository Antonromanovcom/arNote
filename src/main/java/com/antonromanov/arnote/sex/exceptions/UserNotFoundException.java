package com.antonromanov.arnote.exceptions;

public class UserNotFoundException extends Exception {

	private String message;

	public UserNotFoundException(String email) {
		this.message = "Пользователь с email - " + email + "не найден! ";
	}

	public UserNotFoundException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public UserNotFoundException() {
		super();
	}

	public String getMessage() {
		return message;
	}
}
