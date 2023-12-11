package com.shopme.order;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.shopme.common.entity.ShoppingCart;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/check-out")
    public String checkout(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.getCustomerByEmail(username);
        if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddressLine1().trim().isEmpty()
        		|| customer.getAddressLine2().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()){

            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information after checkout!");
            return "customers/account_form";
        }else{
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getCart();
            model.addAttribute("cart", cart);
        }
        return "order/checkout";
    }

	
}
