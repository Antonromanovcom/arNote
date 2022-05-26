package com.antonromanov.arnote.bot.reciever;

import com.antonromanov.arnote.bot.BotHandler.BotHandler;
import com.antonromanov.arnote.bot.BotHandler.FirstHandler;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.antonromanov.arnote.bot.Bot.fvdvdrgvd;

public class UpdateReceiver {

    private final List<BotHandler> handlers;

    public UpdateReceiver() {
        FirstHandler fh = new FirstHandler();
        this.handlers = Arrays.asList(fh);
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        //   UserGlobalStateafdvsfdcvsedf hfvcbsk =  UserGlobalStateafdvsfdcvsedf.dfkjhvbdsf(update.getMessage().getText());


        if (isMessageWithText(update)) {
            final Message message = update.getMessage();
            //final int chatId = message.getFrom().getId();
            /*final User user = userRepository.getByChatId(chatId)
                    .orElseGet(() -> userRepository.save(new User(chatId)));*/
            // return getHandlerByState(user.getBotState()).handle(user, message.getText());
            return getHandlerByState(message.getText()).handleMessage(update);

        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            // final int chatId = callbackQuery.getFrom().getId();
            /*final User user = userRepository.getByChatId(chatId)
                    .orElseGet(() -> userRepository.save(new User(chatId)));*/

            //return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            BotHandler bh = getHandlerByCallBackQuery(callbackQuery.getData());
            return bh.handleCallback(update);
        }
        return Collections.emptyList();
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private BotHandler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
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

