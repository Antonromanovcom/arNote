package com.antonromanov.arnote.dto.response;

import lombok.Builder;

@Builder
public class ResponseParseResult {
	private String status;
	private String errorMessage;
	private String okMessage;
	private Integer itemsAdded;
}
