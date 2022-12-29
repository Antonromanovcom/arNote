package com.antonromanov.arnote.domain.finplanning.loan.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * DTO для выдачи свободных слотов по кредитам.
 */
@Data
@Builder
@AllArgsConstructor
public class FreeLoanSlotsRs {
    private Integer allLoansCount; // всего кредитов
    private List<Integer> openSlots; // открытые слоты - список номеров, куда можно вписаться.
}
