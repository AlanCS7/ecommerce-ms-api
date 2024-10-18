package dev.alancss.ecommerce.order;

import dev.alancss.ecommerce.customer.CustomerResponse;
import dev.alancss.ecommerce.kafka.OrderConfirmation;
import dev.alancss.ecommerce.product.PurchaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                .build();
    }

    public OrderConfirmation toOrderConfirmation(
            Order order, CustomerResponse customer, List<PurchaseResponse> purchasedProducts) {
        return new OrderConfirmation(
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                customer,
                purchasedProducts
        );
    }

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
