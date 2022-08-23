package com.antonromanov.arnote.exceptions;

public class JsonNullException extends Exception {

	private String message;

	public JsonNullException(String message) {
		this.message = message;
	}

	public JsonNullException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


}
