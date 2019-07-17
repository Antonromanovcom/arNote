package com.antonromanov.arnote.utils;

import com.antonromanov.arnote.model.SearchCriteria;
import lombok.Getter;
import javax.persistence.criteria.*;
import java.util.function.Consumer;

public class WishSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

	@Getter
	private Predicate predicate;
	private CriteriaBuilder builder;
	private Root r;

	public WishSearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root r) {
		this.predicate = predicate;
		this.builder = builder;
		this.r = r;
	}



	@Override
	public void accept(SearchCriteria param) {

		if (param.getOperation().equalsIgnoreCase(">")) {
			predicate = builder.and(predicate, builder
					.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
		} else if (param.getOperation().equalsIgnoreCase("<")) {
			predicate = builder.and(predicate, builder.lessThanOrEqualTo(
					r.get(param.getKey()), param.getValue().toString()));
		} else if (param.getOperation().equalsIgnoreCase(":")) {
			if (r.get(param.getKey()).getJavaType() == String.class) {
				predicate = builder.and(predicate, builder.like(
						r.get(param.getKey()), "%" + param.getValue() + "%"));
			} else {
				predicate = builder.and(predicate, builder.equal(
						r.get(param.getKey()), param.getValue()));
			}
		}


	}


}
