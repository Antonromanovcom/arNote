package com.antonromanov.arnote.model;

import lombok.Builder;

@Builder
public class SummEntity {
	private Integer all;
	private Integer priority;
	private Integer allPeriodForImplementation;
	private Integer priorityPeriodForImplementation;
	private Integer lastSalary;
}
