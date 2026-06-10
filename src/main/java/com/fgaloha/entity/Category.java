package com.fgaloha.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_gen")
	@SequenceGenerator(name = "categories_gen", sequenceName = "categories_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;
	
	@Column
	private int displayOrder;
	
	@ManyToMany(mappedBy = "categories")
	private List<Product> products = new ArrayList<>();
	
	public Category() {
		super();
	}

	public Category(Long id, String name, int displayOrder) {
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

	public List<Product> getProducts() {
		return products;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Category category))
			return false;
		return Objects.equals(id, category.id);
	}

	@Override
	public String toString() {

		return "Category{" +
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
