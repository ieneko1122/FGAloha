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
@Table(name = "colors")
public class Color {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "colors_gen")
	@SequenceGenerator(name = "colors_gen", sequenceName = "colors_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;

	@Column
	private String colorCode;

	@Column
	private int displayOrder;

	public Color() {
		super();
	}

	public Color(Long id, String name, String colorCode, int displayOrder) {
		super();
		this.id = id;
		this.name = name;
		this.colorCode = colorCode;
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

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
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
		if (!(obj instanceof Color color))
			return false;
		return Objects.equals(id, color.id);
	}

	@Override
	public String toString() {

		return "Color{" +
				"id=" + id +
				", name='" + name + '\'' +
				", colorCode='" + colorCode + '\'' +
				", displayOrder=" + displayOrder +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
