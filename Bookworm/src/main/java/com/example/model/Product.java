package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_ID")
    private int productId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Column(nullable = true)
    private BigDecimal 	rentPerDay;
    
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "Author_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Author author;

    
    
    @ManyToOne
    @JoinColumn(name = "Publisher_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "Language_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "Discount_ID", nullable = true)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Discount discount ;
    
    @ManyToOne
    @JoinColumn(name = "Format_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Format format;

    @ManyToOne
    @JoinColumn(name = "Genre_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Genre genre;

    @Column(name = "Royalty_Author", nullable = false)
    private BigDecimal royaltyAuthor;

    @Column(name = "Royalty_Publisher", nullable = false)
    private BigDecimal royaltyPublisher;

    @Column(name = "Product_Url")
    private String productUrl;

    @Column(name = "Cover_Url")
    private String coverUrl;

    @Column(name = "Available")
    private Boolean available;

    @Column(name = "Is_Active")
    private Boolean isActive = true;

    @Transient
    private BigDecimal discountedPrice;

    
    @Column(name = "Created_At", insertable = false, updatable = false)
    private Timestamp createdAt;

    // Getters and Setters

    
    
    public int getProductId() {
        return productId;
    }

    public BigDecimal getRentPerDay() {
		return rentPerDay;
	}

	public void setRentPerDay(BigDecimal rentPerDay) {
		this.rentPerDay = rentPerDay;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BigDecimal getRoyaltyAuthor() {
        return royaltyAuthor;
    }

    public void setRoyaltyAuthor(BigDecimal royaltyAuthor) {
        this.royaltyAuthor = royaltyAuthor;
    }

    public BigDecimal getRoyaltyPublisher() {
        return royaltyPublisher;
    }

    public void setRoyaltyPublisher(BigDecimal royaltyPublisher) {
        this.royaltyPublisher = royaltyPublisher;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public BigDecimal getDiscountedPrice() {
        if (discount == null) {
            return price;
        }

        switch (discount.getDiscountType()) {
            case flat:
                return price.subtract(discount.getValue()).max(BigDecimal.ZERO);
            case percentage:
                BigDecimal percentageOff = price.multiply(discount.getValue().divide(BigDecimal.valueOf(100)));
                return price.subtract(percentageOff).max(BigDecimal.ZERO);
            default:
                return price;
        }
    }

}
