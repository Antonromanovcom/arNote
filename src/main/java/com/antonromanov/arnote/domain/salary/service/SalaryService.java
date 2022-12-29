package com.antonromanov.arnote.domain.salary.service;

import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rs.SalaryRs;
import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.model.ArNoteUser;
import java.util.Optional;

public interface SalaryService {
    SalaryRs addSalary(SalaryRq request);
    Integer getLastSalary(ArNoteUser user);
    Optional<Salary> getLastSalaryListByUserDesc(ArNoteUser user);
}
