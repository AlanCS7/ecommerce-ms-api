package dev.alancss.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(

        @NotNull(message = "Product ID is required")
        Integer productId,

        @Positive(message = "Quantity should be positive")
        double quantity
) {
}
