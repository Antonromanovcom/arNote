package com.antonromanov.arnote.sex.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO с данными по ЗП для фронта.
 */

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryListRs {
    private List<SalaryRs> salariesList;
    ResponseStatusRs status;
}
