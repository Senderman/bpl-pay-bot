package com.senderman.bplpaybot.command;

import com.annimon.tgbotsmodule.commands.TextCommand;
import com.senderman.bplpaybot.Role;

import java.util.EnumSet;

public interface CommandExecutor extends TextCommand {

    @Override
    default EnumSet<Role> authority() {
        return EnumSet.of(Role.USER);
    }

    String getDescription();

    default boolean showInHelp() {
        return true;
    }

}
