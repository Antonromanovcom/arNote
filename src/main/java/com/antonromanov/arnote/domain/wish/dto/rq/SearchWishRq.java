package com.antonromanov.arnote.domain.wish.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Входное ДТО для поиска желаний.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchWishRq {
    private String wishName;
}
