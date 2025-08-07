
package com.example.dto;

import com.example.model.Product;
import java.math.BigDecimal;

public class ProductDTO {

    private int productId;
    private String title;
    private String format;
    private String coverUrl;
    private BigDecimal price;

    public ProductDTO() {}

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.title = product.getTitle();
        this.format = product.getFormat().getFormatName();
        this.coverUrl = product.getCoverUrl();
        this.price = product.getPrice();
    }

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

