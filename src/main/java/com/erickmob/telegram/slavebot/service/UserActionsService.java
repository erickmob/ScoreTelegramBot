package com.erickmob.telegram.slavebot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.erickmob.telegram.slavebot.domain.Action;
import com.erickmob.telegram.slavebot.domain.UserActions;
import com.erickmob.telegram.slavebot.repository.UserActionsRepository;

@Component
public class UserActionsService {

	@Autowired
	private UserActionsRepository userActionRepository;

	public String handleCagueiAction(Message message) {
		String msg;
		userActionRepository.save(new UserActions(new Date(), message.getFrom().getId(), Action.caguei));
		long qtd = userActionRepository.countByUserIDAndAction(message.getFrom().getId(), Action.caguei);
		msg = "Show, voce ja cagou ".concat(Long.toString(qtd)).concat(" vezes!");
		return msg;
		
	}
	

	public String handleBebiAction(Message message) {
		String msg;
		userActionRepository.save(new UserActions(new Date(), message.getFrom().getId(), Action.bebi));
		long qtd = userActionRepository.countByUserIDAndAction(message.getFrom().getId(), Action.bebi);
		msg = "Show, voce ja bebeu ".concat(Long.toString(qtd)).concat(" gorós!");
		return msg;
		
	}
}
