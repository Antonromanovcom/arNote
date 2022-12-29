package com.antonromanov.arnote.old.exceptions;

import static com.antonromanov.arnote.old.exceptions.enums.ErrorCodes.ERR_O9;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super(ERR_O9.getUiCode());
	}
}
