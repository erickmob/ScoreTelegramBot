package com.erickmob.telegram.slavebot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.erickmob.telegram.slavebot.domain.Command;
import com.erickmob.telegram.slavebot.domain.UserLogs;
import com.erickmob.telegram.slavebot.repository.UserLogsRepository;

@Component
public class UserLogsService {

	@Autowired
	private UserLogsRepository userLogsRepository;

	public String handleNewVictory(Message message, Command command) {
		String msg;
		userLogsRepository.save(new UserLogs(new Date(), message.getFrom().getId(), message.getChatId(), command));
		long qtd = userLogsRepository.countByUserIDAndCommand(message.getFrom().getId(), command);
		msg = "Show "+message.getFrom().getFirstName()+("!\n" +
				"Voce ja ganhou "+Long.toString(qtd))+" vezes " +
				"no "+command.getActionDone()+" nesse grupo!";
		return msg;
		
	}

	public void clearAllFromChat(Message message){
		userLogsRepository.deleteUserLogsByChatID(message.getChatId());
	}

}
