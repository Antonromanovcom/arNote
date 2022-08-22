package com.antonromanov.arnote.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
public class WishResponse {

    @Getter
    private final long id;
    @Getter
    private final String wish;
    @Getter
    private final Integer price;
    private final Integer priority;
    private final Boolean ac;
    private final String description;
    private final String url;
    private final Integer priorityGroup;
    private final Integer priorityGroupOrder;
    private final String month;

}

