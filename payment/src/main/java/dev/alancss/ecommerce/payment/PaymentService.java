package dev.alancss.ecommerce.payment;

import dev.alancss.ecommerce.notification.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = paymentRepository.save(mapper.toPayment(request));

        notificationProducer.sendNotification(mapper.toPaymentNotificationRequest(request));
        return payment.getId();
    }
}
