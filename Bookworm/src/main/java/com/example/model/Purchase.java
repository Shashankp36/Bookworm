package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Purchases")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Purchase_ID")
	private int purchaseId;

	@ManyToOne
	@JoinColumn(name = "User_ID", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "Product_ID", nullable = false)
	private Product product;

	@Column(name = "Purchase_Date", nullable = false)
	private LocalDateTime purchaseDate;

	@Column(name = "Price_Paid", nullable = false)
	private BigDecimal pricePaid;

	@Column(name = "Author_Royalty", nullable = false)
	private BigDecimal authorRoyalty;

	@Column(name = "Publisher_Royalty", nullable = false)
	private BigDecimal publisherRoyalty;

	@Enumerated(EnumType.STRING)
	@Column(name = "Royalty_Type", nullable = false)
	private RoyaltyType royaltyType;

	// Enum for Royalty Type
	public enum RoyaltyType {
	    percentage,
	    fixed
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Order_ID", nullable = false)
	private Order order;

	

	// Constructors
	public Purchase() {}

	// Getters and Setters

	public int getPurchaseId() {
	    return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
	    this.purchaseId = purchaseId;
	}

	public User getUser() {
	    return user;
	}

	public void setUser(User user) {
	    this.user = user;
	}

	public Product getProduct() {
	    return product;
	}

	public void setProduct(Product product) {
	    this.product = product;
	}

	public LocalDateTime getPurchaseDate() {
	    return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
	    this.purchaseDate = purchaseDate;
	}

	public BigDecimal getPricePaid() {
	    return pricePaid;
	}

	public void setPricePaid(BigDecimal pricePaid) {
	    this.pricePaid = pricePaid;
	}

	public BigDecimal getAuthorRoyalty() {
	    return authorRoyalty;
	}

	public void setAuthorRoyalty(BigDecimal authorRoyalty) {
	    this.authorRoyalty = authorRoyalty;
	}

	public BigDecimal getPublisherRoyalty() {
	    return publisherRoyalty;
	}

	public void setPublisherRoyalty(BigDecimal publisherRoyalty) {
	    this.publisherRoyalty = publisherRoyalty;
	}

	public RoyaltyType getRoyaltyType() {
	    return royaltyType;
	}

	public void setRoyaltyType(RoyaltyType royaltyType) {
	    this.royaltyType = royaltyType;
	}
	public Order getOrder() {
	    return order;
	}

	public void setOrder(Order order) {
	    this.order = order;
	}

}