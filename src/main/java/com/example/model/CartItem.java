package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CartItems")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Cart_Item_ID")
	private int cartItemId;

	@ManyToOne
	@JoinColumn(name = "Cart_ID", nullable = false)
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "Product_ID", nullable = false)
	private Product product;

	@Column(name = "Quantity", nullable = false)
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "Applied_Discount_ID")
	private Discount appliedDiscount;

	public CartItem() {}

	// Getters and Setters

	public int getCartItemId() {
	    return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
	    this.cartItemId = cartItemId;
	}

	public Cart getCart() {
	    return cart;
	}

	public void setCart(Cart cart) {
	    this.cart = cart;
	}

	public Product getProduct() {
	    return product;
	}

	public void setProduct(Product product) {
	    this.product = product;
	}

	public int getQuantity() {
	    return quantity;
	}

	public void setQuantity(int quantity) {
	    this.quantity = quantity;
	}

	public Discount getAppliedDiscount() {
	    return appliedDiscount;
	}

	public void setAppliedDiscount(Discount appliedDiscount) {
	    this.appliedDiscount = appliedDiscount;
	}
}