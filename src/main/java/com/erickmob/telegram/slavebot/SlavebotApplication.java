package com.erickmob.telegram.slavebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class SlavebotApplication {
	
    public static void main(String[] args) {
    	initialize();
        SpringApplication.run(SlavebotApplication.class, args);
    }

    private static void initialize(){
        ApiContextInitializer.init();
    }
}
