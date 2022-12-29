package com.antonromanov.arnote.sex.exceptions;

import com.antonromanov.arnote.sex.exceptions.enums.ErrorCodes;

public class FinPlanningException extends RuntimeException {

	public FinPlanningException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
