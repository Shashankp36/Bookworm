package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(unique = true, nullable = false)
    private String genreName;

	public Genre() {
		super();
	}

	public Genre(String genreName) {
		super();
		this.genreName = genreName;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

    
    // Getters and Setters
    // ...
}
