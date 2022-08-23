package com.antonromanov.arnote.sex.bot;

/*@Slf4j
@AllArgsConstructor*/
public class Bot /*extends TelegramLongPollingBot*/ {

   /* private final MainService dataService;
    private final UsersRepo usersRepo;
    private final Environment env;

    @Override
    public void onUpdateReceived(Update update) {

        Message inMessage = getMessage(update);
        try {
            String arUser = env.getProperty("ar.user");
            ArNoteUser user = usersRepo.findByLogin(arUser).orElseThrow(UserNotFoundException::new);
            log.info("=====================================================");

            UserData userData = UserData.getInstance();
            UpdateReceiver updateReceiver = new UpdateReceiver();
            List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update, dataService, user);

            if (messagesToSend != null && !messagesToSend.isEmpty()) {
                messagesToSend.forEach(response -> {
                    if (response instanceof SendMessage) {
                        vbdk((SendMessage) response, null);
                        log.info("Текущий статус пользователя ?:   {}", userData.getState());
                    }
                });
            }


        } catch (UnsupportedOperationException uoe) {
            log.error("Вызвана операция, которая не предусмотрена для бота: {}", uoe.getMessage());
            Long chatId;
            if (inMessage == null || inMessage.getChat().getId() == null) {
                chatId = update.getCallbackQuery().getMessage().getChatId();
            } else {
                chatId = inMessage.getChat().getId();
            }
            fireMessage(chatId, "Я не знаю такой команды");
        } catch (Exception e) {
            log.error("Ошибка получения пользовательских данных: {}", e.getMessage());
        }
    }

    private Message getMessage(Update update) {
        return update.getMessage();
    }

    public static SendMessage createMessageTemplate(String chatId) {

        SendMessage outMessage = new SendMessage();
        outMessage.enableMarkdownV2(true);
        outMessage.setChatId(chatId);
        return outMessage;
    }

    public static Boolean fvdvdrgvd(List<UserGlobalStateafdvsfdcvsedf> statesList, String query) {
        return statesList.stream().anyMatch(r -> r.getCommand().stream().anyMatch(w->w.startsWith(query)));
    }


    public static List<InlineKeyboardButton> createInlineKeyboardButtonFromEnum(UserGlobalStateafdvsfdcvsedf state) {
        return state.getReply().stream()
                .map(v -> createInlineKeyboardButton(v.getText(), v.getCommand()))
                .collect(Collectors.toList());
    }


    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        InlineKeyboardButton ib = new InlineKeyboardButton();
        ib.setText(text);
        ib.setCallbackData(command);
        return ib;
    }

    private void vbdk(SendMessage msg, InlineKeyboardMarkup inlineKeyboardMarkup) {

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения пользователю: {}", e.getMessage());
        }

    }


    private void fireMessage(Long chanelId, String msg) {
        try {
            SendMessage outMessage = new SendMessage();
            outMessage.enableMarkdownV2(true);
            outMessage.setChatId(chanelId.toString());
            outMessage.setText(msg);
            execute(outMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения пользователю: {}", e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return "arNote_bot";
    }

    @Override
    public String getBotToken() {
        //  return "649537355:AAHlbvfkZbqPHuNRUlRYCFsfIRPXuKXr0co";
        return "5363458470:AAF_Bfytk7p9VMUHEuORXOG6UiP0XLL7GGE";
    }*/
}
