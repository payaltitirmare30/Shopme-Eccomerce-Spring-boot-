package com.shopme.shoppingcart;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShoppingCart;
import com.shopme.common.exception.ProductNotFountException;
import com.shopme.customer.CustomerService;
import com.shopme.product.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

	@Autowired 
	private CustomerService customerService;
	
	@Autowired 
	private ShoppingCartService cartService;
	
	@Autowired
	public ProductService productService;
	
	@GetMapping("/cart")
	public String cart(Model model, Principal principal, HttpSession session) {
		
		if(principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		 Customer customer = customerService.getCustomerByEmail(username);
		ShoppingCart shoppingCart = customer.getCart();
		if(shoppingCart == null) {
			model.addAttribute("check", "No Items in Tour Cart");
			
		}
		session.setAttribute("totalItems", shoppingCart.getTotalItems());
		 model.addAttribute("shoppingCart", shoppingCart);
		 model.addAttribute("subTotal", shoppingCart.getTotalPrice());
			
		return "cart/cart";
	}
	
	 @PostMapping("/add-to-cart")
	    public String addItemToCart(
	            @RequestParam("id") int id,
	            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
	            Principal principal,
	            HttpServletRequest request) throws ProductNotFountException{

	        if(principal == null){
	            return "redirect:/login";
	        }
	        Product product = productService.getProductByID(id);
	        String username = principal.getName();
	        Customer customer = customerService.getCustomerByEmail(username);
             System.out.println(product);
             System.out.println(customer);
             
	        ShoppingCart cart = cartService.addItemToCart(product, quantity, customer);
	      System.out.println(cart);
	      
	        return "redirect:" + request.getHeader("Referer");

	    }
	 
	 @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
	 public String updateCart(@RequestParam("quantity") int quantity,
	                             @RequestParam("id") int productId,
	                             Model model,
	                             Principal principal
	                             ) throws ProductNotFountException{

	        if(principal == null){
	            return "redirect:/login";
	        }else{
	            String username = principal.getName();
	            Customer customer = customerService.getCustomerByEmail(username);
	            Product product = productService.getProductByID(productId);
	            ShoppingCart cart = cartService.updateItemInCart(product, quantity, customer);

	            model.addAttribute("shoppingCart", cart);
	            return "redirect:/cart";
	        }

	    }

	     @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
	    public String deleteItemFromCart(@RequestParam("id") int productId,
             Model model,
             Principal principal) throws ProductNotFountException{
		 			if(principal == null){
		 					return "redirect:/login";
		 				}else{
		 					String username = principal.getName();
		 					Customer customer = customerService.getCustomerByEmail(username);
		 					Product product = productService.getProductByID(productId);
		 					ShoppingCart cart = cartService.deleteItemFromCart(product, customer);
		 					model.addAttribute("shoppingCart", cart);
		 					
		 					return "redirect:/cart";
		 				}
	 				}
	 
	
  }





