package com.antonromanov.arnote.exceptions;

import com.antonromanov.arnote.exceptions.enums.ErrorCodes;

public class AddNewCreditException extends RuntimeException {

	private ErrorCodes code;

	public AddNewCreditException() {
		this.code = ErrorCodes.ERR_O4;
	}

	public ErrorCodes getCode() {
		return code;
	}
}
