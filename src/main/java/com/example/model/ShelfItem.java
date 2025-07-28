package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Shelf_Items")
public class ShelfItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShelfItem_ID")
    private int shelfItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Shelf_ID", nullable = false)
    private Shelf shelf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Purchase_ID")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Rental_ID")
    private Rental rental;

    @Enumerated(EnumType.STRING)
    @Column(name = "Access_Type", nullable = false)
    private AccessType accessType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Format_ID", nullable = false)
    private Format format;

    public enum AccessType {
        purchase,
        rental
    }

    // Getters and Setters

    public int getShelfItemId() {
        return shelfItemId;
    }

    public void setShelfItemId(int shelfItemId) {
        this.shelfItemId = shelfItemId;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
}
