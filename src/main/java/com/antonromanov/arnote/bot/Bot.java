package com.antonromanov.arnote.bot;

import com.antonromanov.arnote.bot.prettytable.PrettyTablePrinter;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.wish.Wish;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.services.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
//public class Bot extends TelegramLongPollingBot {
public class Bot  {

/*
    @Autowired
    private PrettyTablePrinter printerService; // todo: это все не в боте должно быть. Должен быть сервис отдающий такие данные, а контроллер или сервис бота просто их потребяляет. Он не должен быть завязан на эти сервисы и репы.

    @Autowired
    UsersRepo usersRepo; // todo: это все не в боте должно быть. Должен быть сервис отдающий такие данные, а контроллер или сервис бота просто их потребяляет. Он не должен быть завязан на эти сервисы и репы.

    @Autowired
    MainService mainService; // todo: это все не в боте должно быть. Должен быть сервис отдающий такие данные, а контроллер или сервис бота просто их потребяляет. Он не должен быть завязан на эти сервисы и репы.


    @Override
    public void onUpdateReceived(Update update) {
        Message inMessage = getMessage(update);
        // fireMessage(inMessage.getChatId(), "<pre>" + prepareTable() + "</pre>");
   //     fireMessage(inMessage.getChatId(), "```" + printerService.prepareWishTable(getWishes()) + "```");

    }


    public List<Wish> getWishes() {
        ArNoteUser localUser = usersRepo.findById(1L).get();
        if (localUser==null){
            log.error("Не удалось выцепить юзера!");
        } else {
            log.error("Юзера достали!");
        }
        return new ArrayList<>(mainService.getAllWishesByUserId(localUser));
    }


        private Message getMessage (Update update){
       *//* if(update.hasChannelPost() && update.getChannelPost().hasText())
            return update.getChannelPost();
        if(update.hasMessage() && update.getMessage().hasText())*//*
            return update.getMessage();
            //  return null;
        }


        public void fireMessage (Long chanelId, String msg){

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
        public String getBotUsername () {
            return "arBOT";
        }

        @Override
        public String getBotToken () {
            return "649537355:AAHlbvfkZbqPHuNRUlRYCFsfIRPXuKXr0co";
        }*/
    }
