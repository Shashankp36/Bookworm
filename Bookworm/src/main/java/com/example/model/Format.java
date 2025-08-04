package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Formats")
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formatId;

    @Column(unique = true, nullable = false)
    private String formatName;

	public Format() {
		super();
	}

	public Format(String formatName) {
		super();
		this.formatName = formatName;
	}

	public int getFormatId() {
		return formatId;
	}

	public void setFormatId(int formatId) {
		this.formatId = formatId;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

    
    // Getters and Setters
    // ...
    
}
