package com.senderman.bplpaybot.bis.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result<T> {

    private final boolean ok;
    private final T result;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Result(@JsonProperty("ok") boolean ok, @JsonProperty("result") T result) {
        this.ok = ok;
        this.result = result;
    }

    public boolean isOk() {
        return ok;
    }

    public T getResult() {
        return result;
    }

}
