package com.antonromanov.arnote.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@Component
//@Slf4j
public class Bot { // extends TelegramLongPollingBot {

    // Аннотация @Value позволяет задавать значение полю путем считывания из application.yaml
   /* @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;*/

    /* Перегружаем метод интерфейса LongPollingBot
    Теперь при получении сообщения наш бот будет отвечать сообщением Hi!
     */
   /* @Override
    public void onUpdateReceived(Update update) {
        try {
            log.info("Rec...d!");

            execute(new SendMessage().setChatId(update.getMessage().getChatId())
                    .setText("Hi!"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }*/

    // Геттеры, которые необходимы для наследования от TelegramLongPollingBot
   /* public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }*/
}
