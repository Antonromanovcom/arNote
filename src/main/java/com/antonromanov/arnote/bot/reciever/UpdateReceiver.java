package com.antonromanov.arnote.bot.reciever;

import com.antonromanov.arnote.bot.BotHandler.BotHandler;
import com.antonromanov.arnote.bot.BotHandler.FirstHandler;
import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class UpdateReceiver {

    private final List<BotHandler> handlers;

    public UpdateReceiver() {
        FirstHandler fh = new FirstHandler();
        this.handlers = Arrays.asList(fh);
    }

    public  List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        UserGlobalStateafdvsfdcvsedf hfvcbsk =  UserGlobalStateafdvsfdcvsedf.dfkjhvbdsf(update.getMessage().getText());
        return getHandlerByState().fuck(update);
    }


    private BotHandler getHandlerByState() {
        return handlers.stream()
              //  .filter(h -> h.operatedBotState() != null)
             //   .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }


}

