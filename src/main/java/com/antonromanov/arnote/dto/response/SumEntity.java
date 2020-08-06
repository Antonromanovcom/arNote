package com.antonromanov.arnote.dto.response;

import lombok.Builder;

@Builder
public class SumEntity {
	private Integer all;
	private Integer priority;
	private Integer allPeriodForImplementation;
	private Integer priorityPeriodForImplementation;
	private Integer lastSalary;
	private int averageImplementationTime;
	private int implemetedSummAllTime; // На сколько реализовали всего
	private int implemetedSummMonth; // На сколько реализовали в этом месяце
	private int littleWishes; // маленькие хотелки


}
