package com.antonromanov.arnote.domain.salary.service;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.salary.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryListRs;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryRs;
import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.model.ArNoteUser;
import java.util.Optional;

public interface SalaryService {
    SalaryRs addSalary(SalaryRq request);

    Integer getLastSalary(ArNoteUser user);

    Optional<Salary> getLastSalaryListByUserDesc(ArNoteUser user);

    Optional<Salary> getClosestSalary(int year, int currMonth);

    Integer getMonthlySpending(int year, int currMonth);

    SalaryListRs getSalariesList();

    SingleOperationRs editSalary(SalaryRq payload);

    SingleOperationRs deleteSalary(Long id);

}
