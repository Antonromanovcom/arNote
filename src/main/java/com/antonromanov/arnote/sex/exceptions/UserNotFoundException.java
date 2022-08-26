package com.antonromanov.arnote.sex.exceptions;

import static com.antonromanov.arnote.sex.exceptions.enums.ErrorCodes.ERR_O9;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super(ERR_O9.getUiCode() + " | "+ERR_O9.getDescription());
	}
}
