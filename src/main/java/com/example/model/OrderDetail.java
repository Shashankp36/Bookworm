package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_Detail_ID")
    private int orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "Product_Type", nullable = false)
    private ProductType productType;

    @Column(name = "Unit_Price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "Subtotal", nullable = false)
    private BigDecimal subtotal;

    public enum ProductType {
        ebook,
        audiobook
    }

    // Getters and Setters

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    // ✅ Custom setter to set enum using a String (format name)
    public void setProductType(String productType) {
        try {
            this.productType = ProductType.valueOf(productType.toLowerCase());
        } catch (IllegalArgumentException e) {
            this.productType = null; // or set default like ProductType.ebook if needed
        }
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
