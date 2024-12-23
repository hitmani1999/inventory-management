package org.eshoping.model;


import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Entity
@Table
public class Inventory implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer stock;

    public Inventory(Long id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public Inventory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", product=" + product +
                ", stock=" + stock +
                '}';
    }
}
