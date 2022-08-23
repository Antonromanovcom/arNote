package com.antonromanov.arnote.sex.bot.BotHandler;


public class WishLstHandler/* implements BotHandler*/ {

   /* private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.WISH_LIST;

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
    }*/
}
