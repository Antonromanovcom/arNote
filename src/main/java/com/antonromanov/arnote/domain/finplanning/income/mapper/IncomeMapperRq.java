package com.antonromanov.arnote.domain.finplanning.income.mapper;

import com.antonromanov.arnote.domain.finplanning.income.dto.IncomeRq;
import com.antonromanov.arnote.domain.finplanning.income.entity.Income;
import com.antonromanov.arnote.old.model.ArNoteUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncomeMapperRq {

    @Mapping(target = "description", source = "payload.desc")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", source = "payload.id")
    @Mapping(target = "incomeDate", source = "payload.incomeDate")
    @Mapping(target = "isBonus", source = "payload.isBonus")
    Income map(IncomeRq payload, ArNoteUser user);
}
