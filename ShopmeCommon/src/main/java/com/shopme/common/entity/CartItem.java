package com.shopme.common.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	 @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST})
	    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
	    private ShoppingCart cart;
	
	 @OneToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "product_id")
	    private Product product;
	 
	    private int quantity;
	 
	   private float totalPrice;;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public ShoppingCart getCart() {
			return cart;
		}

		public void setCart(ShoppingCart cart) {
			this.cart = cart;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		
		public float getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(float totalPrice) {
			this.totalPrice = totalPrice;
		}

		public CartItem() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
}
