package com.antonromanov.arnote.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public enum UserGlobalStateafdvsfdcvsedf {

    START(Collections.singletonList("/start"), "Начало сессии и выбор режима", 0, 1,
            Arrays.asList(BotReply.builder().text("Мобильный").command("/domain_with_mobile").build(),
                    BotReply.builder().text("Десктоп").command("/domain_with_desktop").build()), "Выберите тип устройства"),
    DOMAIN_SELECT(Arrays.asList("/domain_with_mobile", "/domain_with_desktop"), "Выбор домена", 1, 2,
            Arrays.asList(BotReply.builder().text("Желания").command("/wishes_domain_select").build(),
            BotReply.builder().text("Фин.Планы").command("/fin_planning_domain_select").build()),
            "С какой сущностью будем работать?"),
    WISH_DOMAIN(Collections.singletonList("/wishes_domain_select"), "Работа с желаниями", 2, 3,
            Arrays.asList(BotReply.builder().text("Список желаний").command("/wishlist").build(),
                    BotReply.builder().text("Добавить желание").command("/addwish").build(),
                    BotReply.builder().text("Редактировать желание").command("/editwish").build()),
            "Работа с желаниями"),
    WISH_LIST(Collections.singletonList("/wishlist"), "Список желаний", 3, 4, null,
            "Список желаний"),
    WISH_ADD(Collections.singletonList("/addwish"), "Добавить желание", 3, 5,
            null,
            "Введите желание"),
    WISH_EDIT(Collections.singletonList("/editwish"), "Редактировать желание", 3, 5,
            null,
            "Введите id желания"),
    WISH_ADD_PRICE(Collections.singletonList("/addwishprice"), "Добавить желание", 3, 6, null,
            "Цена желания"),
    WISH_EDIT_PRICE(Collections.singletonList("/editwishprice"), "Редактировать желание", 3, 6, null,
            "Цена желания"),
    WISH_EDIT_PRIOR(Collections.singletonList("/editwishprior"), "Редактировать приоритет желания", 3, 6, null,
            "Редактировать приоритет желания"),
    WISH_EDIT_DELETE(Collections.singletonList("/editwishdelete"), "Редактировать желание", 3, 6, null,
            "Удалить? [Y/N]"),
    WISH_EDIT_DONE(Collections.singletonList("/editwishsetdone"), "Редактировать желание", 3, 6, null,
            "Реализовано? [Y/N]"),
    WISH_ADD_URL(Collections.singletonList("/addwishurl"), "Добавить желание", 3, 7, null,
            "URL"),
    WISH_ADD_DESC(Collections.singletonList("/addwishdesc"), "Добавить желание", 3, 8, null,
            "Описание"),
    WISH_ADD_PRIOR(Collections.singletonList("/addwishprior"), "Добавить желание", 3, 9, null,
            "Приоритет"),
    STOP(Collections.singletonList("/stop"), "Окончание сессии", 0, 3, null,
            null);

    private final List<String> command;
    private final String description;
    private final int parentCode; // todo: удалить эти коды надо, только путают
    private final int code;
    private final List<BotReply> reply;
    private final String replyMessageText;
}
