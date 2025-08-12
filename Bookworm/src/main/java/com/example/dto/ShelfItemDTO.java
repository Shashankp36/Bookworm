package com.example.dto;

import com.example.model.ShelfItem;
import java.time.LocalDate;

public class ShelfItemDTO {

    private int shelfItemId;
    private String accessType;
    private String productTitle;
    private String userName;
    private String coverUrl;   
    private String productUrl; 
    private String format;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public ShelfItemDTO(ShelfItem item) {
        this.shelfItemId = item.getShelfItemId();
        this.accessType = item.getAccessType().toString();

        if (item.getPurchase() != null) {
            this.productTitle = item.getPurchase().getProduct().getTitle();
            this.userName = item.getPurchase().getUser().getUserName();
            this.format = item.getPurchase().getProduct().getFormat().getFormatName();
            this.coverUrl = item.getPurchase().getProduct().getCoverUrl(); 
            this.productUrl = item.getPurchase().getProduct().getProductUrl(); 
        } else if (item.getRental() != null) {
            this.productTitle = item.getRental().getProduct().getTitle();
            this.userName = item.getRental().getUser().getUserName();
            this.rentalStartDate = item.getRental().getRentalStart();
            this.rentalEndDate = item.getRental().getRentalEnd();
            this.format = item.getRental().getProduct().getFormat().getFormatName();
            this.coverUrl = item.getRental().getProduct().getCoverUrl(); 
            this.productUrl = item.getRental().getProduct().getProductUrl(); 
        }
    }

    // Getters and Setters

    public int getShelfItemId() {
        return shelfItemId;
    }

    public void setShelfItemId(int shelfItemId) {
        this.shelfItemId = shelfItemId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
