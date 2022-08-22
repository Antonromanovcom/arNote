package com.antonromanov.arnote.dto.response.monthgroupping;

import com.antonromanov.arnote.dto.response.monthgroupping.GroupOfWishesForOneMonth;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * Response-список месяцов с желаниями внутри.
 */
@Data
@Builder
public class ListOfMonthsResponse {
    private List<GroupOfWishesForOneMonth> list;
}
