package com.erickmob.telegram.slavebot.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "UserActions")
@Data
public class UserActions {


    public UserActions(Date date, Integer userId, Action action) {
		this.date = date;
		this.userID = userId;
		this.action = action;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Date date;
	
	private Integer userID;
    
	private Action action;

}
