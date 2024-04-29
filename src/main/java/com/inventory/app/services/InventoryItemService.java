package com.inventory.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inventory.app.InventoryException;
import com.inventory.app.models.InventoryItem;
import com.inventory.app.models.request.AddProductRq;
import com.inventory.app.models.rs.InventoryProductListRs;
import com.inventory.app.repos.InventoryItemRepo;

@Service
public class InventoryItemService {

	private InventoryItemRepo itemRepo;
	private static final Logger logger = LoggerFactory.getLogger(InventoryItemService.class);

	public InventoryItemService(InventoryItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}

	public InventoryProductListRs getAllProducts() {
		List<InventoryItem> productList = new ArrayList<>();
		itemRepo.findAll().forEach(productList::add);

		return new InventoryProductListRs(productList, productList.size());
	}

	public InventoryItem getProductById(Long id) {
		logger.info("The value of id: " + id);
		Optional<InventoryItem> itemData = itemRepo.findById(id);
		if (itemData.isEmpty()) {
			logger.info("Product for product id: " + id + " is not present");
			throw new InventoryException("Product Not Found");
		} else {
			return itemData.get();
		}
	}

	public boolean findProductById(Long id) {
		logger.info("The value of id: " + id);
		Optional<InventoryItem> itemData = itemRepo.findById(id);
		if (itemData.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public InventoryItem addProductData(AddProductRq productRq) {
		logger.info("Product data : " + productRq.getProductName() + " & : " + productRq.getQuantity());

		InventoryItem itemObj = new InventoryItem(productRq.getProductName(), productRq.getQuantity());
		return itemRepo.save(itemObj);

	}

	public void deleteProduct(Long id) {
		InventoryItem itemObj = getProductById(id);
		if (itemObj.getQuantity() != 0) {
			throw new InventoryException("Product Quantity still available, Please don't delete");
		} else {
			itemRepo.deleteById(id);
		}

	}

}
