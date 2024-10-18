package dev.alancss.ecommerce.orderline;

import dev.alancss.ecommerce.order.Order;

public record OrderLineRequest(
        Order order,
        Integer productId,
        double quantity
) {
}
