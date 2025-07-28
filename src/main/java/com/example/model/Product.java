package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "Format_ID")
    private Format format;

    @Column(unique = true)
    private String isbn;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "Language_ID")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "Genre_ID")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "Author_ID")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "Publisher_ID")
    private Publisher publisher;

    private BigDecimal royaltyAuthor;
    private BigDecimal royaltyPublisher;

    private String productUrl;
    private String coverUrl;

    private Boolean available;
    
    @Column(name = "Is_Active")
    private Boolean isActive = true;


    @Column(name = "Created_At", insertable = false, updatable = false)
    private Timestamp createdAt;

    // Getters and Setters

    public int getProductId() {
        return productId;
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

    public Format getFormat() {
        return format;
    }

    public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setFormat(Format format) {
        this.format = format;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
