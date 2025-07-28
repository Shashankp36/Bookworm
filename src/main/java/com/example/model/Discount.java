package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Discount")
public class Discount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Discount_ID")
	private int discountId;

	@Enumerated(EnumType.STRING)
	@Column(name = "Discount_Type", nullable = false)
	private DiscountType discountType;

	@Column(name = "Value", nullable = false)
	private BigDecimal value;

	@ManyToOne
	@JoinColumn(name = "Product_ID")
	private Product product;

	@OneToMany(mappedBy = "appliedDiscount", cascade = CascadeType.ALL)
	private List<CartItem> cartItems;

	// Enum
	public enum DiscountType {
	    flat,
	    percentage
	}

	public Discount() {}

	// Getters and Setters
	public int getDiscountId() {
	    return discountId;
	}

	public void setDiscountId(int discountId) {
	    this.discountId = discountId;
	}

	public DiscountType getDiscountType() {
	    return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
	    this.discountType = discountType;
	}

	public BigDecimal getValue() {
	    return value;
	}

	public void setValue(BigDecimal value) {
	    this.value = value;
	}

	public Product getProduct() {
	    return product;
	}

	public void setProduct(Product product) {
	    this.product = product;
	}

	public List<CartItem> getCartItems() {
	    return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
	    this.cartItems = cartItems;
	}

}
