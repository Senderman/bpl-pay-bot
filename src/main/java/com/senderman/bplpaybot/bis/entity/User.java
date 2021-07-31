package com.senderman.bplpaybot.bis.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private final int id;
    private final int money;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public User(
            @JsonProperty("id") int id,
            @JsonProperty("money") int money
    ) {
        this.id = id;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }


}