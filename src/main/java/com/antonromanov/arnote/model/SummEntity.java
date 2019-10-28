package com.antonromanov.arnote.model;

import lombok.Builder;

@Builder
public class SummEntity {
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
