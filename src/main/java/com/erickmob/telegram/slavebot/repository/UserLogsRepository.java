package com.erickmob.telegram.slavebot.repository;

import com.erickmob.telegram.slavebot.domain.UserLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.erickmob.telegram.slavebot.domain.Command;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserLogsRepository extends CrudRepository<UserLogs, Long>{

	long countByUserIDAndCommand(Integer userID, Command command);

	@Transactional
	void deleteUserLogsByChatID(Long chatID);

	List<UserLogs> findAllByChatID(Long chatID);
}