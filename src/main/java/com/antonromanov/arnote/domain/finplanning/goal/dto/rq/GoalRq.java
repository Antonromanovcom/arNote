package com.antonromanov.arnote.domain.finplanning.goal.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

/**
 * DTO для добавления нового расхода / цели
 */
@Data
@Builder
@AllArgsConstructor
public class GoalRq {
    private String description; // Описание
    private Integer price; // Стоимость
    private Date startDate; //  Дата покупки
    private Long repayment; // ID кредита по которому выполняется досрочное погашение.
    private Long id; // ID расхода.
}
