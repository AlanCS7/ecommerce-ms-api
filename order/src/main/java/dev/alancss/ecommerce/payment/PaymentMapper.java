package dev.alancss.ecommerce.payment;

import dev.alancss.ecommerce.customer.CustomerResponse;
import dev.alancss.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public PaymentRequest toPaymentRequest(Order order, CustomerResponse customer) {
        return new PaymentRequest(
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
    }
}
