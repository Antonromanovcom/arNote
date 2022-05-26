package com.antonromanov.arnote.bot;

import com.antonromanov.arnote.bot.prettytable.PrettyTablePrinter;
import com.antonromanov.arnote.bot.reciever.UpdateReceiver;
import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.bot.userdata.UserGlobalStateafdvsfdcvsedf;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.wish.Wish;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.services.MainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final MainService dataService;
    private final UsersRepo usersRepo;
    private final Environment env;
    //private final UpdateReceiver updateReciever;

    @Override
    public void onUpdateReceived(Update update) {
        PrettyTablePrinter printerService = new PrettyTablePrinter();
        Message inMessage = getMessage(update);
        try {
            String arUser = env.getProperty("ar.user");
            log.info("Логин пользователя из environment: {}", arUser);
            ArNoteUser user = usersRepo.findByLogin(arUser).orElseThrow(UserNotFoundException::new);
            log.info("Удалось достать пользака. Id =  {}, Name =  {}", user.getId(), user.getFullname());
            List<Wish> wishes = dataService.getAllWishesWithPriority1(user);
            log.info("Кол-во приоритетных желаний:   {}", wishes.size());
            log.info("hasCallbackQuery ?:   {}", update.hasCallbackQuery());

            if (update.hasCallbackQuery()) {
             //   log.info("update.getCallbackQuery().getMessage() ?:   {}", update.getCallbackQuery().getMessage());
                log.info("update.getCallbackQuery().getData() ?:   {}", update.getCallbackQuery().getData());
            }

            log.info("hasMessage ?:   {}", update.hasMessage());

            if (update.hasMessage()) {
                log.info("getMessage().hasText() ?:   {}", update.getMessage().hasText());
                log.info(".getMessage().getFrom().getUserName() ?:   {}", update.getMessage().getFrom().getUserName());
            }

            UserData userData = UserData.getInstance();
            log.info("Текущий статус пользователя ?:   {}", userData.getState());

            // fireMessage(inMessage.getChatId(), "```" + printerService.prepareWishTable(wishes) + "```");

            UpdateReceiver updateReceiver = new UpdateReceiver();
            List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);

        //    log.info("Messages size: {}", messagesToSend.size());

            if (messagesToSend != null && !messagesToSend.isEmpty()) {
                messagesToSend.forEach(response -> {
                    if (response instanceof SendMessage) {
                    //    log.info("Message: {}", ((SendMessage) response).getText());
                        vbdk((SendMessage) response, null);
                    }
                });
            }


            //      vbdk(createMessageTemplate(inMessage.getChatId().toString()), inlineKeyboardMarkup);


           /* InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = Collections.singletonList(createInlineKeyboardButton("Start quiz", "Add"));
            inlineKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtonsRowOne));
            vbdk(createMessageTemplate(inMessage.getChatId().toString()), inlineKeyboardMarkup);*/


        } catch (Exception e) {
            log.error("Ошибка получения пользовательских данных: {}", e.getMessage());
            //   fireMessage(inMessage.getChatId(), "Не получилось получить данные пользователя!");
        }
    }

    private Message getMessage(Update update) {
        return update.getMessage();
    }

    // Создаем шаблон SendMessage с включенным Markdown
    public static SendMessage createMessageTemplate(String chatId) {

        SendMessage outMessage = new SendMessage();
        outMessage.enableMarkdownV2(true);
        outMessage.setChatId(chatId);
        return outMessage;
    }

    public static Boolean fvdvdrgvd(List<UserGlobalStateafdvsfdcvsedf> statesList, String query) {
        return statesList.stream().anyMatch(r-> r.getReply().stream().anyMatch(e->e.getCommand().startsWith(query)));
    }

    public static List<InlineKeyboardButton> createInlineKeyboardButtonFromEnum(UserGlobalStateafdvsfdcvsedf state) {
        return state.getReply().stream()
                .map(v->createInlineKeyboardButton(v.getText(), v.getCommand()))
                .collect(Collectors.toList());
    }

    // Создаем кнопку
    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        InlineKeyboardButton ib = new InlineKeyboardButton();
        ib.setText(text);
        ib.setCallbackData(command);
        return ib;
    }

    private void vbdk(SendMessage msg, InlineKeyboardMarkup inlineKeyboardMarkup) {

        try {
            //  msg.setReplyMarkup(inlineKeyboardMarkup);
            //  msg.setText("11");
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения пользователю: {}", e.getMessage());
        }

    }


    private void fireMessage(Long chanelId, String msg) {

        try {
            SendMessage outMessage = new SendMessage();
            // outMessage.enableHtml(true);
            // outMessage.setParseMode("html");
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
    }
}
