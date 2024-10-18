package dev.alancss.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper mapper;

    public void saveOrderLine(OrderLineRequest request) {
        var orderLine = mapper.toOrderLine(request);
        orderLineRepository.save(orderLine);
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .toList();
    }
}
