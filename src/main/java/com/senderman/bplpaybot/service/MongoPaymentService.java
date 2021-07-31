package com.senderman.bplpaybot.service;

import com.senderman.bplpaybot.model.Payment;
import com.senderman.bplpaybot.repository.PaymentRepository;
import org.jvnet.hk2.annotations.Service;

import java.util.stream.Stream;

@Service
public class MongoPaymentService implements PaymentService {

    private final PaymentRepository repository;

    public MongoPaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public Stream<Payment> findUnpaid() {
        return repository.findByIsPaid(false);
    }

    @Override
    public Stream<Payment> findPaid() {
        return repository.findByIsPaid(true);
    }

    @Override
    public Stream<Payment> findByTelegramId(int telegramId) {
        return repository.findByTelegramId(telegramId);
    }

    @Override
    public Stream<Payment> findPaidByTelegramId(int telegramId) {
        return repository.findByTelegramIdAndIsPaid(telegramId, true);
    }

    @Override
    public Stream<Payment> findUnpaidByTelegramId(int telegramId) {
        return repository.findByTelegramIdAndIsPaid(telegramId, false);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Payment payment) {
        repository.delete(payment);
    }
}
