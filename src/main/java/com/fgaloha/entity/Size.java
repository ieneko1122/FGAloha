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
@Table(name = "sizes")
public class Size {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sizes_gen")
	@SequenceGenerator(name = "sizes_gen", sequenceName = "sizes_seq", allocationSize = 1)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private int displayOrder;
	
	public Size() {
		super();
	}

	public Size(Long id, String name, int displayOrder) {
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
		if (!(obj instanceof Size size))
			return false;
		return Objects.equals(id, size.id);
	}
	
	@Override
	public String toString() {

		return "Size{" +
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
