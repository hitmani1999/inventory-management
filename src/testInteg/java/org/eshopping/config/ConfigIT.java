package org.eshopping.config;


import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

@Configuration
@EnableAutoConfiguration
//@ComponentScan(basePackages = {"org.eshopping"})
public class ConfigIT {
    static MySQLContainer<?> mysqlContainer;
    static GenericContainer<?> redisContainer;

    @BeforeAll
    static void setup() {
        // MySQL Container
        mysqlContainer = new MySQLContainer<>("mysql:8.0.33")
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");
        mysqlContainer.start();

        System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
        System.setProperty("spring.datasource.password", mysqlContainer.getPassword());

        // Redis Container
        redisContainer = new GenericContainer<>("redis:7.0.0")
                .withExposedPorts(6379);
        redisContainer.start();

        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
    }

    @AfterAll
    static void teardown() {
        mysqlContainer.stop();
        redisContainer.stop();
    }

}
