package com.erickmob.telegram.slavebot;

import com.erick.telegram.slavebot.domain.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class SlavebotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlavebotApplication.class, args);

        try{
            doIt();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiException te){
            te.printStackTrace();
        }
    }

    private static void doIt(){
        ApiContextInitializer.init();
    }
}
