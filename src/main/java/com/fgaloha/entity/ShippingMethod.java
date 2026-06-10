package com.fgaloha.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_methods")
public class ShippingMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_methods_gen")
	@SequenceGenerator(name = "shipping_methods_gen", sequenceName = "shipping_methods_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;

	@Column
	private int price;

	@Column
	private Integer freeThreshold;

	@Column
	private int displayOrder;

	public ShippingMethod() {
		super();
	}

	public ShippingMethod(Long id, String name, int price, Integer freeThreshold, int displayOrder) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.freeThreshold = freeThreshold;
		this.displayOrder = displayOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Integer getFreeThreshold() {
		return freeThreshold;
	}

	public void setFreeThreshold(Integer freeThreshold) {
		this.freeThreshold = freeThreshold;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ShippingMethod method))
			return false;
		return Objects.equals(id, method.id);
	}

	@Override
	public String toString() {

		return "ShippingMethod{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price=" + price +
				", freeThreshold=" + freeThreshold +
				", displayOrder=" + displayOrder +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
