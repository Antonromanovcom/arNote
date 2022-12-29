package com.antonromanov.arnote.domain.finplanning.loan.mapper;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.old.model.ArNoteUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanRqMapper {

    @Mapping(target = "startAmount", source = "rq.startAmount")
    @Mapping(target = "fullPayPerMonth", source = "rq.fullPayPerMonth")
    @Mapping(target = "realPayPerMonth", source = "rq.realPayPerMonth")
    @Mapping(target = "creditNumber", source = "nextLoanNumber")
    @Mapping(target = "startDate", source = "rq.startDate")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "rq.desc")
    @Mapping(target = "id", source = "rq.id")
    Credit map(CreditRq rq, int nextLoanNumber, ArNoteUser user);
}
