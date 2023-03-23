package com.antonromanov.arnote.common.exceptions;

import static com.antonromanov.arnote.common.exceptions.enums.ErrorCodes.ERR_O9;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super(ERR_O9.getUiCode());
	}
}
