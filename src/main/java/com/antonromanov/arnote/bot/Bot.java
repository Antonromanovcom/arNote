package com.antonromanov.arnote.bot;

import lombok.extern.slf4j.Slf4j;
import org.sk.PrettyTable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message inMessage = getMessage(update);
        // fireMessage(inMessage.getChatId(), "<pre>" + prepareTable() + "</pre>");
        fireMessage(inMessage.getChatId(), "```" + prepareTable() + "```");

    }


    public String prepareTable() {
        PrettyTable table = new PrettyTable("Firstname", "Lastname", "Phone");
        table.addRow("John", "Doe",  "+2137999999");
        table.addRow("Jane", "Doe", "+2137999999");
    //     System.out.println(table);
        return table.toString();
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
           // outMessage.enableHtml(true);
            // outMessage.setParseMode("html");
            outMessage.enableMarkdownV2(true);
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
