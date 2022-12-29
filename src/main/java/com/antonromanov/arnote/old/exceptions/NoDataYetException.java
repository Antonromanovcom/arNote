package com.antonromanov.arnote.old.exceptions;

import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;

public class NoDataYetException extends RuntimeException {

	public NoDataYetException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
