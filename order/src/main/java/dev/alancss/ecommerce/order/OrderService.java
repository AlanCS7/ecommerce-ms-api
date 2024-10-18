package dev.alancss.ecommerce.order;

import dev.alancss.ecommerce.customer.CustomerClient;
import dev.alancss.ecommerce.exception.InvalidCustomerException;
import dev.alancss.ecommerce.exception.OrderNotFoundException;
import dev.alancss.ecommerce.kafka.OrderProducer;
import dev.alancss.ecommerce.orderline.OrderLineRequest;
import dev.alancss.ecommerce.orderline.OrderLineService;
import dev.alancss.ecommerce.product.ProductClient;
import dev.alancss.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = customerClient.getCustomerById(request.customerId())
                .orElseThrow(() -> new InvalidCustomerException("Cannot create order: Customer does not exist"));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = orderRepository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            var productId = purchaseRequest.productId();
            double quantity = purchaseRequest.quantity();

            var orderLineRequest = new OrderLineRequest(order.getId(), productId, quantity);
            orderLineService.saveOrderLine(orderLineRequest);
        }

        // TODO start payment process

        var orderConfirmation = mapper.toOrderConfirmation(order, customer, purchasedProducts);
        orderProducer.sendOrderConfirmation(orderConfirmation);

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order with ID '%d' not found.".formatted(orderId)
                ));
    }
}
