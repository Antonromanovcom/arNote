package com.antonromanov.arnote.domain.finplanning.loan.mapper;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.GroupedWishRs;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import com.antonromanov.arnote.domain.wish.dto.rs.SalaryRs;
import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import com.antonromanov.arnote.domain.wish.entity.Wish;
import com.antonromanov.arnote.sex.entity.common.Salary;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.sex.utils.ArNoteUtils.computerMonthName;

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
