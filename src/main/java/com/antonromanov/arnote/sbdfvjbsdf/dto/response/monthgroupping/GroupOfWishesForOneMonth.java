package com.antonromanov.arnote.dto.response.monthgroupping;

import com.antonromanov.arnote.dto.response.WishResponse;
import lombok.Builder;
import lombok.Data;
import java.util.List;


/**
 * Группа желаний в рамках одного месяца.
 */
@Builder
@Data
public class GroupOfWishesForOneMonth {

    private List<WishResponse> wishList;
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

