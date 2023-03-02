package com.antonromanov.arnote.domain.finplanning.goal.mapper.rq;

import com.antonromanov.arnote.domain.finplanning.goal.dto.rq.GoalRq;
import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.old.model.ArNoteUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GoalMapperRq {

    @Mapping(target = "id", source = "payload.id")
    @Mapping(target = "description", source = "payload.description")
    @Mapping(target = "price", source = "payload.price")
    @Mapping(target = "startDate", source = "payload.startDate")
    @Mapping(target = "repayment", source = "payload.repayment")
    @Mapping(target = "user", source = "user")
    Goal map(GoalRq payload, ArNoteUser user);
}
