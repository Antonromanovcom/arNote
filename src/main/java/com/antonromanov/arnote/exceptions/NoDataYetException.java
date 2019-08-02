package com.antonromanov.arnote.exceptions;

public class NoDataYetException extends Exception {

	private String message;

	public NoDataYetException(Boolean switcher) {
		if (switcher){
			this.message = "ERR-01";	// Еще нет желаний
		} else {
			this.message = "ERR-02"; // Еще нет зарплаты
		}
	}

	public NoDataYetException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public NoDataYetException() {
		super();
	}

	public String getMessage() {
		return message;
	}
}
