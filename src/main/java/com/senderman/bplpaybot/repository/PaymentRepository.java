package com.senderman.bplpaybot.repository;

import com.senderman.bplpaybot.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {

    Stream<Payment> findByIsPaid(boolean isPaid);

    Stream<Payment> findByTelegramId(int telegramId);

    Stream<Payment> findByTelegramIdAndIsPaid(int telegramId, boolean isPaid);

}
