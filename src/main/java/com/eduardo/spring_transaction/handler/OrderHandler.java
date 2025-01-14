package com.eduardo.spring_transaction.handler;

import com.eduardo.spring_transaction.entity.Order;
import com.eduardo.spring_transaction.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderHandler.class);
    private final OrderRepository repository;

    public OrderHandler(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveOrder(Order order) {
        log.info("Saving order for customer: {}", order.getId());
        return repository.save(order);
    }
}
