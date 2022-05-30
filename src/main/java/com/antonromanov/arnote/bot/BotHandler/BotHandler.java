package com.antonromanov.arnote.bot.BotHandler;

import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.services.MainService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.List;

public interface BotHandler {
    List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update, MainService dataService, ArNoteUser user);
    List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update, MainService dataService, ArNoteUser user);
    List<UserGlobalStateafdvsfdcvsedf> operatedBotState();
    List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery();
}
