package com.example.model;


import java.util.Collection;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

 // CORRECT - Uses SimpleGrantedAuthority

 // Add this import at the top of the file
 

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
     // This creates an authority object that correctly represents itself as the string "ROLE_ADMIN"
     return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
 }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();  // or user.getUserName()
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // or user.isActive();
    }

	@Override
	public String toString() {
		return "CustomUserDetails [user=" + user + "]";
	}
    
}
