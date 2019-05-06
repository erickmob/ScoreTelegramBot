package com.erickmob.telegram.slavebot;

import javax.annotation.PostConstruct;

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

import com.erickmob.telegram.slavebot.domain.Action;
import com.erickmob.telegram.slavebot.service.UserActionsService;

//@ComponentScan({"com.erickmob.telegram.slavebot.service"
//	,"com.erickmob.telegram.slavebot.repository"
//})
@Component
public class SlaveBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(SlaveBot.class);

    private String token;

    private String username;	

    @Autowired
    private UserActionsService userActionsService;
    

	@Override
    public String getBotToken() {
        return "888247542:AAFmi7whgJ7jDHZroKxTUue6CqHp9LRNx1U";
    }

//    @Override
    public String getBotUsername() {
        return "Erickmobslavebot";
    }

//    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            handleMessage(message);
        }
    }
    
    private void handleMessage(Message message) {
		Action actionFoundInMessage = searchForActionByCommand(message.getText());
		if(actionFoundInMessage == null) {
			sendCommandNotFoundMessage(message);
		}else {
			handleAction(actionFoundInMessage, message);
		}
	
}

	private void handleAction(Action actionFoundInMessage, Message message) {
		switch(actionFoundInMessage) {
			case bebi:
				this.sendMessage(message.getChatId(), userActionsService.handleBebiAction(message));
				break;
			case caguei:
				this.sendMessage(message.getChatId(), userActionsService.handleCagueiAction(message));
				break;
			case help:
				sendHelpMessage(message);
				break;
				
		}
		
	}


	private void sendHelpMessage(Message message) {
		this.sendMessage(message.getChatId(), 
				"Opa, to aqui pra te ajudar!.  \n"
				+ "Por enquanto só entendo os seguintes comandos: \n"
				+ "/bebi : contabilizo que vc ja tomou seu goró e te respondo o total de vezes que vc ja fez isso.\n"
				+ "/caguei : contabilizo as vezes que vc fez merda e te digo quantas merdas ja foram descarga a baixo.");
		
	}

	private void sendCommandNotFoundMessage(Message message) {
		this.sendMessage(message.getChatId(), "Eta pega, não entendi. Digite  /help para saber o que posso fazer.");	
	}

	private Action searchForActionByCommand(String text) {
		return Action.fromString(text);
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

//    @PostConstruct
//    public void start() {
//        logger.info("username: {}, token: {}", username, token);
//    }
    
    @PostConstruct
    public void registerBot(){
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
            logger.info("==================================TelegramBotService.afterPropertiesSet:registerBot finish");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
