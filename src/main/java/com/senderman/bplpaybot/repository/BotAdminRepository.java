package com.senderman.bplpaybot.repository;

import com.senderman.bplpaybot.model.BotAdmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotAdminRepository extends CrudRepository<BotAdmin, Long> {
}
