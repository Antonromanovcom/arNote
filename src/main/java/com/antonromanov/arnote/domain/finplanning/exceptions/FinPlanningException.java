package com.antonromanov.arnote.domain.finplanning.exceptions;

import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;

public class FinPlanningException extends RuntimeException {

	public FinPlanningException(ErrorCodes code) {
		super(code.getUiCode());
	}
}
