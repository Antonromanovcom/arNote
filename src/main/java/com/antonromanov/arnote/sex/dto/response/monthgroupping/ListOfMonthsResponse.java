package com.antonromanov.arnote.sex.dto.response.monthgroupping;

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
