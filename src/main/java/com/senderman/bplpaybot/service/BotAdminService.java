package com.senderman.bplpaybot.service;

import com.senderman.bplpaybot.model.BotAdmin;

public interface BotAdminService {

    BotAdmin save(BotAdmin botAdmin);

    void delete(BotAdmin botAdmin);

    void deleteById(long id);

    boolean existsById(long id);

}
