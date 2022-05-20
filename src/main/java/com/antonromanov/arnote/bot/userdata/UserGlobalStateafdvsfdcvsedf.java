package com.antonromanov.arnote.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserGlobalStateafdvsfdcvsedf {

    START("/start", "Начало сессии и выбор режима", 0, 1),
    DOMAIN("/domain", "Выбор домена", 0, 2),
    STOP("/stop", "Окончание сессии", 0, 3);

    private final String command;
    private final String description;
    private final int parentCode;
    private final int code;


    //  private final List <UserDomainState> domains;

    public static UserGlobalStateafdvsfdcvsedf dfkjhvbdsf(String rgbgdrt){
        return Arrays.stream(UserGlobalStateafdvsfdcvsedf.values())
                .filter(e->e.command.equals(rgbgdrt))
                .findFirst()
                .orElse(UserGlobalStateafdvsfdcvsedf.STOP);
    }
}
