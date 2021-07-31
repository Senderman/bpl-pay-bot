package com.senderman.bplpaybot;

import com.annimon.tgbotsmodule.commands.authority.Authority;
import com.senderman.bplpaybot.service.BotAdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.EnumSet;

@Component
public class BotAuthority implements Authority<Role> {

    private final long mainAdminId;
    private final BotAdminService botAdminService;

    public BotAuthority(@Value("${main-admin-id}") long mainAdminId, BotAdminService botAdminService) {
        this.mainAdminId = mainAdminId;
        this.botAdminService = botAdminService;
    }

    @Override
    public boolean hasRights(Update update, User user, EnumSet<Role> roles) {
        if (user.getId().equals(mainAdminId))
            return true;
        if (roles.contains(Role.USER))
            return true;

        return botAdminService.existsById(user.getId());
    }
}
