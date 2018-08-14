package com.polozhaev.business.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name = "users")
public class User implements UserDetails {

	// ROLE_ADMIN
	// ROLE_USER

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "email")
	private String email;

	@NonNull
	@Column(name = "username", unique = true)
	private String username;

	@NonNull
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role = "ROLE_USER";

	@NonNull
	@Column(name = "enabled")
	private boolean enabled = true;

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public void setUsername(String username) {
		this.username = username;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return this.role.equals("ROLE_ADMIN") ? true : false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + ", role="
				+ role + "]";
	}

	
	
	public String getPassword() {
		return password;
	}


	public String getUsername() {
		return username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
