package dev.alancss.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {

    public String customerName() {
        return customerFirstname + " " + customerLastname;
    }
}
