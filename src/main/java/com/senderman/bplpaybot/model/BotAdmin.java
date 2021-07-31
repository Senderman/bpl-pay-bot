package com.senderman.bplpaybot.model;

import org.springframework.data.annotation.Id;

public class BotAdmin {

    @Id
    private long id;
    private String nickname;

    public BotAdmin() {
    }

    public BotAdmin(long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
