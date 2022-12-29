package com.antonromanov.arnote.domain.salary.service.impl;

import com.antonromanov.arnote.domain.salary.service.SalaryService;
import com.antonromanov.arnote.domain.salary.service.mapper.SalaryMapper;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rs.SalaryRs;
import com.antonromanov.arnote.sex.entity.common.Salary;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.domain.salary.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryMapper mapper;
    private final UserService userService;
    private final SalaryRepository salaryRepository;

    @Override
    public Integer getLastSalary(ArNoteUser user) {
        return salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst()
                .map(Salary::getResidualSalary).orElse(0);
    }

    @Override
    public Optional<Salary> getLastSalaryListByUserDesc(ArNoteUser user) {
        return salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst();
    }


    @Override
    public SalaryRs addSalary(SalaryRq request) {
        ArNoteUser user = userService.getUserFromPrincipal();
        return mapper.mapSalaryRs(salaryRepository.saveAndFlush(mapper.mapSalaryRq(request, user)));
    }
}