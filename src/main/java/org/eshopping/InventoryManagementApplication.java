package org.eshopping;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class InventoryManagementApplication {

    private static final Logger logger = LoggerFactory.getLogger(InventoryManagementApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApplication.class, args);
    }
}