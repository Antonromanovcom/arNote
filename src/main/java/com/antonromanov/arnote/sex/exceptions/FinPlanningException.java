package com.antonromanov.arnote.exceptions;

public class FinPlanningException extends RuntimeException {

	private String message;

	public FinPlanningException() {
		this.message = "Ошибка построения консолидированной таблицы фин-планирования!";
	}

	public String getMessage() {
		return message;
	}
}
