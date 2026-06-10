package com.fgaloha.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_gen")
	@SequenceGenerator(name = "cart_items_gen", sequenceName = "cart_items_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductVariant variant;
	
	@Column
	private int quantity;
	
	public CartItem() {
		super();
	}

	public CartItem(Long id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProductVariant getVariant() {
		return variant;
	}

	public void setVariant(ProductVariant variant) {
		this.variant = variant;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CartItem item))
			return false;
		return Objects.equals(id, item.id);
	}

	@Override
	public String toString() {

		return "CartItem{" +
				
				", quantity=" + quantity +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
	public int getSubTotal() {
		return variant.getActualPrice() * quantity;
	}
}
