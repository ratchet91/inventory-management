package com.inventory.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.app.models.InventoryTransaction;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryTransaction, Long> {

}
