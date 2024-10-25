package dev.alancss.ecommerce.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @Positive(message = "Amount should be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method should be specified - BITCOIN|CASH|CREDIT_CARD|DEBIT_CARD|PAYPAL")
        PaymentMethod paymentMethod,

        @NotNull(message = "Order ID name is required")
        Integer orderId,

        String orderReference,

        @Valid
        Customer customer
) {
}
