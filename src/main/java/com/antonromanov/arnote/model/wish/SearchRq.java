package com.antonromanov.arnote.model.wish;

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
