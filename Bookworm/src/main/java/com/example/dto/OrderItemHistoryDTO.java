package com.example.dto;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderItemHistoryDTO {

    private int orderId;
    private int productId;
    private String productType; // "ebook" or "audiobook"
    private BigDecimal pricePaid;
    private LocalDateTime purchaseDate; // for purchase
    private LocalDate rentalStart;      // for rental
    private LocalDate rentalEnd;        // for rental

    // Constructors
    public OrderItemHistoryDTO() {}

    public OrderItemHistoryDTO(int orderId, int productId, String productType,
                               BigDecimal pricePaid, LocalDateTime purchaseDate,
                               LocalDate rentalStart, LocalDate rentalEnd) {
        this.orderId = orderId;
        this.productId = productId;
        this.productType = productType;
        this.pricePaid = pricePaid;
        this.purchaseDate = purchaseDate;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
    }

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(BigDecimal pricePaid) {
		this.pricePaid = pricePaid;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public LocalDate getRentalStart() {
		return rentalStart;
	}

	public void setRentalStart(LocalDate rentalStart) {
		this.rentalStart = rentalStart;
	}

	public LocalDate getRentalEnd() {
		return rentalEnd;
	}

	public void setRentalEnd(LocalDate rentalEnd) {
		this.rentalEnd = rentalEnd;
	}

    // Getters and setters
    // (You can use Lombok @Data or manually generate them)
}
