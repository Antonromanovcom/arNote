package com.antonromanov.arnote.sbdfvjbsdf.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public enum UserGlobalState {

    START("/start", "Начало сессии и выбор режима", null), DOMAIN("/domain", "Выбор домена", null), STOP("/stop", "Окончание сессии", null);

    private final String command;
    private final String description;
    private final List <UserDomainState> domains;

   /* public static CalendarType handle(String id){
        return Arrays.stream(CalendarType.values())
                .filter(e->e.id == Integer.parseInt(id))
                .findFirst()
                .orElse(null);
    }*/
}
