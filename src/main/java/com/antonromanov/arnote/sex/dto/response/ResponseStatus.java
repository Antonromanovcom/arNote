package com.antonromanov.arnote.dto.response;

import lombok.Builder;

@Builder
public class ResponseStatus {
	private String status;
	private String errorMessage;
	private String okMessage;
}
