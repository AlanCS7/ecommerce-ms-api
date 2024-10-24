package dev.alancss.ecommerce.kafka.order;

public record Customer(
        String id,
        String firstname,
        String lastname,
        String email
) {

    public String name() {
        return firstname + " " + lastname;
    }
}
