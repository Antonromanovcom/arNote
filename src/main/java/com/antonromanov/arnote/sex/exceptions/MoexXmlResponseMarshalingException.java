package com.antonromanov.arnote.exceptions;

public class MoexXmlResponseMarshalingException extends RuntimeException {

	private String message;

	public MoexXmlResponseMarshalingException() {
		this.message = "Ошибка маршеллинга данных от биржи!";
	}

	public String getMessage() {
		return message;
	}
}
