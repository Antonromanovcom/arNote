package com.antonromanov.arnote.sex.bot.BotHandler;

public class FirstHandler /*implements BotHandler*/ {


    /*private final UserGlobalStateafdvsfdcvsedf MESSAGE_HANDLER_STATE = UserGlobalStateafdvsfdcvsedf.START;
    private final UserGlobalStateafdvsfdcvsedf CALLBACK_STATE = UserGlobalStateafdvsfdcvsedf.DOMAIN_SELECT;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleMessage(Update update, MainService dataService, ArNoteUser user) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(Arrays.asList(createInlineKeyboardButtonFromEnum(MESSAGE_HANDLER_STATE)));
        SendMessage messageToSend = createMessageTemplate(update.getMessage().getChatId().toString());
        messageToSend.setText(MESSAGE_HANDLER_STATE.getReplyMessageText());
        messageToSend.setReplyMarkup(inlineKeyboardMarkup);

        return Arrays.asList(messageToSend);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCallback(Update update, MainService dataService, ArNoteUser user) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        UserData userData = UserData.getInstance();
        userData.setState(CALLBACK_STATE);

        if (UserGlobalStateafdvsfdcvsedf.START.getReply().get(0).getCommand().equals(update.getCallbackQuery().getData())){
            userData.setDisplayType(DisplayType.MOBILE);
        } else{
            userData.setDisplayType(DisplayType.DESKTOP);
        }

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(createInlineKeyboardButtonFromEnum(CALLBACK_STATE)));
        SendMessage messageToSend = createMessageTemplate(update.getCallbackQuery().getMessage().getChatId().toString());
        messageToSend.setText(CALLBACK_STATE.getReplyMessageText());
        messageToSend.setReplyMarkup(inlineKeyboardMarkup);

        return Collections.singletonList(messageToSend);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedBotState() {
        return Collections.singletonList(MESSAGE_HANDLER_STATE);
    }

    @Override
    public List<UserGlobalStateafdvsfdcvsedf> operatedCallBackQuery() {
        return Collections.singletonList(CALLBACK_STATE);
    }*/
}
