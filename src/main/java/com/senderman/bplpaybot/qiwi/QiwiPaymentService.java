package com.senderman.bplpaybot.qiwi;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import com.senderman.bplpaybot.model.Payment;
import com.senderman.bplpaybot.service.PaymentService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

@Component
public class QiwiPaymentService {

    private final PaymentService paymentService;
    private final BillPaymentClient billPaymentClient;

    public QiwiPaymentService(PaymentService paymentService, BillPaymentClient billPaymentClient) {
        this.paymentService = paymentService;
        this.billPaymentClient = billPaymentClient;
    }

    public Payment createPayment(long userId, String username, int amount, int price) {
        var payment = new Payment(UUID.randomUUID().toString(), userId, amount, price);
        var bill = createBill(payment, username);
        payment.setPayUrl(bill.getPayUrl());
        paymentService.save(payment);
        return payment;
    }

    private BillResponse createBill(Payment payment, String username) {
        var moneyAmount = new MoneyAmount(BigDecimal.valueOf(payment.getPrice()), Currency.getInstance("RUB"));
        try {
            return billPaymentClient.createBill(new CreateBillInfo(
                    payment.getId(),
                    moneyAmount,
                    "Донат через бота от " + username + " на " + payment.getCoins() + " коинов",
                    ZonedDateTime.now().plusDays(45),
                    null,
                    "https://qiwi.com"
            ));
        } catch (URISyntaxException e) {
            // Will never happen
            e.printStackTrace();
            return null;
        }
    }
}
