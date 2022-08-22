package com.antonromanov.arnote.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Данные по кредитам
 */
@Data
@Builder
@AllArgsConstructor
// @JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditListRs {
    Integer credit1;
    Integer credit2;
    Integer credit3;
    Integer credit4;
    Integer credit5;
    private List<CreditRs> credits;
}
