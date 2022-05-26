package com.antonromanov.arnote.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum UserGlobalStateafdvsfdcvsedf {

    START("/start", "Начало сессии и выбор режима", 0, 1,
            Arrays.asList(BotReply.builder().text("Мобильный").command("/domain").build(),
                    BotReply.builder().text("Десктоп").command("/domain").build()), "Выберите тип устройства"),
    DOMAIN_SELECT("/domain", "Выбор домена", 1, 2,
            Arrays.asList(BotReply.builder().text("Желания").command("/wishes_domain_select").build(),
            BotReply.builder().text("Фин.Планы").command("/fin_planning_domain_select").build()),
            "С какой сущностью будем работать?"),
    WISH_DOMAIN("/wishes_domain_select", "Работа с желаниями", 2, 3,
            Arrays.asList(BotReply.builder().text("Список желаний").command("/wishlist").build(),
                    BotReply.builder().text("Добавить желание").command("/addwish").build(),
                    BotReply.builder().text("Редактировать желание").command("/editwish").build()),
            "Работа с желаниями"),
    WISH_LIST("/wishlist", "Список желаний", 3, 4, null, "Список желаний"),
    STOP("/stop", "Окончание сессии", 0, 3, null, null);

    private final String command;
    private final String description;
    private final int parentCode;
    private final int code;
    private final List<BotReply> reply;
    private final String replyMessageText;

    public static UserGlobalStateafdvsfdcvsedf dfkjhvbdsf(String rgbgdrt){
        return Arrays.stream(UserGlobalStateafdvsfdcvsedf.values())
                .filter(e->e.command.equals(rgbgdrt))
                .findFirst()
                .orElse(UserGlobalStateafdvsfdcvsedf.STOP);
    }
}
