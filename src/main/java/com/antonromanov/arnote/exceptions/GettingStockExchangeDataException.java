package com.antonromanov.arnote.exceptions;

public class GettingStockExchangeDataException extends RuntimeException {

	private String message;

	public GettingStockExchangeDataException() {
		this.message = "Ошибка получения / парсинга данных от биржи!";
	}

	public String getMessage() {
		return message;
	}
}
