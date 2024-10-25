package dev.alancss.ecommerce.order;

import dev.alancss.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        String reference,

        @Positive(message = "Amount should be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method should be specified - BITCOIN|CASH|CREDIT_CARD|DEBIT_CARD|PAYPAL")
        PaymentMethod paymentMethod,

        @NotBlank(message = "Customer ID name is required")
        String customerId,

        @NotEmpty(message = "The order must contain at least one product")
        List<PurchaseRequest> products
) {
}
