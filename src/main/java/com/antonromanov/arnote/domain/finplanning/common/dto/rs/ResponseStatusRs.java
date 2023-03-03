package com.antonromanov.arnote.domain.finplanning.common.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseStatusRs {
    private int code;
    private String description;
    private String status;
}