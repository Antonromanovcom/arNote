package com.antonromanov.arnote.bot.BotHandler;

import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.services.MainService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import static com.antonromanov.arnote.bot.Bot.createInlineKeyboardButtonFromEnum;
import static com.antonromanov.arnote.bot.Bot.createMessageTemplate;


public class WishHandler implements BotHandler {

    private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.WISH_DOMAIN;
    private final UserGlobalStateafdvsfdcvsedf CALLBACK_STATE = UserGlobalStateafdvsfdcvsedf.WISH_DOMAIN;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update, MainService dataService, ArNoteUser user) {
        return Collections.emptyList();
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update, MainService dataService, ArNoteUser user) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        UserData userData = UserData.getInstance();
        userData.setState(CALLBACK_STATE);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(createInlineKeyboardButtonFromEnum(CALLBACK_STATE)));
        SendMessage messageToSend = createMessageTemplate(update.getCallbackQuery().getMessage().getChatId().toString());
        messageToSend.setText(CALLBACK_STATE.getReplyMessageText());
        messageToSend.setReplyMarkup(inlineKeyboardMarkup);
        return Collections.singletonList(messageToSend);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedBotState() {
        return Collections.emptyList();
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery() {
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }
}