package com.antonromanov.arnote.enums;

import com.antonromanov.arnote.dto.response.WishResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Comparator;

/**
 * Режим сортировки.
 */
@AllArgsConstructor
@Getter
public enum SortMode {
    NAME(Comparator.comparing(WishResponse::getWish)),
    PRICE_ASC(Comparator.comparing(WishResponse::getPrice)),
    PRICE_DESC(Comparator.comparing(WishResponse::getPrice).reversed()),
    ALL(Comparator.comparing(WishResponse::getId));

   private final Comparator<WishResponse> comparing;
}

