package com.inventory.app.models.request;

import com.inventory.app.TransactionType;

public class AddOrDeductInventoryRq {
	private Long id;
	private int quantity;
	private TransactionType transactionType;

	public AddOrDeductInventoryRq() {

	}

	public AddOrDeductInventoryRq(Long id, int quantity, TransactionType transactionType) {
		id = this.id;
		quantity = this.quantity;
		transactionType = this.transactionType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}
