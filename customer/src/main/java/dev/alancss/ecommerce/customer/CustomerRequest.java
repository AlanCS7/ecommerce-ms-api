package dev.alancss.ecommerce.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "Firstname is required")
        String firstname,

        @NotBlank(message = "Lastname is required")
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        String email,

        @Valid
        Address address
) {
}
