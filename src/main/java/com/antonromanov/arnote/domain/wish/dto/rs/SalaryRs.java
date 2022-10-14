package com.antonromanov.arnote.domain.wish.dto.rs;

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
@AllArgsConstructor //todo: перенести в пакет com.antonromanov.arnote.domain.salary.dto.rs;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryRs {
    private Long id;
    private Integer fullSalary; // полная зарплата
    private Integer residualSalary; // зарплата после трат различных
    private Integer livingExpenses; // траты на жизнь: расходы на еду, транспорт и прочее
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date salaryDate;
}
