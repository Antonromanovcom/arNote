package com.antonromanov.arnote.dto.request;

import lombok.Data;

/**
 * Используется для добавления новой зарплаты.
 */
@Data
public class NewSalaryRq {
    private int fullSalary;
    private int residualSalary;
}
