package com.senderman.bplpaybot.bis;

import com.senderman.bplpaybot.bis.entity.User;
import com.senderman.bplpaybot.bis.exception.InternalServerErrorException;

public interface BplIdSystem {

    User getUser(long id) throws InternalServerErrorException;

    void increaseMoney(long id, int amount) throws InternalServerErrorException;

}
