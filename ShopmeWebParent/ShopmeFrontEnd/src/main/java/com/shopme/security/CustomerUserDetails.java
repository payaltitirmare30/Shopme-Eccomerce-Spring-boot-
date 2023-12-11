package com.shopme.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import  org.springframework.security.core.authority.*;
import com.shopme.common.entity.User;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Role;
import java.util.*;
public class CustomerUserDetails implements UserDetails {

	
	private Customer customer;
	
	public CustomerUserDetails(Customer customer) {
		this.customer =customer;
	}  
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return null;
	}

	@Override 
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getEmail();
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
		return customer.isEnabled();
	}
   // method to show user full details in index page
	public String getFullname() {
		return this.customer.getFirstName()+" "+this.customer.getLastName();
	}
	
	public void setFirstName(String firstName) {
		this.customer.setFirstName(firstName);
	}
	
	public void setLastName(String lastName) {
		this.customer.setLastName(lastName);
	}
}
