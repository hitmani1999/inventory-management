package org.eshopping.repository;

import org.eshopping.model.Inventory;
import org.eshopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProduct(Product product);

    @Query("select new org.eshopping.model.Inventory(p.id, i.stock) from Inventory i right join i.product p where stock < :threshold or i.stock is null")
    List<Inventory> productsHavingStockBelowThreshold(@Param("threshold") int threshold);

}
