package dev.alancss.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Customer(
        String id,

        @NotBlank(message = "Firstname is required")
        String firstname,

        @NotBlank(message = "Lastname is required")
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        String email
) {
}
