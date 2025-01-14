package com.eduardo.spring_transaction.repository;

import com.eduardo.spring_transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
