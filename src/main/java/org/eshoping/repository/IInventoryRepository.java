package org.eshoping.repository;

import org.eshoping.model.Inventory;
import org.eshoping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProduct(Product product);
}
