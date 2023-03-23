package com.antonromanov.arnote.common.exceptions;


import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;

public class BadIncomeParameter extends RuntimeException {
	public BadIncomeParameter(ErrorCodes code) {
		super(code.getUiCode());
	}
}
