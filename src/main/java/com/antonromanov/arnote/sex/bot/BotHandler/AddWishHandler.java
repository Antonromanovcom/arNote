package com.antonromanov.arnote.sex.bot.BotHandler;

public class AddWishHandler /*implements BotHandler*/ {

   /* private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.WISH_ADD;


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update, MainService dataService, ArNoteUser user) {
        UserData userData = UserData.getInstance();
        Message msg = update.getMessage();


        // todo: нужно вынести поиск по введенному тексту в зависимости от статуса в отдельный хендлер
        if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_ADD) { //todo: весь этот блок подумать как отрефакторить. Может паттерн какой применить
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRICE);
            userData.getWish().setWish(msg.getText()); //todo: проверка что не пустое???
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRICE.getReplyMessageText());
        } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRICE) {
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_ADD_URL);
            userData.getWish().setPrice(Integer.parseInt(msg.getText())); //todo: проверка что не пустое??? А проверка на число?
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_ADD_URL.getReplyMessageText());
        } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_ADD_URL) {
            userData.getWish().setUrl(msg.getText());
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_ADD_DESC);
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_ADD_DESC.getReplyMessageText());
        } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_ADD_DESC) {
            userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRIOR);
            userData.getWish().setDescription(msg.getText());
            return printMessage(msg.getChatId().toString(), UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRIOR.getReplyMessageText());
       // } else if (userData.getState() == UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRIOR) {
        } else {
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
        userData.setState(UserGlobalStateafdvsfdcvsedf.WISH_ADD);
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
        return Arrays.asList(UserGlobalStateafdvsfdcvsedf.WISH_ADD,
                UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRICE,
                UserGlobalStateafdvsfdcvsedf.WISH_ADD_URL,
                UserGlobalStateafdvsfdcvsedf.WISH_ADD_DESC,
                UserGlobalStateafdvsfdcvsedf.WISH_ADD_PRIOR);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery() {
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }*/
}
