package dev.alancss.ecommerce.email;

import dev.alancss.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static dev.alancss.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static dev.alancss.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String to,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@ecommerce.com");
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        var context = new Context();
        context.setVariables(
                Map.of(
                        "customerName", customerName,
                        "amount", amount,
                        "orderReference", orderReference
                )
        );

        try {
            String templateName = PAYMENT_CONFIRMATION.getTemplate();
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to {} with template: {}", to, templateName);
        } catch (MessagingException e) {
            log.warn("Cannot send payment confirmation email to {}", to, e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String to,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@ecommerce.com");
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        var context = new Context();
        context.setVariables(
                Map.of(
                        "customerName", customerName,
                        "totalAmount", amount,
                        "orderReference", orderReference,
                        "products", products
                )
        );

        try {
            String templateName = ORDER_CONFIRMATION.getTemplate();
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to {} with template: {}", to, templateName);
        } catch (MessagingException e) {
            log.warn("Cannot send order confirmation email to {}", to, e);
        }
    }
}
