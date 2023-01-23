package com.antonromanov.arnote.domain.finplanning.loan.mapper;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FullLoanRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.OperateCreditRs;
import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.CreditRs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanRsMapper {

    @Mapping(target = "creditNumber", source = "nextLoanNumber")
    @Mapping(target = "creditsCount", source = "size")
    OperateCreditRs map(Integer nextLoanNumber, Integer size);

    @Mapping(target = "number", source = "creditNumber")
    FullLoanRs mapCreditToFullLoanRs(Credit credit);

    @Mapping(target = "startAmount", source = "amount")
    FullLoanRs mapCreditRsToFullLoanRs(CreditRs credit);

}
