package com.antonromanov.arnote.bot;


import com.antonromanov.arnote.services.MainService;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

   // private ExecutorService pool = Executors.newCachedThreadPool();
   // private static Logger logger = LoggerFactory.getLogger("console_logger");

   // @Value("${bot.name}")
   private final String botUsername = "arBOT";

   //  @Value("${bot.token}")
    private final String botToken = "649537355:AAHlbvfkZbqPHuNRUlRYCFsfIRPXuKXr0co";




    @Override
    public void onUpdateReceived(Update update) {
        log.warn("PIZDEC!!!!");
        Message inMessage = getMessage(update);

            fireMessage(inMessage.getChatId(), "PIZDEC");

    }


    private Message getMessage(Update update) {
       /* if(update.hasChannelPost() && update.getChannelPost().hasText())
            return update.getChannelPost();
        if(update.hasMessage() && update.getMessage().hasText())*/
            return update.getMessage();
      //  return null;
    }


    public void fireMessage(Long chanelId, String msg) {

            try {
                SendMessage outMessage = new SendMessage();
                outMessage.enableHtml(true);
                outMessage.setChatId(chanelId.toString());
                outMessage.setText(msg);
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

    }


    @Override
    public String getBotUsername() {
        return "arBOT";
    }

    @Override
    public String getBotToken() {
        return "649537355:AAHlbvfkZbqPHuNRUlRYCFsfIRPXuKXr0co";
    }
}
