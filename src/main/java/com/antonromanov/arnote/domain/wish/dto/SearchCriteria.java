package com.antonromanov.arnote.domain.wish.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

	private String key;
	private String operation;
	private Object value;

	public SearchCriteria(String group, String group1, String group2) {
		this.key = group;
		this.operation = group1;
		this.value = group2;
	}


}
