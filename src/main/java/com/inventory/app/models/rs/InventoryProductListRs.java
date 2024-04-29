package com.inventory.app.models.rs;

import java.util.List;

import com.inventory.app.models.InventoryItem;

public class InventoryProductListRs {
	List<InventoryItem> products;
	int count;

	public InventoryProductListRs(List<InventoryItem> products, int count) {
		this.products = products;
		this.count = count;
	}

	public List<InventoryItem> getProducts() {
		return products;
	}

	public void setProducts(List<InventoryItem> products) {
		this.products = products;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
