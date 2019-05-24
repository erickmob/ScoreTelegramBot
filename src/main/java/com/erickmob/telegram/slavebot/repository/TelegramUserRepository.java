package com.erickmob.telegram.slavebot.repository;

import com.erickmob.telegram.slavebot.domain.TelegramUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
    TelegramUser findByUserID(Integer userId);
}
