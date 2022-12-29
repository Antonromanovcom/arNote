package com.antonromanov.arnote.old.exceptions;

import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;

public class FinPlanningException extends RuntimeException {

	public FinPlanningException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
