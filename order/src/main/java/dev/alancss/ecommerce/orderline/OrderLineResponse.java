package dev.alancss.ecommerce.orderline;

public record OrderLineResponse(
        Integer orderLineId,
        double quantity
) {
}
