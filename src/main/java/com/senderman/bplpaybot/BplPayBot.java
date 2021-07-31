package com.senderman.bplpaybot;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;

import java.util.List;

public class BplPayBot implements BotModule {

    public static void main(String[] args) {
        final var profile = (args.length >= 1) ? args[0] : "";
        Runner.run(profile, List.of(new BplPayBot()));
    }

    @Override
    public @NotNull BotHandler botHandler(@NotNull Config config) {
        var handlerClass = BotHandler.class;
        var context = SpringApplication.run(handlerClass);
        return context.getBean(handlerClass);
    }

}
