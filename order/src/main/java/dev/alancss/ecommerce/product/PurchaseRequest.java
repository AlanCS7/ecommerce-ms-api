package dev.alancss.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product ID name is required")
        Integer productId,

        @Positive(message = "Product quantity should be positive")
        double quantity
) {
}
