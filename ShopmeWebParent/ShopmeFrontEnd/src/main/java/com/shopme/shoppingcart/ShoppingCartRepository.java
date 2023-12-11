package com.shopme.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopme.common.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

}
