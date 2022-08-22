package com.antonromanov.arnote.sbdfvjbsdf.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public enum UserDomainState {
    WISHES("/wishes", "Работа с желаниями", null), FINPLANNING("/fp", "ФинПланы", null);


    private final String command;
    private final String description;
    private final List<SubDomain> subDomains;

   /* public static CalendarType handle(String id){
        return Arrays.stream(CalendarType.values())
                .filter(e->e.id == Integer.parseInt(id))
                .findFirst()
                .orElse(null);
    }*/
}
