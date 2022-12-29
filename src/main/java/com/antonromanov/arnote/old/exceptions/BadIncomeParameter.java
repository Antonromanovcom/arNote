package com.antonromanov.arnote.old.exceptions;


import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;

public class BadIncomeParameter extends RuntimeException {
	public BadIncomeParameter(ErrorCodes code) {
		super(code.getUiCode());
	}
}
