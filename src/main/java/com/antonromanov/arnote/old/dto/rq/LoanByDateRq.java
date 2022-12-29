package com.antonromanov.arnote.old.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO для запроса кредита по дате.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanByDateRq {
    private Date startDate; // Дата взятия кредита
}
