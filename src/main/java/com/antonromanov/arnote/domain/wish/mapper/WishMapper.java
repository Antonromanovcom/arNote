package com.antonromanov.arnote.domain.wish.mapper;

import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.GroupedWishRs;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import com.antonromanov.arnote.domain.wish.dto.rs.SalaryRs;
import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import com.antonromanov.arnote.sex.entity.common.Salary;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.domain.wish.entity.Wish;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.sex.utils.ArNoteUtils.computerMonthName;

@Mapper(componentModel = "spring")
public interface WishMapper {

    String DEFAULT_USER_VIEW_MODE = "TABLE";

    WishRs mapWish (Wish source);

    default List<WishRs> mapWishList (List<Wish> wishList){
        return wishList.stream()
                .map(this::mapWish)
                .collect(Collectors.toList());
    }

    Wish map(WishRq wish);

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


    LocalUserRs mapArnoteUser(ArNoteUser source);

    @Mapping(target = "fullname", source = "request.fullName")
    @Mapping(target = "pwd", source = "pwd")
    @Mapping(target = "viewMode", constant = DEFAULT_USER_VIEW_MODE)
    @Mapping(target = "creationDate", expression = "java(setDateNow())")
    @Mapping(target = "userRole", expression = "java(getDefaultUserRole())")
    ArNoteUser mapLocalUserRq(LocalUserRq request, String pwd);

    @Mapping(target = "wish", source = "wish.wishName")
    GroupedWishRs mapWishForGroupedList(Wish wish);


    @AfterMapping
    default void injectMonth(@MappingTarget GroupedWishRs target, Wish wish, int maxPrior) {
        target.setMonth(computerMonthName(wish.getPriorityGroup() == null ? maxPrior : wish.getPriorityGroup()));
    }
}
