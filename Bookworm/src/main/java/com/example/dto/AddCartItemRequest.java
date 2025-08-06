package com.example.dto;

import com.example.model.CartItem.ItemType;

public class AddCartItemRequest {
    private int productId;
    private ItemType itemType;
    private Integer days; // optional

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
