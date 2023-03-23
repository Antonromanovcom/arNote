package com.antonromanov.arnote.domain.salary.mapper;

import com.antonromanov.arnote.domain.salary.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryRs;
import com.antonromanov.arnote.domain.salary.entity.Salary;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface SalaryMapper {

    @Mapping(target = "salarydate", expression = "java(setDateNow())")
    @Mapping(target = "salaryTimeStamp", expression = "java(setTimestampNow())")
    @Mapping(target = "id", source = "request.id")
    Salary mapSalaryRq(SalaryRq request, ArNoteUser user);


    @Mapping(target = "id", source = "salaryInDb.id")
    @Mapping(target = "fullSalary", source = "request.fullSalary")
    @Mapping(target = "residualSalary", source = "request.residualSalary")
    @Mapping(target = "livingExpenses", source = "request.livingExpenses")
    Salary mapForUpdateSalaryRq(SalaryRq request, Salary salaryInDb, ArNoteUser user);


    @AfterMapping
    default void injectTimeStamp(@MappingTarget Salary target, Date salaryDate, Salary salaryInDb) {
        target.setSalaryTimeStamp(salaryDate == null ? salaryInDb.getSalaryTimeStamp() : (salaryDate).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
    }


    default Date setDateNow() {
        return new Date();
    }

    default LocalDateTime setTimestampNow() {
        return (new Date()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Mapping(target = "salaryDate", source = "salary.salarydate")
    SalaryRs mapSalaryRs(Salary salary);
}
