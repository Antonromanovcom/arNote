package com.antonromanov.arnote.sex.dto.response.monthgroupping;

import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import lombok.Builder;
import lombok.Data;
import java.util.List;


/**
 * Группа желаний в рамках одного месяца.
 */
@Builder
@Data
public class GroupOfWishesForOneMonth {

    private List<WishRs> wishList;
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

