package com.antonromanov.arnote.common.exceptions;

import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;

public class NoDataYetException extends RuntimeException {

	public NoDataYetException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
