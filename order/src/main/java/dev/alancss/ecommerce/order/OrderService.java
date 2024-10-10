package dev.alancss.ecommerce.order;

import dev.alancss.ecommerce.customer.CustomerClient;
import dev.alancss.ecommerce.exception.InvalidCustomerException;
import dev.alancss.ecommerce.orderline.OrderLineRequest;
import dev.alancss.ecommerce.orderline.OrderLineService;
import dev.alancss.ecommerce.product.ProductClient;
import dev.alancss.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = customerClient.getCustomerById(request.customerId())
                .orElseThrow(() -> new InvalidCustomerException("Cannot create order: Customer does not exist"));

        var purchaseProducts = productClient.purchaseProducts(request.products());

        var order = orderRepository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            var productId = purchaseRequest.productId();
            double quantity = purchaseRequest.quantity();

            var orderLineRequest = new OrderLineRequest(order.getId(), productId, quantity);
            orderLineService.saveOrderLine(orderLineRequest);
        }

        // TODO start payment process

        // TODO send the order confirmation --> notification-ms (kafka)

        return order.getId();
    }
}
