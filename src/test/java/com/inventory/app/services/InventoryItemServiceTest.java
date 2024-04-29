package com.inventory.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.inventory.app.models.InventoryItem;
import com.inventory.app.models.request.AddProductRq;
import com.inventory.app.repos.InventoryItemRepo;

@SpringBootTest
public class InventoryItemServiceTest {

	@MockBean
	InventoryItemRepo itemRepo;

	@Autowired
	InventoryItemService itemService;

	@Test
	public void test_getAllProducts() {

		List<InventoryItem> itemData = new ArrayList<>();
		itemData.add(new InventoryItem(123L, "Test Product", 1234));
		itemData.add(new InventoryItem(124L, "Test Product2", 134));
		when(itemRepo.findAll()).thenReturn(itemData);
		assertEquals(itemData.size(), itemService.getAllProducts().getCount());

	}

	@Test
	public void test_getProductById() {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 1234);
		when(itemRepo.findById(12300L)).thenReturn(Optional.of(itemData));
		assertEquals("Test Product", itemService.getProductById(12300L).getProductName());
	}

	@Test
	public void test_findProductById() {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 1234);
		when(itemRepo.findById(12300L)).thenReturn(Optional.of(itemData));
		assertEquals(true, itemService.findProductById(12300L));
	}

	@Test
	public void test_addProductData() {
		InventoryItem itemData = new InventoryItem(133L, "Test Product1", 1234);
		InventoryItem itemDataTest = new InventoryItem(null, "Test Product1", 1234);

		AddProductRq rqData = new AddProductRq("Test Product1", 1234);
		when(itemRepo.save(itemDataTest)).thenReturn(itemData);
		InventoryItem reString = itemService.addProductData(rqData);
		assertEquals("Test Product1", itemService.addProductData(rqData).getProductName());
	}

	@Test
	public void test_deleteProduct() {
		InventoryItem itemData = new InventoryItem(122L, "Test Product1", 0);
		when(itemRepo.findById(122L)).thenReturn(Optional.of(itemData));
		itemService.deleteProduct(122L);
		verify(itemRepo, times(1)).deleteById(122L);

	}

}
