package dev.alancss.ecommerce.notification;

import dev.alancss.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotification(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
