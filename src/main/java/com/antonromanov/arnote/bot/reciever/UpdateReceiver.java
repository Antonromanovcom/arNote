package com.antonromanov.arnote.bot.reciever;

import com.antonromanov.arnote.bot.BotHandler.BotHandler;
import com.antonromanov.arnote.bot.BotHandler.FirstHandler;
import com.antonromanov.arnote.bot.BotHandler.WishHandler;
import com.antonromanov.arnote.bot.BotHandler.WishLstHandler;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import static com.antonromanov.arnote.bot.Bot.fvdvdrgvd;


public class UpdateReceiver {

    private final List<BotHandler> handlers;

    public UpdateReceiver() {
        FirstHandler fh = new FirstHandler();
        WishHandler wh = new WishHandler();
        WishLstHandler wlh = new WishLstHandler();
        this.handlers = Arrays.asList(fh, wh, wlh);
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) throws UnsupportedOperationException {

        if (isMessageWithText(update)) {
            final Message message = update.getMessage();
            return getHandlerByState(message.getText()).handleMessage(update);

        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            BotHandler bh = getHandlerByCallBackQuery(callbackQuery.getData());
            return bh.handleCallback(update);
        }
        throw new UnsupportedOperationException();
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private BotHandler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery() != null)
                .filter(h -> fvdvdrgvd(h.operatedCallBackQuery(), query))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }


    private BotHandler getHandlerByState(String messageText) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().stream().anyMatch(f -> f.getCommand().startsWith(messageText)))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }
}

