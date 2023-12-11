package com.shopme.shoppingcart;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.*;

@Service
public class ShoppingCartService {

	 @Autowired
	    private CartItemRepository itemRepository;

	    @Autowired
	    private ShoppingCartRepository cartRepository;

	  
	    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
	        ShoppingCart cart = customer.getCart();
System.out.println("here cart" + cart);
	        if (cart == null) {
	            cart = new ShoppingCart();
	        }
	        Set<CartItem> cartItems = cart.getCartItem();
	        
	        CartItem cartItem = findCartItem(cartItems, product.getId());
	        
	        if (cartItems == null) {
	            cartItems = new HashSet<>();
	            if (cartItem == null) {
	                cartItem = new CartItem();
	                cartItem.setProduct(product);
	                
	                cartItem.setTotalPrice(quantity * product.getDiscountPrice());
	                cartItem.setQuantity(quantity);
	                cartItem.setCart(cart);
	                cartItems.add(cartItem);
	                itemRepository.save(cartItem);
	            }
	        } else {
	            if (cartItem == null) {
	                cartItem = new CartItem();
	                cartItem.setProduct(product);
	                cartItem.setTotalPrice(quantity * product.getDiscountPrice());
	                cartItem.setQuantity(quantity);
	                cartItem.setCart(cart);
	                cartItems.add(cartItem);
	                itemRepository.save(cartItem);
	            } else {
	                cartItem.setQuantity(cartItem.getQuantity() + quantity);
	                cartItem.setTotalPrice(cartItem.getTotalPrice() + ( quantity * product.getDiscountPrice()));
	                itemRepository.save(cartItem);
	            }
	        }
	        cart.setCartItem(cartItems);

	        int totalItems = totalItems(cart.getCartItem());
	        float totalPrice = totalPrice(cart.getCartItem());

	        cart.setTotalPrice(totalPrice);
	        cart.setTotalItems(totalItems);
	        cart.setCustomer(customer);

	        return cartRepository.save(cart);
	    } 
	        
	  

	 private CartItem findCartItem(Set<CartItem> cartItems, Integer productId) {
	        if (cartItems == null) {
	            return null;
	        }
	        CartItem cartItem = null;

	        for (CartItem item : cartItems) {
	            if (item.getProduct().getId() == productId) {
	                cartItem = item;
	            }
	        }
	        return cartItem;
	    }
	 
	 private int totalItems(Set<CartItem> cartItems){
	        int totalItems = 0;
	        for(CartItem item : cartItems){
	            totalItems += item.getQuantity();
	        }
	        return totalItems;
	    }
	 
	 private float totalPrice(Set<CartItem> cartItems){
	        float totalPrice = 0.0f;

	        for(CartItem item : cartItems){
	            totalPrice += item.getTotalPrice();
	        }

	        return totalPrice;
	    }
	 
	 public ShoppingCart updateItemInCart(Product product, int quantity, Customer customer) {
	        ShoppingCart cart = customer.getCart();

	        Set<CartItem> cartItems = cart.getCartItem();

	        CartItem item = findCartItem(cartItems, product.getId());

	        item.setQuantity(quantity);
	        item.setTotalPrice(quantity * product.getDiscountPrice());

	        itemRepository.save(item);

	        int totalItems = totalItems(cartItems);
	        float totalPrice = totalPrice(cartItems);

	        cart.setTotalItems(totalItems);
	        cart.setTotalPrice(totalPrice);

	        return cartRepository.save(cart);
	    }
	 
	 
	 public ShoppingCart deleteItemFromCart(Product product, Customer customer) {
	        ShoppingCart cart = customer.getCart();

	        Set<CartItem> cartItems = cart.getCartItem();

	        CartItem item = findCartItem(cartItems, product.getId());

	        cartItems.remove(item);

	        itemRepository.delete(item);

	        float totalPrice = totalPrice(cartItems);
	        int totalItems = totalItems(cartItems);

	        cart.setCartItem(cartItems);
	        cart.setTotalItems(totalItems);
	        cart.setTotalPrice(totalPrice);

	        return cartRepository.save(cart);
	    }
	 
	  /*    Set<CartItem> cartItems = cart.getCartItem();
     CartItem cartItem = findCartItem(cartItems, product.getId());

     if (cartItem == null) {
         cartItem = new CartItem();
         cartItem.setProduct(product);
         cartItem.setTotalPrice(quantity * product.getCost());
         cartItem.setQuantity(quantity);
         cartItem.setCart(cart);
         cartItems.add(cartItem);
     } else {
         cartItem.setQuantity(cartItem.getQuantity() + quantity);
         cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getCost()));
     }

     itemRepository.save(cartItem);

     cart.setCartItem(cartItems);
     cart.setTotalItems(totalItems(cartItems));
     cart.setTotalPrice(totalPrice(cartItems));

     return cartRepository.save(cart);
 }
 
 
 */
}
