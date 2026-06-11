package com.fgaloha.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_gen")
	@SequenceGenerator(name = "orders_gen", sequenceName = "orders_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Column
	private int totalPrice;
	
	@Column
	private int shippingFee;
	
	@Enumerated(EnumType.STRING)
	@Column
	private OrderStatus status = OrderStatus.PENDING;
	
	@Column
	private String shippingAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ShippingMethod shippingMethod;
	
	@Column
	private String shippingName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PaymentMethod paymentMethod;
	
	@Column
	private String paymentName;
	
	@Column
	private LocalDateTime orderedAt;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	public Order() {
		super();
	}

	public Order(Long id, int totalPrice, int shippingFee, OrderStatus status, String shippingAddress,
			String shippingName, String paymentName, LocalDateTime orderedAt) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.shippingFee = shippingFee;
		this.status = status;
		this.shippingAddress = shippingAddress;
		this.shippingName = shippingName;
		this.paymentName = paymentName;
		this.orderedAt = orderedAt;
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

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(int shippingFee) {
		this.shippingFee = shippingFee;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	@PrePersist
	protected void onOrder() {
		this.orderedAt = LocalDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Order order))
			return false;
		return Objects.equals(id, order.id);
	}

	@Override
	public String toString() {

		return "Order{" +
				"id=" + id +
				", totalPrice=" + totalPrice +
				", shippingFee=" + shippingFee +
				", status='" + status.getDisplayName() + '\'' +
				", shippingAddress='" + shippingAddress + '\'' +
				", shippingName='" + shippingName + '\'' +
				", paymentName='" + paymentName + '\'' +
				", orderedAt='" + orderedAt + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
