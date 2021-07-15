package com.antonromanov.arnote.domain.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Входное ДТО для поиска желаний.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRq {
    private String wishName;
}
