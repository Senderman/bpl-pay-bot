package com.senderman.bplpaybot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("payment")
public class Payment {

    @Id
    private String id;

    private long telegramId;
    private int coins;
    private int price;
    private boolean isPaid;
    private String payUrl;

    public Payment() {
    }

    public Payment(String id, long telegramId, int coins, int price, boolean isPaid) {
        this.telegramId = telegramId;
        this.coins = coins;
        this.price = price;
        this.isPaid = isPaid;
    }

    public Payment(String id, long telegramId, int coins, int rubles) {
        this(id, telegramId, coins, rubles, false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
}
