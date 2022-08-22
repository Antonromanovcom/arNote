package com.antonromanov.arnote.sbdfvjbsdf.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

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
