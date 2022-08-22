package com.antonromanov.arnote.bot.userdata;

import com.antonromanov.arnote.model.wish.Wish;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


public final class UserData {
    private static UserData INSTANCE;

    @Getter
    @Setter
    private UserGlobalStateafdvsfdcvsedf state;

    @Getter
    @Setter
    private DisplayType displayType;

    private Wish newWish;

    @Setter
    @Getter
    private Wish wishToEdit;

    private UserData() {}

    public static UserData getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserData();
        }
        return INSTANCE;
    }

    public  Wish getWish() {
        if(newWish == null) {
            newWish = new Wish();
            newWish.setAc(false);
            newWish.setRealized(false);
            newWish.setCreationDate(new Date());
            newWish.setDescription("Создано с помощью Телеграмм-Бота");
        }
        return newWish;
    }

    public void clear() {
        newWish = null;
    }
}
