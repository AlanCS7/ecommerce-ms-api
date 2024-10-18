package dev.alancss.ecommerce.payment;

import dev.alancss.ecommerce.customer.CustomerResponse;
import dev.alancss.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
