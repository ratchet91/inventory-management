package com.inventory.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.inventory.app.TransactionType;
import com.inventory.app.models.InventoryItem;
import com.inventory.app.models.InventoryTransaction;
import com.inventory.app.models.request.AddOrDeductInventoryRq;
import com.inventory.app.repos.InventoryItemRepo;
import com.inventory.app.repos.InventoryRepo;

@SpringBootTest
public class InventoryTransactionServiceTest {

	@MockBean
	InventoryItemRepo itemRepo;

	@MockBean
	InventoryRepo transactionRepo;

	@Autowired
	InventoryTransactionService transactionService;

	@Test
	public void test_deductInventory() {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 1234);
		InventoryTransaction inventoryData = new InventoryTransaction(100L, itemData, 100, null,
				TransactionType.ADDITION);
		AddOrDeductInventoryRq addInventoryRq = new AddOrDeductInventoryRq(12300L, 10, TransactionType.ADDITION);

		when(itemRepo.findById(12300L)).thenReturn(Optional.of(itemData));
		when(transactionRepo.save(inventoryData)).thenReturn(inventoryData);
		when(itemRepo.save(itemData)).thenReturn(itemData);
		when(null)

		assertEquals(transactionService.deductInventory(addInventoryRq).getQuantity(), 90);

	}

	@Test
	public void test_addInventory() {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 1234);
		InventoryTransaction inventoryData = new InventoryTransaction(100L, itemData, 100, null,
				TransactionType.ADDITION);
		AddOrDeductInventoryRq addInventoryRq = new AddOrDeductInventoryRq(12300L, 10, TransactionType.ADDITION);

		when(itemRepo.findById(12300L)).thenReturn(Optional.of(itemData));
		when(transactionRepo.save(inventoryData)).thenReturn(inventoryData);
		when(itemRepo.save(itemData)).thenReturn(itemData);

		assertEquals(transactionService.deductInventory(addInventoryRq).getQuantity(), 110);

	}

}
