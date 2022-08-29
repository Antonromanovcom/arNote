package com.antonromanov.arnote.domain.wish.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Входное ДТО для поиска желаний.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchWishRq {
    @NotBlank
    private String wishName;
}
