package com.senderman.bplpaybot.service;

import com.senderman.bplpaybot.model.Payment;

import java.util.stream.Stream;

public interface PaymentService {

    Payment save(Payment payment);

    Stream<Payment> findUnpaid();

    Stream<Payment> findPaid();

    Stream<Payment> findByTelegramId(int telegramId);

    Stream<Payment> findPaidByTelegramId(int telegramId);

    Stream<Payment> findUnpaidByTelegramId(int telegramId);

    void deleteById(String id);

    void delete(Payment payment);

}
