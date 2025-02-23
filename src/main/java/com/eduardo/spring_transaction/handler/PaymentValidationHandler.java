package com.eduardo.spring_transaction.handler;

import com.eduardo.spring_transaction.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentValidationHandler {

    private static final Logger log = LoggerFactory.getLogger(PaymentValidationHandler.class);

    public void validatePayment(Order order) {
        boolean successful = isPaymentSuccessful(order);

        if (!successful) {
            throw new RuntimeException("Payment validation failed.");
        }
        log.info("Payment validation successful for Order ID: {}", order.getId());
    }

    private boolean isPaymentSuccessful(Order order) {
        return order.getTotalPrice() <= 100000;
    }
}
