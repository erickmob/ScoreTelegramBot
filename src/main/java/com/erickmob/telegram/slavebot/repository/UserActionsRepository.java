package com.erickmob.telegram.slavebot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.erickmob.telegram.slavebot.domain.Action;
import com.erickmob.telegram.slavebot.domain.UserActions;

@Repository
public interface UserActionsRepository extends CrudRepository<UserActions, Long>{

	long countByUserIDAndAction(Integer userID, Action action);
	
}