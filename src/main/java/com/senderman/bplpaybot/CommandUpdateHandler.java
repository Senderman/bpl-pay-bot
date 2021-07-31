package com.senderman.bplpaybot;

import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.authority.Authority;
import com.senderman.bplpaybot.command.CommandExecutor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CommandUpdateHandler extends CommandRegistry<Role> {

    public CommandUpdateHandler(
            BotHandler botHandler,
            Authority<Role> authority,
            Set<CommandExecutor> commands
    ) {
        super(botHandler, authority);
        splitCallbackCommandByWhitespace();
        commands.forEach(this::register);
    }


}
