package com.shopme.common.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

 	@Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
		
	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 64)
	private String password;
	
	@Column(name="first_name" ,nullable = false, length = 45)
	private String firstName;
	
	@Column(name="last_name" ,nullable = false, length = 45)
	private String lastName;
	
	@Column(name="phone_number" ,nullable = false, length = 15)
	private String phoneNumber;
	
	@Column(nullable = false, length = 64)
	private String addressLine1;
	
	@Column(name="address_line_2",nullable = false, length = 64)
	private String addressLine2;
	
	@Column(nullable = false, length = 45)
	private String city;
	
	@Column(nullable = false, length = 45)
	private String country;
	
	@Column(nullable = false, length = 45)
	private String state;
	
	@Column(name="postal_code", nullable = false, length = 64)
	private String postalCode;
	
	private boolean enabled;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private ShoppingCart cart;
	
	
	public Customer() {
	}

	public Customer(Integer id) {
		this.id = id;
	}
	

	public Customer(String email, String password, String firstName, String lastName, String phoneNumber,
			String addressLine1, String addressLine2, String city, String country, String state, String postalCode) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.state = state;
		this.postalCode = postalCode;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", city=" + city + "]";
	}

	
	
	
}
