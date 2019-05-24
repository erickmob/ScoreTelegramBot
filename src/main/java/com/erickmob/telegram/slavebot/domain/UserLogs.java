package com.erickmob.telegram.slavebot.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "UserLogs")
@Data
public class UserLogs {


    public UserLogs(Date date, Integer userId, Long chatID, Command command) {
		this.date = date;
		this.userID = userId;
		this.command = command;
		this.chatID = chatID;
	}

	public UserLogs() {}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Date date;
	
	private Integer userID;

	private Long chatID;
    
	private Command command;

}
