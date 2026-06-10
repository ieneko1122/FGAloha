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
@Table(name = "payment_methods")
public class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_methods_gen")
	@SequenceGenerator(name = "payment_methods_gen", sequenceName = "payment_methods_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;
	
	@Column
	private int displayOrder;
	
	public PaymentMethod() {
		super();
	}

	public PaymentMethod(Long id, String name, int displayOrder) {
		super();
		this.id = id;
		this.name = name;
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
		if (!(obj instanceof PaymentMethod method))
			return false;
		return Objects.equals(id, method.id);
	}

	@Override
	public String toString() {

		return "PaymentMethod{" +
				"id=" + id +
				", name='" + name + '\'' +
				", displayOrder=" + displayOrder +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
