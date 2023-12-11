package com.shopme.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.*;
import com.shopme.security.CustomerUserDetails;

@Controller
public class CustomerController {

   @Autowired
   private CustomerService service;
   
   @GetMapping("/register")
   public String shoeRegisterForm(Model model) {
	    
	  model.addAttribute("pageTitle", "Customer Registration");
	  model.addAttribute("customer", new Customer());
	  
	  return "register/register_form";
   }
   
  @PostMapping("/create_customer")
   public String createCustomer(Customer customer, Model model) {
   service.registerCustomer(customer);
   
   model.addAttribute("pageTitle", "Registration Suceeded!");
   
      return "register/register_success";
   }
 
  @GetMapping("/customer_account")
  public String viewCustomerDetails(@AuthenticationPrincipal CustomerUserDetails loggedUser, Model model) {
	  String email = loggedUser.getUsername();
	  System.out.println(email);
	 Customer customer = service.getCustomerByEmail(email);
	 
	 model.addAttribute("customer", customer);
	 model.addAttribute("pageTitle", "your Account Details");
	 
	 return "customers/my_account";
  }
  
  @PostMapping("/customer_account/update")
  public String updateCustomer(Customer customer , RedirectAttributes redirectAttribute  ) {
			
	 Customer customers = service.updateAccount(customer);
	 	
	  return "redirect:/customer_account ";
	  
	  
  }
  
  @GetMapping("/customer_account_update")
  public String viewCustomerDetailsForUpdating(@AuthenticationPrincipal CustomerUserDetails loggedUser, Model model) {
	  String email = loggedUser.getUsername();
	  System.out.println(email);
	 Customer customer = service.getCustomerByEmail(email);
	 
	 model.addAttribute("customer", customer);
	 model.addAttribute("pageTitle", "your Account Details");
	 
	 return "customers/account_form";
  }
}
