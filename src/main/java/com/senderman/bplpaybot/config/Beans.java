package com.senderman.bplpaybot.config;

import com.annimon.tgbotsmodule.services.CommonAbsSender;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.senderman.bplpaybot.BotHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Configuration
public class Beans {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public BillPaymentClient qiwiClient(@Value("${qiwi-secret}") String qiwiToken) {
        return BillPaymentClientFactory.createDefault(qiwiToken);
    }

    @Bean
    public Supplier<CommonAbsSender> commonAbsSenderSupplier(BotHandler botHandler) {
        return () -> botHandler;
    }

    @Bean
    public ExecutorService threadPool() {
        return Executors.newSingleThreadExecutor();
    }

}
