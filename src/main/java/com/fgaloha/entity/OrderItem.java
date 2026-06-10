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
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_gen")
	@SequenceGenerator(name = "order_items_gen", sequenceName = "order_items_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductVariant variant;
	
	@Column
	private String productName;
	
	@Column
	private String sizeName;
	
	@Column
	private String colorName;
	
	@Column
	private int unitPrice;
	
	@Column
	private int quantity;
	
	public OrderItem() {
		super();
	}

	public OrderItem(Long id, String productName, String sizeName, String colorName, int unitPrice, int quantity) {
		super();
		this.id = id;
		this.productName = productName;
		this.sizeName = sizeName;
		this.colorName = colorName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductVariant getVariant() {
		return variant;
	}

	public void setVariant(ProductVariant variant) {
		this.variant = variant;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getSubTotal() {
		return unitPrice * quantity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OrderItem item))
			return false;
		return Objects.equals(id, item.id);
	}

	@Override
	public String toString() {

		return "OrderItem{" +
				"id=" + id +
				", productName='" + productName + '\'' +
				", sizeName='" + sizeName + '\'' +
				", colorName='" + colorName + '\'' +
				", unitPrice=" + unitPrice +
				", quantity=" + quantity +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
