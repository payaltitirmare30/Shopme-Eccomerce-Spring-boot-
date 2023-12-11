package com.shopme.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

	
	 @Query(value = "update cart_items set shopping_cart_id = null where shopping_cart_id = ?1", nativeQuery = true)
	    void deleteCartItemById(Integer cartId);

	
	 
}
