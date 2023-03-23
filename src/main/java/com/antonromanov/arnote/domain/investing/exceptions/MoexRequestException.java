package com.antonromanov.arnote.domain.investing.exceptions;

public class MoexRequestException extends RuntimeException {

	private String message;

	public MoexRequestException() {
		this.message = "Ошибка отправки запроса к API биржи!";
	}

	public String getMessage() {
		return message;
	}
}
