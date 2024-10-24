package dev.alancss.ecommerce.kafka;

import dev.alancss.ecommerce.email.EmailService;
import dev.alancss.ecommerce.kafka.order.OrderConfirmation;
import dev.alancss.ecommerce.kafka.payment.PaymentConfirmation;
import dev.alancss.ecommerce.notification.Notification;
import dev.alancss.ecommerce.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.alancss.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static dev.alancss.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentConfirmationNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consumed from payment-topic {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                paymentConfirmation.customerName(),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consumed from order-topic {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                orderConfirmation.customer().name(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
