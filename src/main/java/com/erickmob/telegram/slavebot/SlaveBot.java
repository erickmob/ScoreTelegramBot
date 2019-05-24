package com.erickmob.telegram.slavebot;

import javax.annotation.PostConstruct;

import com.erickmob.telegram.slavebot.domain.UserLogs;
import com.erickmob.telegram.slavebot.repository.TelegramUserRepository;
import com.erickmob.telegram.slavebot.repository.UserLogsRepository;
import com.erickmob.telegram.slavebot.service.MessageService;
import com.erickmob.telegram.slavebot.service.TelegramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.erickmob.telegram.slavebot.domain.Command;
import com.erickmob.telegram.slavebot.service.UserLogsService;

import java.util.Date;

//@ComponentScan({"com.erickmob.telegram.slavebot.service"
//	,"com.erickmob.telegram.slavebot.repository"
//})
@Component
public class SlaveBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(SlaveBot.class);

    private String token;

    private String username;

    @Autowired
    private UserLogsService userActionsService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private UserLogsRepository userLogsRepository;

    @Override
    public String getBotToken() {
        return "yourTelegramToken";
    }

    //    @Override
    public String getBotUsername() {
        return "yourBotName";
    }


//    @PostConstruct
//    public void start() {
//        logger.info("username: {}, token: {}", username, token);
//    }

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
            logger.info("==================================TelegramBotService.afterPropertiesSet:registerBot finish");
//            populate();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void populate() {
        userLogsRepository.save(new UserLogs(new Date(), 2, 1L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 3, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 3, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 1, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 1, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 1, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 1, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 4, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 4, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 4, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 4, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 4, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 4, 403267918L, Command.ludo));
        userLogsRepository.save(new UserLogs(new Date(), 1, 403267918L, Command.detetive));
        userLogsRepository.save(new UserLogs(new Date(), 1, 403267918L, Command.detetive));
        userLogsRepository.save(new UserLogs(new Date(), 2, 403267918L, Command.vida));
        userLogsRepository.save(new UserLogs(new Date(), 2, 403267918L, Command.vida));
    }

    //    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            try {
                telegramUserService.updateOrCreateUser(message.getFrom());
            } catch (Exception e) {
                logger.info("==================================Error on updateCreate User");
                e.printStackTrace();
            }
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        Command commandFoundInMessage = searchForActionByCommand(message.getText());
        if (commandFoundInMessage == null) {
            sendCommandNotFoundMessage(message);
        } else {
            handleAction(commandFoundInMessage, message);
        }

    }

    private void sendCommandNotFoundMessage (Message message){
        this.sendMessage(message.getChatId(), "Eta pega, não entendi. Digite  /help para saber o que posso fazer.");
    }

    private void handleAction(Command commandFoundInMessage, Message message) {
        if (isHelpCommand(commandFoundInMessage)) {
            sendHelpMessage(message);
        } else if (isPlacarCommand(commandFoundInMessage)) {
            sendPlacarMessage(message);
        } else if (isClearCommand(commandFoundInMessage)) {
            userActionsService.clearAllFromChat(message);
            this.sendMessage(message.getChatId(), "Placar zerado nesse chat.");
        } else {
            this.sendMessage(message.getChatId(), userActionsService.handleNewVictory(message, commandFoundInMessage));
        }
    }

    private boolean isHelpCommand(Command commandFoundInMessage) {
        return commandFoundInMessage.equals(Command.help);
    }

    private void sendHelpMessage(Message message) {
        this.sendMessage(message.getChatId(),messageService.getHelpMessage());
    }

    private boolean isPlacarCommand(Command commandFoundInMessage) {
        return commandFoundInMessage.equals(Command.placar);
    }

    private void sendPlacarMessage(Message message) {
        this.sendMessage(message.getChatId(),messageService.getPlacarMessage(message));
    }

    private boolean isClearCommand(Command commandFoundInMessage) {
        return commandFoundInMessage.equals(Command.clear);
    }


    private Command searchForActionByCommand(String text) {
        return Command.fromString(text.contains("@") ? text.split("@")[0] : text );
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(text);
        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text.concat("!"), chatId);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

}
