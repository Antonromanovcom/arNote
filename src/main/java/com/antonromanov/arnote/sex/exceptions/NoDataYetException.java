package com.antonromanov.arnote.sex.exceptions;

import com.antonromanov.arnote.sex.exceptions.enums.ErrorCodes;

public class NoDataYetException extends RuntimeException {

	public NoDataYetException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
