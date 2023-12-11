package com.shopme.common.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="shopping_cart")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "shopping_cart_id")
	private Integer id;
	
	private int totalItems;
	
	private float totalPrice;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cart")
	private Set<CartItem> cartItem ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Set<CartItem> cartItem) {
		this.cartItem = cartItem;
	}

	public ShoppingCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
