package com.antonromanov.arnote.domain.investing.exceptions;

import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;

public class InvestingException extends RuntimeException {

	public InvestingException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
