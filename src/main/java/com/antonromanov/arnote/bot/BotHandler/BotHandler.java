package com.antonromanov.arnote.bot.BotHandler;

import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.List;

public interface BotHandler {
    List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update);
    List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update);
    List<UserGlobalStateafdvsfdcvsedf> operatedBotState();
    List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery();
}
