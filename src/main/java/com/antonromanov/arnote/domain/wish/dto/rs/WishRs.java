package com.antonromanov.arnote.domain.wish.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class WishRs {

    private  long id;
    private  String wish;
    private  Integer price;
    private  Integer priority;
    private  Boolean ac;
    private  String description;
    private  String url;
    private  Integer priorityGroup;
    private  Integer priorityGroupOrder;
    private  String month;

}

