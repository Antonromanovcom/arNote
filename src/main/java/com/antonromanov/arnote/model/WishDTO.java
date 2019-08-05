package com.antonromanov.arnote.model;

import lombok.*;

import javax.persistence.*;


@Builder
public class WishDTO {

    private long id;
    private String wish;
    private Integer price;
    private Integer priority;
    private Boolean ac;
    private String description;
    private String url;
    private Integer priorityGroup;
    private Integer priorityGroupOrder;
    private String month;


}

