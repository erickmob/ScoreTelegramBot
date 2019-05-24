package com.erickmob.telegram.slavebot.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TelegramUser")
@Data
public class TelegramUser {


    public TelegramUser(Integer userID, String username, String name, String lastName) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
    }

    public TelegramUser() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer userID;
    private String username;
    private String name;
    private String lastName;
}
