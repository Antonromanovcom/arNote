package com.antonromanov.arnote.domain.wish.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishRs {

    private  long id;
    private  String wishName;
    private  Integer price;
    private  Integer priority;
    private  Boolean archive;
    private  String description;
    private  String url;
    private  Integer priorityGroup;
    private  Integer priorityGroupOrder;

}

