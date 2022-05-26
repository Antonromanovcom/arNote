package com.antonromanov.arnote.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor

public final class UserData {
    private static UserData INSTANCE;
    private UserGlobalStateafdvsfdcvsedf state;

    private UserData() {
    }

    public static UserData getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserData();
        }
        return INSTANCE;
    }
}
