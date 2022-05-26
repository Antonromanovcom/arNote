package com.antonromanov.arnote.bot.BotHandler;

import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.antonromanov.arnote.bot.Bot.*;

public class FirstHandler implements BotHandler {


    private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.START;
    private final UserGlobalStateafdvsfdcvsedf CALLBACK_STATE = UserGlobalStateafdvsfdcvsedf.DOMAIN_SELECT;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update) {

       /* SendMessage welcomeMessage = createMessageTemplate(update.getMessage().getChatId().toString());
        welcomeMessage.setText("Hola\\. I am  here to help you learn Java");


        SendMessage registrationMessage = createMessageTemplate(update.getMessage().getChatId().toString());
        registrationMessage.setText("In order to start our journey tell me your name");*/

      //  createInlineKeyboardButton()

        // Создаем кнопку для начала игры
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(Arrays.asList(createInlineKeyboardButtonFromEnum(MESSAGE_HANDLER_STATE)));
        SendMessage messageToSend = createMessageTemplate(update.getMessage().getChatId().toString());
        messageToSend.setText(MESSAGE_HANDLER_STATE.getReplyMessageText());
        messageToSend.setReplyMarkup(inlineKeyboardMarkup);

        return Arrays.asList(messageToSend);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update) {
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
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery() {
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }
}
