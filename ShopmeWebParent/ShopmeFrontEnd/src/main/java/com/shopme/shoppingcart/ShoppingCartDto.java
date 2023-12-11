package com.shopme.shoppingcart;

import java.util.Set;

import com.shopme.common.entity.Customer;

public class ShoppingCartDto {

	 private Integer id;

	    private Customer customer;

	    private double totalPrice;

	    private int totalItems;

	    private Set<CartItemDto> cartItems;
}
