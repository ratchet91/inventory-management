package com.inventory.app.models;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.inventory.app.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private InventoryItem item;

	@Column(name = "QUANTITY_CHANGE")
	private int quantityChange;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

	private LocalDateTime timestamp;

	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSACTION_TYPE")
	private TransactionType transactionType; // "deduction" or "addition"

	// Constructors
	public InventoryTransaction(Long id, InventoryItem item, int quantityChange, LocalDateTime timestamp,
			TransactionType transactionType) {
		this.id = id;
		this.item = item;
		this.quantityChange = quantityChange;
		this.timestamp = timestamp;
		this.transactionType = transactionType;
	}

	// Getters and setters

	public InventoryItem getItem() {
		return item;
	}

	public void setItem(InventoryItem item) {
		this.item = item;
	}

	public int getQuantityChange() {
		return quantityChange;
	}

	public void setQuantityChange(int quantityChange) {
		this.quantityChange = quantityChange;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
}
