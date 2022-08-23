package com.antonromanov.arnote.exceptions;

public class JsonParseException  extends Exception {

	private String message;

	public JsonParseException(String json) {
		this.message = "Ошибка парсинга json: " + json;
	}


	public JsonParseException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
