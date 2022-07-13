package com.antonromanov.arnote.bot.BotHandler;

import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.services.MainService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.antonromanov.arnote.bot.Bot.createMessageTemplate;


public class EditWishHandler implements BotHandler {

    private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.WISH_EDIT;


    @Override //todo: когда пользак вводит id желания для редактирования нужно проверять что такое желание вообще есть и в случае если нет - выкидывать на начало с соответствующим сообщением
    public List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update, MainService dataService, ArNoteUser user) {
        UserData userData = UserData.getInstance();
        Message msg = update.getMessage();
        userData.setWishToEdit(dataService.getWishById(Integer.parseInt(msg.getText())).get()); // todo: обработать этот кейс. Если не найдено - то стоп и выходим на /start

        // todo: нужно вынести поиск по введенному тексту в зависимости от статуса в отдельный хендлер
        if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_EDIT) { //todo: весь этот блок подумать как отрефакторить. Может паттерн какой применить
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRICE);
            //  userData.getWish().setWish(msg.getText() + "["+userData.getWishToEdit().getPrice() + "]"); // заменить на formatString %s
            return printMessage(msg.getChatId().toString(),
                    UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRICE.getReplyMessageText() +
                            " [" + userData.getWishToEdit().getPrice() + "]"); // заменить на formatString %s
        } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRICE) {
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRIOR);
            userData.getWish().setPrice(Integer.parseInt(msg.getText())); //todo: проверка что не пустое??? А проверка на число?
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRIOR.getReplyMessageText());
        } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRIOR) {
            userData.getWish().setUrl(msg.getText());
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DELETE);
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DELETE.getReplyMessageText());
        } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DONE) {
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DONE);
            userData.getWish().setDescription(msg.getText());
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DONE.getReplyMessageText());
        } else { //todo: а проверка статуса ?????
            userData.setState(UserGlobalStateafdvsfdcvsedf.STOP);
            userData.getWish().setPriority(Integer.parseInt(msg.getText())); //todo: проверка что не пустое??? А проверка на число?
            userData.getWish().setUser(user);
            long wishId = dataService.saveWish(userData.getWish()).getId();
            userData.clear();
            return printMessage(msg.getChatId().toString(), "Сохранили желание с ID " + wishId);
        }
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update, MainService dataService,
                                                                            ArNoteUser user) {
        UserData userData = UserData.getInstance();
        userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_EDIT);
        return printMessage(update.getCallbackQuery().getMessage().getChatId().toString(),
                MESSAGE_HANDLER_STATE.getReplyMessageText());
    }

    private List<PartialBotApiMethod<? extends Serializable>> printMessage(String chatId, String text) {
        SendMessage messageToSend = createMessageTemplate(chatId);
        messageToSend.setText(text);
        return Collections.singletonList(messageToSend);
    }


    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedBotState() {
        return Arrays.asList(UserGlobalStateafdvsfdcvsedf.WISH_EDIT,
                UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DONE,
                UserGlobalStateafdvsfdcvsedf.WISH_EDIT_DELETE,
                UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRICE,
                UserGlobalStateafdvsfdcvsedf.WISH_EDIT_PRIOR);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery() {
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }
}
