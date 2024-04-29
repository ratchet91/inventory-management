package com.inventory.app.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.app.TransactionType;
import com.inventory.app.models.InventoryItem;
import com.inventory.app.models.InventoryTransaction;
import com.inventory.app.models.request.AddOrDeductInventoryRq;
import com.inventory.app.models.rs.InventoryProductListRs;
import com.inventory.app.services.InventoryItemService;
import com.inventory.app.services.InventoryTransactionService;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

	@MockBean
	InventoryItemService productService;

	@MockBean
	InventoryTransactionService transactionService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void test_getAllProducts() throws JsonProcessingException, Exception {
		List<InventoryItem> itemData = new ArrayList<>();
		itemData.add(new InventoryItem(123L, "Test Product", 1234));
		itemData.add(new InventoryItem(124L, "Test Product2", 134));
		when(productService.getAllProducts()).thenReturn(new InventoryProductListRs(itemData, itemData.size()));

		mockMvc.perform(get("/inventory/getAllProducts").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(itemData))).andExpect(status().isOk());

	}

	@Test
	public void test_getProductsById() throws JsonProcessingException, Exception {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 1234);
		when(productService.getProductById(12300L)).thenReturn(itemData);
		mockMvc.perform(get("/inventory/getProductsById/{id}", 12300L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void test_deleteProductById() throws JsonProcessingException, Exception {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 1234);
		doNothing().when(productService).deleteProduct(12300L);
		mockMvc.perform(delete("/inventory/deleteProductById/{id}", 12300L).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(itemData))).andExpect(status().isOk());

	}

	@Test
	public void test_addProductQuantity() throws JsonProcessingException, Exception {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 10);
		InventoryTransaction transactionData = new InventoryTransaction(null, itemData, 10, null,
				TransactionType.ADDITION);
		AddOrDeductInventoryRq addInventoryRq = new AddOrDeductInventoryRq(12300L, 10, TransactionType.ADDITION);
		when(transactionService.getInventoryItemById(12300L)).thenReturn(itemData);
		when(transactionService.addInventory(addInventoryRq)).thenReturn(new InventoryItem(12300L, "Test Product", 20));

		mockMvc.perform(post("/inventory/product/addQuantity").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 12300, \"quantity\": 10, \"transactionType\": \"ADDITION\"}"))
				.andExpect(status().isOk());

	}

	@Test
	public void test_deductProductQuantity() throws JsonProcessingException, Exception {
		InventoryItem itemData = new InventoryItem(12300L, "Test Product", 20);
		InventoryTransaction transactionData = new InventoryTransaction(null, itemData, 10, null,
				TransactionType.ADDITION);
		AddOrDeductInventoryRq addInventoryRq = new AddOrDeductInventoryRq(12300L, 10, TransactionType.ADDITION);
		when(transactionService.getInventoryItemById(12300L)).thenReturn(itemData);
		when(transactionService.addInventory(addInventoryRq)).thenReturn(new InventoryItem(12300L, "Test Product", 10));

		mockMvc.perform(post("/inventory/product/deductQuantity").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 12300, \"quantity\": 5, \"transactionType\": \"DEDUCTION\"}"))
				.andExpect(status().isOk()).andExpect(status().isOk());

	}

}
