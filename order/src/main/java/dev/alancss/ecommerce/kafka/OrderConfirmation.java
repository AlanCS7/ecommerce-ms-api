package dev.alancss.ecommerce.kafka;

import dev.alancss.ecommerce.customer.CustomerResponse;
import dev.alancss.ecommerce.order.PaymentMethod;
import dev.alancss.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
