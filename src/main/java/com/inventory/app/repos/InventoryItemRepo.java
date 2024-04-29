package com.inventory.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.app.models.InventoryItem;

public interface InventoryItemRepo extends JpaRepository<InventoryItem, Long>{

}
