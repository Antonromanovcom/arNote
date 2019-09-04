package com.antonromanov.arnote.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Builder
public class WishDTO {

    private long id;
    @Getter
    private String wish;
    @Getter
    private Integer price;
    private Integer priority;
    private Boolean ac;
    private String description;
    private String url;
    private Integer priorityGroup;
    private Integer priorityGroupOrder;
    private String month;

}

