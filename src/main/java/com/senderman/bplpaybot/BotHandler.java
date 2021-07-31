package com.senderman.bplpaybot;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.senderman.bplpaybot.util.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@SpringBootApplication
public class BotHandler extends com.annimon.tgbotsmodule.BotHandler {

    private final String botUsername;
    private final String botToken;
    private final long notifyChannelId;
    private final CommandRegistry<Role> commandRegistry;

    public BotHandler(
            @Value("${telegram-username}") String botUsername,
            @Value("${telegram-token}") String botToken,
            @Value("${notify-channel-id}") long notifyChannelId,
            @Lazy CommandRegistry<Role> commandRegistry) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.notifyChannelId = notifyChannelId;
        this.commandRegistry = commandRegistry;


        addMethodPreprocessor(SendMessage.class, m -> {
            m.enableHtml(true);
            m.disableWebPagePreview();
        });

        addMethodPreprocessor(EditMessageText.class, m -> {
            m.enableHtml(true);
            m.disableWebPagePreview();
        });
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (var update : updates) {
            try {
                onUpdate(update);
            } catch (RejectedExecutionException ignored) {
            } catch (Throwable e) {
                Methods.sendMessage()
                        .setChatId(notifyChannelId)
                        .setText("⚠️ <b>Ошибка обработки апдейта</b>\n\n" + ExceptionUtils.stackTraceAsString(e))
                        .enableHtml()
                        .disableWebPagePreview()
                        .callAsync(this);
            }
        }
    }

    @Override
    protected BotApiMethod<?> onUpdate(@NotNull Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getDate() + 120 < System.currentTimeMillis() / 1000)
                return null;
        }
        commandRegistry.handleUpdate(update);
        return null;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    // Telegram API exceptions are often the cause of huge logs. If anything goes wrong,
    // we will catch the exception in onUpdatesReceived
    @Override
    public void handleTelegramApiException(TelegramApiException ex) {
    }
}
