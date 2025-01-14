package com.eduardo.spring_transaction.repository;

import com.eduardo.spring_transaction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Product, Long> {
}
