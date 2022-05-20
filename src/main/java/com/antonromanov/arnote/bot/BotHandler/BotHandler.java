package com.antonromanov.arnote.bot.BotHandler;


import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.List;

public interface BotHandler {
    List<PartialBotApiMethod<? extends Serializable>> fuck(Update update);
}
