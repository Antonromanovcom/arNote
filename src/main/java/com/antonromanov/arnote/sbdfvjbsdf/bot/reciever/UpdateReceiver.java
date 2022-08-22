package com.antonromanov.arnote.bot.reciever;

import com.antonromanov.arnote.bot.BotHandler.*;
import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.services.MainService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.antonromanov.arnote.bot.Bot.fvdvdrgvd;


public class UpdateReceiver {

    private final List<BotHandler> handlers;
    UserData userData;

    public UpdateReceiver() {
        FirstHandler fh = new FirstHandler();
        WishHandler wh = new WishHandler();
        WishLstHandler wlh = new WishLstHandler();
        AddWishHandler awh = new AddWishHandler();
        EditWishHandler ewh = new EditWishHandler();
        userData = UserData.getInstance();
        this.handlers = Arrays.asList(fh, wh, wlh, awh, ewh);
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update, MainService dataService, ArNoteUser user)
            throws UnsupportedOperationException { //todo: это не дело что мы сквозь все сервисы прокидываем MainService и ArNoteUser. Подумать как это разрешить?

        if (isMessageWithText(update)) {
            final Message message = update.getMessage();
            BotHandler handler = getHandlerByState(message.getText());
            return handler.handleMessage(update, dataService, user);
        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            BotHandler bh = getHandlerByCallBackQuery(callbackQuery.getData());
            return bh.handleCallback(update, dataService, user);
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

        Optional<BotHandler> handler = handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().stream().anyMatch(r -> r.getCommand().stream().anyMatch(w -> w.startsWith(messageText))))
                .findAny();
        BotHandler temp = handler.orElseGet(this::processTextMessages);
        return temp; // todo: нужно вынести поиск по введенному тексту в зависимости от статуса в отдельный хендлер
    }

    private BotHandler processTextMessages() {
        if (userData.getState() != null) {

            return handlers.stream()
                    .filter(h -> h.operatedBotState().stream().anyMatch(r -> r == userData.getState()))
                    .findAny()
                    .orElseThrow(UnsupportedOperationException::new);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}

