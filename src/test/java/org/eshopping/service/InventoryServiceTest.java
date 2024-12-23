package org.eshopping.service;

import org.eshopping.model.Product;
import org.eshopping.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.util.AssertionErrors;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock
    private IProductRepository iProductRepository;

    private CacheManager cacheManager;
    private Cache productCache;
    @InjectMocks
    private InventoryService productService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheManager = new ConcurrentMapCacheManager("product");
        productCache = cacheManager.getCache("product");
    }

    @Test
    public void testAddProduct(){
        Product product = new Product();
        product.setId(1l);
        product.setName("Name");
        product.setDescription("Description");
        product.setPrice(100.0);

        when(iProductRepository.save(product)).thenReturn(product);

        AssertionErrors.assertNull("message", productCache.get(product.getId()));

        productService.addProduct(product);

        AssertionErrors.assertNull("message", productCache.get(product.getId()));
        verify(iProductRepository, times(1)).save(product);
    }
}
