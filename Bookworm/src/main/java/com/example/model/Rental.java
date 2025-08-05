package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Rentals")
public class Rental {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "Rental_ID")
private int rentalId;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "User_ID", nullable = false)
private User user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "Product_ID", nullable = false)
private Product product;

@Column(name = "Rental_Start", nullable = false)
private LocalDate rentalStart;

@Column(name = "Rental_End", nullable = false)
private LocalDate rentalEnd;

@Column(name = "Price_Paid", nullable = false)
private BigDecimal pricePaid;

@Column(name = "Author_Royalty", nullable = false)
private BigDecimal authorRoyalty;

@Column(name = "Publisher_Royalty", nullable = false)
private BigDecimal publisherRoyalty;

@Enumerated(EnumType.STRING)
@Column(name = "Royalty_Type", nullable = false)
private RoyaltyType royaltyType;

// Enum for royalty type
public enum RoyaltyType {
    percentage,
    fixed
}
@ManyToOne
@JoinColumn(name = "Order_ID")
private Order order;

public Order getOrder() {
    return order;
}

public void setOrder(Order order) {
    this.order = order;
}


// Getters and Setters

public int getRentalId() {
    return rentalId;
}

public void setRentalId(int rentalId) {
    this.rentalId = rentalId;
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

public LocalDate getRentalStart() {
    return rentalStart;
}

public void setRentalStart(LocalDate startDate) {
    this.rentalStart = startDate;
}

public LocalDate getRentalEnd() {
    return rentalEnd;
}

public void setRentalEnd(LocalDate rentalEnd) {
    this.rentalEnd = rentalEnd;
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
}