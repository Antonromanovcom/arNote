package com.antonromanov.arnote.model;

import lombok.*;
import java.util.List;


@Builder
public class WishDTOList {

    @Getter
    List<WishDTO> wishList;
    String monthName;
    String year;
    int monthNumber;
    int colspan;
    int sum;
    boolean overflow;
    String colorClass;
    boolean expanded;
}

