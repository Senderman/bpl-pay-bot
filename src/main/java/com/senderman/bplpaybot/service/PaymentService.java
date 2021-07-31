package com.senderman.bplpaybot.service;

import com.senderman.bplpaybot.model.Payment;

import java.util.Collection;
import java.util.stream.Stream;

public interface PaymentService {

    Payment save(Payment payment);

    void saveAll(Collection<Payment> payments);

    Stream<Payment> findUnpaid();

    Stream<Payment> findPaid();

    Stream<Payment> findByTelegramId(int telegramId);

    Stream<Payment> findPaidByTelegramId(int telegramId);

    Stream<Payment> findUnpaidByTelegramId(int telegramId);

    void deleteById(String id);

    void delete(Payment payment);

    void deleteAll(Collection<Payment> payments);

}
