package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "RoyaltyPayments")
public class RoyaltyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Royalty_ID")
    private int royaltyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "Royalty_Type", nullable = false)
    private RoyaltyType royaltyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "Payee_Type", nullable = false)
    private PayeeType payeeType;

    @Column(name = "Payee_ID", nullable = false)
    private int payeeId; // This could point to either an Author or Publisher

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "Payment_Date", nullable = false)
    private LocalDateTime paymentDate;

    // Enums
    public enum RoyaltyType {
        purchase,
        rental
    }

    public enum PayeeType {
        author,
        publisher
    }

    // Getters and Setters

    public int getRoyaltyId() {
        return royaltyId;
    }

    public void setRoyaltyId(int royaltyId) {
        this.royaltyId = royaltyId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RoyaltyType getRoyaltyType() {
        return royaltyType;
    }

    public void setRoyaltyType(RoyaltyType royaltyType) {
        this.royaltyType = royaltyType;
    }

    public PayeeType getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(PayeeType payeeType) {
        this.payeeType = payeeType;
    }

    public int getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(int payeeId) {
        this.payeeId = payeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
    	
        this.paymentDate = paymentDate;
    }
}
