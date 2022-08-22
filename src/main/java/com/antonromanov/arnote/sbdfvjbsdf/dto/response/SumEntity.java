package com.antonromanov.arnote.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Статистическая информация по желаниям.
 */
@Builder
@Data
public class SumEntity {
	private Integer all;
	private Integer priority;
	private Integer allPeriodForImplementation;
	private Integer priorityPeriodForImplementation;
	private Integer lastSalary;
	private int averageImplementationTime;
	private int implementedSumAllTime; // На сколько реализовали всего
	private int implementedSumMonth; // На сколько реализовали в этом месяце
	private int littleWishes; // маленькие хотелки


}
