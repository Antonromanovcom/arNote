package com.antonromanov.arnote.old.exceptions;

import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;

public class InvestingException extends RuntimeException {

	public InvestingException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
