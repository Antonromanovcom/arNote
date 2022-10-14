package com.antonromanov.arnote.domain.wish.dto;

import com.antonromanov.arnote.domain.investing.dto.response.serializers.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WishAnalyticsRs {

	@JsonProperty("all") //todo: потом переименовать это и тут и на фронте
	private Integer sumOfAllWishes;

	@JsonProperty("priority") //todo: потом переименовать это и тут и на фронте
	private Integer sumOfPriorityWishes;

	@JsonProperty("allPeriodForImplementation") //todo: потом переименовать это и тут и на фронте
	private Integer timeForRealizationAllWishes;

	private Integer priorityPeriodForImplementation;

	private Integer lastSalary;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double averageImplementationTime;

	@JsonProperty("implemetedSummAllTime") //todo: потом переименовать это и тут и на фронте
	private int sumOfImplementedWishesFromTheBeginning; //  реализовали в рублях за все время?

	@JsonProperty("implemetedSummMonth") //todo: потом переименовать это и тут и на фронте
	private int sumOfImplementedWishesForCurrentMonth; // реализовали в рублях в этом месяце?

	private int littleWishes; // маленькие хотелки //todo: удалить????



}
