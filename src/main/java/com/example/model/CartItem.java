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

	@ManyToOne
	@JoinColumn(name = "Applied_Discount_ID")
	private Discount appliedDiscount;
	
	public enum ItemType {
		PURCHASE,
	    RENT
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "Item_Type", nullable = false)
	private ItemType itemType;

	
	
	public CartItem() {
	}

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

	public Discount getAppliedDiscount() {
		return appliedDiscount;
	}

	public void setAppliedDiscount(Discount appliedDiscount) {
		this.appliedDiscount = appliedDiscount;
	}
	

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
}
