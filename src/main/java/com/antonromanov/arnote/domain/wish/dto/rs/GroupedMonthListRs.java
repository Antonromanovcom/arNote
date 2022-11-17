package com.antonromanov.arnote.domain.wish.dto.rs;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class GroupedMonthListRs {
    private List<ListOfGroupedWishesRs> months;
}

