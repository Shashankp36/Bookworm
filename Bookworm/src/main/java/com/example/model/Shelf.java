package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;


@Entity
@Table(name = "Shelfs")
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Shelf_ID")
    private Integer shelfId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ShelfItem> items;

    // Getters and Setters

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShelfItem> getItems() {
        return items;
    }

    public void setItems(List<ShelfItem> items) {
        this.items = items;
    }
}
