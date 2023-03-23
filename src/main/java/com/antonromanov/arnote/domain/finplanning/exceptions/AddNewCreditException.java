package com.antonromanov.arnote.domain.finplanning.exceptions;

import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;

public class AddNewCreditException extends RuntimeException {

	private ErrorCodes code;

	public AddNewCreditException() {
		this.code = ErrorCodes.ERR_O4;
	}

	public ErrorCodes getCode() {
		return code;
	}
}
