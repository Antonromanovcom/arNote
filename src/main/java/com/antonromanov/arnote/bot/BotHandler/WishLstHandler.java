package com.antonromanov.arnote.bot.BotHandler;

import com.antonromanov.arnote.bot.prettytable.PrettyTablePrinter;
import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.wish.Wish;
import com.antonromanov.arnote.services.MainService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import static com.antonromanov.arnote.bot.Bot.createMessageTemplate;


public class WishLstHandler implements BotHandler {

    private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.WISH_LIST;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update, MainService dataService, ArNoteUser user) {
        return Collections.emptyList();
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update, MainService dataService, ArNoteUser user) {
        UserData userData = UserData.getInstance();
        PrettyTablePrinter printerService = new PrettyTablePrinter();
        userData.setState(UserGlobalStateafdvsfdcvsedf.STOP);
        SendMessage messageToSend = createMessageTemplate(update.getCallbackQuery().getMessage().getChatId().toString());
        List<Wish> wishes = dataService.getAllWishesWithPriority1(user);
        messageToSend.setText("```" + printerService.prepareWishTable(wishes) + "```");
        return Collections.singletonList(messageToSend);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedBotState() {
        return Collections.emptyList();
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery() {
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }
}
