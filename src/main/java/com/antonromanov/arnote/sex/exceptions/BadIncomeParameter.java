package com.antonromanov.arnote.sex.exceptions;


import com.antonromanov.arnote.sex.exceptions.enums.ErrorCodes;

public class BadIncomeParameter extends RuntimeException {
	public BadIncomeParameter(ErrorCodes code) {
		super(code.getUiCode());
	}
}
