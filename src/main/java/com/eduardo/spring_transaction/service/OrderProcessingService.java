package com.eduardo.spring_transaction.service;

import com.eduardo.spring_transaction.entity.Order;
import com.eduardo.spring_transaction.entity.Product;
import com.eduardo.spring_transaction.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessingService.class);
    private final InventoryHandler inventoryHandler;
    private final OrderHandler orderHandler;
    private final AuditLogHandler auditLogHandler;
    private final PaymentValidationHandler paymentValidationHandler;

    public OrderProcessingService(InventoryHandler inventoryHandler, OrderHandler orderHandler, AuditLogHandler auditLogHandler, PaymentValidationHandler paymentValidationHandler) {
        this.inventoryHandler = inventoryHandler;
        this.orderHandler = orderHandler;
        this.auditLogHandler = auditLogHandler;
        this.paymentValidationHandler = paymentValidationHandler;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order placeAnOrder(Order order) {
        try {
            Product product = inventoryHandler.getProduct(order.getProductId());
            validateStockAvailability(order, product);

            order.setTotalPrice(order.getQuantity() * product.getPrice());
            Order savedOrder = orderHandler.saveOrder(order);

            paymentValidationHandler.validatePayment(savedOrder);

            updateInventoryStock(order, product);

            auditLogHandler.logAuditDetails(savedOrder, "Order placement succeeded");

            return savedOrder;
        } catch (Exception ex) {
            auditLogHandler.logAuditDetails(order, "Order placement failed: " + ex.getMessage());
            throw ex;
        }
    }


    private void validateStockAvailability(Order order, Product product) {
        if (order.getQuantity() > product.getStockQuantity()) {
            throw new RuntimeException("Insufficient stock!");
        }
    }

    private void updateInventoryStock(Order order, Product product) {
        int updatedStock = product.getStockQuantity() - order.getQuantity();
        product.setStockQuantity(updatedStock);
        inventoryHandler.updateProductDetails(product);
    }
}