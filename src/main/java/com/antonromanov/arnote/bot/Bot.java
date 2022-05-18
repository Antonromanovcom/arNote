package com.antonromanov.arnote.bot;

import com.antonromanov.arnote.services.MainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



@Slf4j
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final MainService repo;

    @Override
    public void onUpdateReceived(Update update) {
        int s = repo.getWishById(1).get().getPrice();
        log.warn("Size = {}", s);
        Message inMessage = getMessage(update);
        fireMessage(inMessage.getChatId(), "PIZDEC");
    }


        private Message getMessage (Update update){
        return update.getMessage();
    }


        public void fireMessage (Long chanelId, String msg){

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
        return "arNote_bot";
    }

    @Override
    public String getBotToken() {
      //  return "649537355:AAHlbvfkZbqPHuNRUlRYCFsfIRPXuKXr0co";
        return "5363458470:AAF_Bfytk7p9VMUHEuORXOG6UiP0XLL7GGE";
    }
}
