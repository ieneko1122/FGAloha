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
@Table(name = "product_variants")
public class ProductVariant {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variants_gen")
	@SequenceGenerator(name = "product_variants_gen", sequenceName = "product_variants_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Size size;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Color color;
	
	@Column
	private int stock;
	
	@Column
	private int priceDiff;
	
	public ProductVariant() {
		super();
	}

	public ProductVariant(Long id, int stock, int priceDiff) {
		super();
		this.id = id;
		this.stock = stock;
		this.priceDiff = priceDiff;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPriceDiff() {
		return priceDiff;
	}

	public void setPriceDiff(int priceDiff) {
		this.priceDiff = priceDiff;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProductVariant variant))
			return false;
		return Objects.equals(id, variant.id);
	}

	@Override
	public String toString() {

		return "ProductVariants{" +
				"id=" + id +
				", stock=" + stock +
				", priceDiff=" + priceDiff +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public int getActualPrice() {
		return product.getPrice() + priceDiff; 
	}
}
