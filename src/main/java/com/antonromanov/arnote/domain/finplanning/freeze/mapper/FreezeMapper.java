package com.antonromanov.arnote.domain.finplanning.freeze.mapper;

import com.antonromanov.arnote.domain.finplanning.freeze.dto.rq.FreezeRq;
import com.antonromanov.arnote.domain.finplanning.freeze.entity.Freeze;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDate;
import java.util.Date;
import static com.antonromanov.arnote.old.utils.ArNoteUtils.localDateToDate;

@Mapper(componentModel = "spring")
public interface FreezeMapper {


    @Mapping(target = "amount", source = "payload.amount")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "startDate", source = "payload", qualifiedByName = "fillDate")
    Freeze map(FreezeRq payload, ArNoteUser user);


    @Named("fillDate")
    default Date fillDate(FreezeRq payload) {
        return localDateToDate(LocalDate.of(payload.getYear(), payload.getMonth(), 1));
    }
}
