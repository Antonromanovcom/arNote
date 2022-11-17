package com.antonromanov.arnote.domain.wish.dto.rs;

import lombok.*;
import java.util.List;

@Builder
@Data
public class ListOfGroupedWishesRs {

    private List<GroupedWishRs> wishes;
    private String monthName;
    private String year;
    private int monthNumber;
    private int colspan;
    private int sum;
    private boolean overflow;
    private String colorClass;
    private boolean expanded;
    private int balance; // набегающий баланс
}

