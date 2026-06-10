package com.fgaloha.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_gen")
	@SequenceGenerator(name = "users_gen", sequenceName = "users_seq", allocationSize = 1)
	private Long id;

	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String name;
	
	@Column
	private String postalCode;
	
	@Column
	private String prefecture;
	
	@Column
	private String city;
	
	@Column
	private String addressLine;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String role;
	
	@Column
	private boolean emailMagazine;
	
	@Column
	private LocalDateTime createdAt;
	
	public User() {
		super();
	}

	public User(Long id, String email, String password, String name, String postalCode, String prefecture, String city,
			String addressLine, String phoneNumber, String role, boolean emailMagazine) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.postalCode = postalCode;
		this.prefecture = prefecture;
		this.city = city;
		this.addressLine = addressLine;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.emailMagazine = emailMagazine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEmailMagazine() {
		return emailMagazine;
	}

	public void setEmailMagazine(boolean emailMagazine) {
		this.emailMagazine = emailMagazine;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User user))
			return false;
		return Objects.equals(id, user.id);
	}

	@Override
	public String toString() {

		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", name='" + name + '\'' +
				", postalCode='" + postalCode + '\'' +
				", prefecture='" + prefecture + '\'' +
				", city='" + city + '\'' +
				", addressLine='" + addressLine + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", role='" + role + '\'' +
				", emailMagazine=" + emailMagazine +
				", createdAt='" + createdAt + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public boolean hasAddress() {
	  return Stream.of(postalCode, prefecture, city, addressLine)
			  .allMatch(s -> s != null && !s.isBlank());
	}
}
