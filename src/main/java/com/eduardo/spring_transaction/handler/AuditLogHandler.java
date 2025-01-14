package com.eduardo.spring_transaction.handler;

import com.eduardo.spring_transaction.entity.AuditLog;
import com.eduardo.spring_transaction.entity.Order;
import com.eduardo.spring_transaction.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AuditLogHandler {
    private static final Logger log = LoggerFactory.getLogger(AuditLogHandler.class);
    private final AuditLogRepository auditLogRepository;

    public AuditLogHandler(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAuditDetails(Order order, String action) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setOrderId(order.getId());
            auditLog.setAction(action);
            auditLog.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(auditLog);

            log.info("Audit log saved: {}", action);
        } catch (Exception ex) {
            log.error("Failed to save audit log: {}", ex.getMessage());
        }
    }
}
