package com.example.dto;


public class UserLoginDTO {
    private String email;
    private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserLoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public UserLoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserLoginDTO [email=" + email + ", password=" + password + "]";
	}

    // Getters and setters
}
