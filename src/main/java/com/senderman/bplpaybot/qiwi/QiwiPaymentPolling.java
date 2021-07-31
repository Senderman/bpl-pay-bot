package com.senderman.bplpaybot.qiwi;

import com.annimon.tgbotsmodule.services.CommonAbsSender;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.senderman.bplpaybot.bis.BplIdSystem;
import com.senderman.bplpaybot.bis.exception.InternalServerErrorException;
import com.senderman.bplpaybot.model.Payment;
import com.senderman.bplpaybot.service.PaymentService;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.annimon.tgbotsmodule.api.methods.Methods.sendMessage;

@Component
public class QiwiPaymentPolling {

    private final PaymentService paymentService;
    private final BplIdSystem bplIdSystem;
    private final BillPaymentClient qiwiClient;
    private final ExecutorService threadPool;
    private final CommonAbsSender botSender;
    private final long timeout = TimeUnit.MINUTES.toMillis(5);
    private boolean doPolling;
    private final List<Payment> paymentsToDelete = new LinkedList<>();
    private final List<Payment> paymentsToUpdate = new LinkedList<>();

    public QiwiPaymentPolling(
            PaymentService paymentService,
            BplIdSystem bplIdSystem,
            BillPaymentClient qiwiClient,
            ExecutorService threadPool,
            Supplier<CommonAbsSender> botSenderSupplier
    ) {
        this.paymentService = paymentService;
        this.bplIdSystem = bplIdSystem;
        this.qiwiClient = qiwiClient;
        this.threadPool = threadPool;
        this.botSender = botSenderSupplier.get();
    }

    public void enablePolling() {
        if (doPolling)
            return;

        threadPool.execute(this::polling);
    }

    public void disablePolling() {
        this.doPolling = false;
    }

    private void polling() {
        while (doPolling) {
            paymentService.findUnpaid().forEach(this::processBill);
            paymentService.saveAll(paymentsToUpdate);
            paymentsToUpdate.clear();
            paymentService.deleteAll(paymentsToDelete);
            paymentsToDelete.clear();
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processBill(Payment payment) {
        var resp = qiwiClient.getBillInfo(payment.getId());
        var status = resp.getStatus().getValue();
        switch (status) {
            case PAID -> processPaidBill(payment);
            case REJECTED -> processRejectedBill(payment);
            case EXPIRED -> processExpiredBill(payment);
        }
    }

    private void processPaidBill(Payment payment) {
        try {
            var userId = payment.getTelegramId();
            var amount = payment.getCoins();
            bplIdSystem.increaseMoney(userId, amount);
            payment.setPaid(true);
            paymentsToUpdate.add(payment);
            sendMessage(userId, "На ваш счет успешно зачислено %d BPL Коинов!".formatted(amount)).callAsync(botSender);
        } catch (InternalServerErrorException e) {
            notifyError(e);
        }
    }

    private void processRejectedBill(Payment payment) {
        paymentsToDelete.add(payment);
        var userId = payment.getTelegramId();
        var amount = payment.getCoins();
        sendMessage(userId, "Ваш платеж на %d BPL Коинов был отклонен".formatted(amount)).callAsync(botSender);
    }

    private void processExpiredBill(Payment payment) {
        paymentsToDelete.add(payment);
        var userId = payment.getTelegramId();
        var amount = payment.getCoins();
        sendMessage(userId, "Оплата платежа на %d BPL Коинов истек!".formatted(amount)).callAsync(botSender);
    }

    private void notifyError(Throwable e) {
        // TODO implement
    }
}
