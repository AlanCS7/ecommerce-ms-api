package dev.alancss.ecommerce.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank(message = "Product description is required")
        String description,

        @PositiveOrZero(message = "Available quantity should be positive or zero")
        double availableQuantity,

        @PositiveOrZero(message = "Price should be positive")
        BigDecimal price,

        @NotNull(message = "Category ID is required")
        Integer categoryId
) {
}
