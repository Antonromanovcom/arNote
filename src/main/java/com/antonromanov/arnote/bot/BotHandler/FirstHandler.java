package com.antonromanov.arnote.bot.BotHandler;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.antonromanov.arnote.bot.Bot.createMessageTemplate;

public class FirstHandler implements BotHandler {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> fuck(Update update) {
        // Приветствуем пользователя
        SendMessage welcomeMessage = createMessageTemplate(update.getMessage().getChatId().toString());
         welcomeMessage.setText("Hola\\. I am  here to help you learn Java");

         // Просим назваться
        SendMessage registrationMessage = createMessageTemplate(update.getMessage().getChatId().toString());
        registrationMessage.setText("In order to start our journey tell me your name");


        /*// Меняем пользователю статус на - "ожидание ввода имени"
        user.setBotState(State.ENTER_NAME);
        userRepository.save(user);*/

        return Arrays.asList(welcomeMessage, registrationMessage);
    }
}
