package org.eshopping.service;



import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PreDestroy;
import org.eshopping.exception.ProductNotFoundException;
import org.eshopping.model.Inventory;
import org.eshopping.model.Product;
import org.eshopping.repository.IInventoryRepository;
import org.eshopping.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class InventoryService {

    @Value("${threshold.value:50}")
    private int threshold;

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final IProductRepository iProductRepository;

    private final IInventoryRepository iInventoryRepository;

    private final INotificationService iNotificationService;

//    private final RedisCacheService redisCacheService;

    private final ExecutorService executorService;

    public InventoryService(IProductRepository iProductRepository, IInventoryRepository iInventoryRepository, INotificationService iNotificationService, ExecutorService executorService) {
        this.iProductRepository = iProductRepository;
        this.iInventoryRepository = iInventoryRepository;
        this.iNotificationService = iNotificationService;
//        this.redisCacheService = redisCacheService;
        this.executorService = executorService;
    }

    @CacheEvict(value = "product", key = "#product.id")
    public synchronized Product addProduct(Product product) {
       return iProductRepository.save(product);
    }

    public void addProducts(List<Product> products) {
        long currentTime = System.nanoTime();
        logger.info("Adding product...");
        for (Product product : products) {
            executorService.submit(() -> addProduct(product));
        }
        logger.info("Products successfully added in {} ns", System.nanoTime() - currentTime);
    }

    @CachePut(cacheNames = "inventory", key = "#productId")
    public Inventory updateStock(Long productId, Integer stock) {
        Inventory inventory = iInventoryRepository.findByProduct(new Product(productId));
        if (inventory == null) {
            Product product = iProductRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
            inventory = new Inventory();
            inventory.setId(System.currentTimeMillis());
            inventory.setProduct(product);
        }
        inventory.setStock(stock);
        iInventoryRepository.save(inventory);

        //redisCacheService.saveInventoryToRedis(inventory);
        return new Inventory(productId, stock);
    }

    @Cacheable(value = "product",key = "#productId")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "getProductFromFallbackMechanism")
    public Product getProduct(Long productId) {
        return iProductRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Cacheable(value = "inventory",key = "#productId")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "getStockFromFallBackMechanism")
    public Inventory getStock(Long productId) {
        Inventory inventory = iInventoryRepository.findByProduct(new Product(productId));
        if (inventory == null) {
            return new Inventory(productId, 0); // No stock info available
        }
        return new Inventory(inventory.getProduct().getId(), inventory.getStock());
    }

    @PreDestroy
    public void shutDownExecutorService() {
        executorService.shutdown();
        logger.info("ExecutorService has been shut down.");
    }

    public Product getProductFromFallbackMechanism(Long productId, Throwable throwable){
        logger.error("Error while getting product data, Calling fallback logic-> {}" , throwable.getMessage());
        if(throwable instanceof ProductNotFoundException) {
            Product product = new Product();
            product.setDescription("No Product Available with for given id " + productId);
            return product;
        }
        return null;
    }

    public Inventory getStockFromFallBackMechanism(Long productId, Throwable throwable){
        logger.error("Error while getting inventory data, Calling fallback logic -> {}" , throwable.getMessage());
        return null;
    }


    @Scheduled(fixedRate = 300000)
    public void monitorStockLevels() {
        List<Inventory> lowStockProducts = iInventoryRepository.productsHavingStockBelowThreshold(threshold);
        if (!lowStockProducts.isEmpty()) {
            long time = System.nanoTime();
            for (Inventory inventory : lowStockProducts) {
                checkForProductHavingLowStock(inventory);
            }
        }
    }

    @Async
    public void checkForProductHavingLowStock(Inventory inventory){
        iNotificationService.sendNotification(inventory);
    }
}
