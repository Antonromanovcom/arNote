package com.antonromanov.arnote.sex.dto.rq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * DTO с данными по ЗП для фронта.
 */

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryRq {
    private Long id;
    private Integer fullSalary; // полная зарплата
    private Integer residualSalary; // зарплата после трат различных
    private Integer livingExpenses; // траты на жизнь: расходы на еду, транспорт и прочее
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date salaryDate; // траты на жизнь: расходы на еду, транспорт и прочее
}
