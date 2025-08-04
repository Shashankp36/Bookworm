package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;

    @Column(nullable = false)
    private String authorName;

    private String bio;

    @Column(unique = true)
    private String contactEmail;
    
    

	public Author() {
		super();
	}
	

	public Author(String authorName, String bio, String contactEmail) {
		super();
		this.authorName = authorName;
		this.bio = bio;
		this.contactEmail = contactEmail;
	}


	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

    // Getters and Setters
    // ...
    
}
