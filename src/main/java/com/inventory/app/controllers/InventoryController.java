package com.inventory.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.app.InventoryException;
import com.inventory.app.models.InventoryItem;
import com.inventory.app.models.request.AddOrDeductInventoryRq;
import com.inventory.app.models.request.AddProductRq;
import com.inventory.app.models.rs.InventoryProductListRs;
import com.inventory.app.services.InventoryItemService;
import com.inventory.app.services.InventoryTransactionService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryTransactionService inventoryService;

	@Autowired
	private InventoryItemService productService;

	@GetMapping("/getAllProducts")
	public ResponseEntity<InventoryProductListRs> getAllItems() {
		try {
			InventoryProductListRs itemsList = productService.getAllProducts();
			return new ResponseEntity<>(itemsList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getProductsById/{ID}")
	public ResponseEntity<InventoryItem> getItemsById(@PathVariable Long ID) {
		logger.info("The value for id: " + ID);
		InventoryItem productData = productService.getProductById(ID);
		return new ResponseEntity<>(productData, HttpStatus.OK);
	}

	@DeleteMapping("/deleteProductById/{id}")
	public ResponseEntity<HttpStatus> deleteItemById(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/product/add")
	public ResponseEntity<InventoryItem> addItems(@RequestBody AddProductRq productData) {
		if (productData.getProductName().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		InventoryItem itemObj = productService.addProductData(productData);
		return new ResponseEntity<>(itemObj, HttpStatus.OK);
	}

	@PostMapping("/product/deductQuantity")
	public ResponseEntity<InventoryItem> deductInventory(@RequestBody AddOrDeductInventoryRq transaction) {
		try {
			if (transaction.getId() == null || transaction.getQuantity() == 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			if (transaction.getQuantity() <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			InventoryItem updatedQuantity = inventoryService.deductInventory(transaction);
			return new ResponseEntity<>(updatedQuantity, HttpStatus.OK);
		} catch (InventoryException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/product/addQuantity")
	public ResponseEntity<InventoryItem> addInventory(@RequestBody AddOrDeductInventoryRq transaction) {
		try {
			if (transaction.getId() == null || transaction.getQuantity() == 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			if (transaction.getQuantity() <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			InventoryItem updatedQuantity = inventoryService.addInventory(transaction);
			return new ResponseEntity<>(updatedQuantity, HttpStatus.OK);
		} catch (InventoryException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
