package org.eshopping.controller;

import org.eshopping.model.Inventory;
import org.eshopping.model.Product;
import org.eshopping.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        logger.info("Received request to add product..");
        Product savedProduct = inventoryService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PostMapping("/product/bulk-push")
    public ResponseEntity<?> addProduct(@RequestBody List<Product> products) {
        logger.info("Received request to add product in bulk..");
        inventoryService.addProducts(products);
        return ResponseEntity.ok("All Product Added..");
    }

    @PutMapping("/product/{productId}/stock")
    public ResponseEntity<Inventory> updateStock(@PathVariable Long productId, @RequestParam Integer stock) {
        logger.info("Received request to add stock for product id {}", productId);
        Inventory updatedStock = inventoryService.updateStock(productId, stock);
        return ResponseEntity.ok(updatedStock);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "productId") Long productId) {
        logger.info("Received request to get the product details for product id {}", productId);
        Product product = inventoryService.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/product/{productId}/stock")
    public ResponseEntity<Inventory> getStock(@PathVariable Long productId) {
        logger.info("Received request to get the stock details for product id {}", productId);
        Inventory stock = inventoryService.getStock(productId);
        return ResponseEntity.ok(stock);
    }

}
