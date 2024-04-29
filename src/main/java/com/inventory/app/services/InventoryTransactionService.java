package com.inventory.app.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.app.InventoryException;
import com.inventory.app.TransactionType;
import com.inventory.app.models.InventoryItem;
import com.inventory.app.models.InventoryTransaction;
import com.inventory.app.models.request.AddOrDeductInventoryRq;
import com.inventory.app.repos.InventoryItemRepo;
import com.inventory.app.repos.InventoryRepo;

@Service
public class InventoryTransactionService {
	private InventoryRepo inventoryRepos;
	private InventoryItemRepo itemRepo;
	private static final Logger logger = LoggerFactory.getLogger(InventoryTransactionService.class);

	public InventoryTransactionService(InventoryRepo inventoryRepos, InventoryItemRepo itemRepo) {
		this.inventoryRepos = inventoryRepos;
		this.itemRepo = itemRepo;
	}

	@Transactional
	public InventoryItem getInventoryItemById(Long id) throws InventoryException {

		Optional<InventoryItem> itemData = itemRepo.findById(id);
		if (itemData.isEmpty()) {
			throw new InventoryException("Item not available in inventory");
		} else {
			return itemData.get();
		}

	}

	@Transactional
	public InventoryItem deductInventory(AddOrDeductInventoryRq transaction) throws InventoryException {
		// Retrieve the inventory item by its ID from the database

		InventoryItem itemData = getInventoryItemById(transaction.getId());

		int availableQuantity = itemData.getQuantity();
		int quantityToDeduct = transaction.getQuantity();
		if (availableQuantity < quantityToDeduct) {
			throw new InventoryException("Insufficient inventory");
		}
		itemData.setQuantity(availableQuantity - quantityToDeduct);
		InventoryTransaction inventoryData = new InventoryTransaction(null, itemData, quantityToDeduct, null,
				TransactionType.DEDUCTION);
		inventoryRepos.save(inventoryData);
		return itemRepo.save(itemData);

		// return availableQuantity - quantityToDeduct;
	}

	@Transactional
	public InventoryItem addInventory(AddOrDeductInventoryRq transaction) throws InventoryException {
		// Retrieve the Product item by its ID from the database
		// And make an entry for transaction in Inventory Transaction for record
		logger.info(" The value of itemId: " + transaction.getId());
		InventoryItem itemData = getInventoryItemById(transaction.getId());
		logger.info(" The value of productName from itemData: " + itemData.getProductName());
		int availableQuantity = itemData.getQuantity();
		int quantityToAdd = transaction.getQuantity();

		int newQuantity = availableQuantity + quantityToAdd;
		itemData.setQuantity(newQuantity);
		InventoryTransaction inventoryData = new InventoryTransaction(null, itemData, quantityToAdd, null,
				TransactionType.ADDITION);
		inventoryRepos.save(inventoryData);
		return itemRepo.save(itemData);
		// return itemData.getQuantity() + transaction.getQuantity();

	}

}
