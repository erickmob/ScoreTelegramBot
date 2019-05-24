package com.erickmob.telegram.slavebot.service;


import com.erickmob.telegram.slavebot.domain.TelegramUser;
import com.erickmob.telegram.slavebot.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class TelegramUserService {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    public void updateOrCreateUser(User user){
        TelegramUser telegramUser = telegramUserRepository.findByUserID(user.getId());
        if(telegramUser == null){
            telegramUserRepository.save(new TelegramUser(user.getId(),user.getUserName(), user.getFirstName(),user.getLastName()));
        }else{
            telegramUser.setUsername(user.getUserName());
            telegramUser.setName(user.getFirstName());
            telegramUser.setLastName(user.getLastName());

        }
    };
}
