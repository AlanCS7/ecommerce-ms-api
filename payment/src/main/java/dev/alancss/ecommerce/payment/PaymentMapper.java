package dev.alancss.ecommerce.payment;

import dev.alancss.ecommerce.notification.PaymentNotification;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }

    public PaymentNotification toPaymentNotificationRequest(PaymentRequest request) {
        return new PaymentNotification(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email()
        );
    }
}
