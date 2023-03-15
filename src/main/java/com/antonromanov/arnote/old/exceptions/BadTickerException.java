package com.antonromanov.arnote.old.exceptions;

public class BadTickerException extends RuntimeException {

	private String message;

	public BadTickerException(String ticker) {
		this.message = "Попытка добавить инструмент по тикеру, который не существует! Тикер - " + ticker;
	}

	public String getMessage() {
		return message;
	}
}
