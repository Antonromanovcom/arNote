package com.antonromanov.arnote.domain.user.mapper;

import com.antonromanov.arnote.domain.wish.dto.rq.LocalUserRq;
import com.antonromanov.arnote.domain.wish.dto.rs.LocalUserRs;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper {

    String DEFAULT_USER_VIEW_MODE = "TABLE";

    default Date setDateNow() {
        return new Date();
    }

    default ArNoteUser.Role getDefaultUserRole() {
        return ArNoteUser.Role.USER;
    }

    LocalUserRs mapArnoteUser(ArNoteUser source);

    @Mapping(target = "fullname", source = "request.fullName")
    @Mapping(target = "pwd", source = "pwd")
    @Mapping(target = "viewMode", constant = DEFAULT_USER_VIEW_MODE)
    @Mapping(target = "creationDate", expression = "java(setDateNow())")
    @Mapping(target = "userRole", expression = "java(getDefaultUserRole())")
    ArNoteUser mapLocalUserRq(LocalUserRq request, String pwd);
}
