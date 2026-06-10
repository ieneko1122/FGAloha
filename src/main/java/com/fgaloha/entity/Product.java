package com.fgaloha.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_gen")
	@SequenceGenerator(name = "products_gen", sequenceName = "products_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;

	@Column
	private int price;
	
	@Column
	private String description;
	
	@Column
	private String imageUrl;
	
	@Column
	private LocalDateTime createdAt;
	
	@ManyToMany
	@JoinTable(
			name = "product_categories",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	private List<Category> categories = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<ProductVariant> variants = new ArrayList<>();

	public Product() {
		super();
	}
	
	public Product(Long id, String name, int price, String description, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.imageUrl = imageUrl;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<ProductVariant> getVariants() {
		return variants;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product product))
			return false;
		return Objects.equals(id, product.id);
	}

	@Override
	public String toString() {

		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price=" + price +
				", description='" + description + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", createdAt='" + createdAt + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public boolean isSoldOut() {
		return variants.stream().allMatch(v -> v.getStock() ==0);
	}
	
	public long getColorCount() {
		return variants.stream()
				.map(ProductVariant::getColor)
				.distinct()
				.count();
	}
	
}
