package com.eduardo.spring_transaction.handler;

import com.eduardo.spring_transaction.entity.Product;
import com.eduardo.spring_transaction.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryHandler {

    private static final Logger log = LoggerFactory.getLogger(InventoryHandler.class);
    private final InventoryRepository inventoryRepository;

    public InventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateProductDetails(Product product) {
        if (product.getPrice() >= 50000) {
            log.error("Product price exceeds limit. Transaction will rollback.");
            throw new RuntimeException("Invalid product price.");
        }
        inventoryRepository.save(product);
    }

    public Product getProduct(Long id) {
        return inventoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not available with ID: " + id)
        );
    }
}
