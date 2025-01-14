package com.eduardo.spring_transaction.repository;

import com.eduardo.spring_transaction.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
