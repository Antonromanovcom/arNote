package com.antonromanov.arnote.domain.salary.service.mapper;

import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rs.SalaryRs;
import com.antonromanov.arnote.sex.entity.common.Salary;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface SalaryMapper {

    @Mapping(target = "salarydate", expression = "java(setDateNow())")
    @Mapping(target = "id", source = "request.id")
    Salary mapSalaryRq(SalaryRq request, ArNoteUser user);


    default Date setDateNow() {
        return new Date();
    }

    default ArNoteUser.Role getDefaultUserRole() {
        return ArNoteUser.Role.USER;
    }

    @Mapping(target = "salaryDate", source = "salary.salarydate")
    SalaryRs mapSalaryRs(Salary salary);
}
