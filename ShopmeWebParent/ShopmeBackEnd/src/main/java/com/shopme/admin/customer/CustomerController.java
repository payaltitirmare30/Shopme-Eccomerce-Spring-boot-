package com.shopme.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.user.UserService;
import com.shopme.common.entity.Customer;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/customers")
	public String listAll(Model model) {
		
		List<Customer> listCustomer = service.listAllCustomer();
		model.addAttribute("listCustomer",listCustomer);
		
		return "customers/customers";		
	}
	
	@GetMapping("/customers/new")
	public String newCustomer(Model model) {
		
		model.addAttribute("customer",new Customer());
		model.addAttribute("pageTitle", "Create New Customer");
		return "customers/customers_form";
	}
	
	 @PostMapping("/customers/save")
	   public String createCustomer( Customer customer , RedirectAttributes redirectAttribute) {
		service.save(customer);
		 
		 redirectAttribute.addFlashAttribute("message","The customer has been saved succefully");
	     return "redirect:/customers";
	   }
	 
	 
	 @GetMapping("/customers/edit/{id}")
		public String editUser(@PathVariable(name="id")Integer id ,RedirectAttributes redirectAttribute,Model model) {
		try {
		 Customer customer = service.getCustomerById(id);
		 
		model.addAttribute("customer",customer);
		model.addAttribute("pageTitle","Edit Customer (ID: "+id+ ")");
		
		return "customers/customers_form";
		
		}catch(CustomerNotFoundException e) {
			redirectAttribute.addFlashAttribute("message", e.getMessage());
			 return "redirect:/customers";
		}
		
	   }	 
	
	 @GetMapping("/customers/{id}/enabled/{status}")
		public String updateCustomerEnabledStatus(@PathVariable("id") Integer id,
				@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
			service.updateCustomerEnabledStatus(id, enabled);
			String status = enabled ? "enabled" : "disabled";
			String message = "The Customer ID " + id + " has been " + status;
			redirectAttributes.addFlashAttribute("message", message);
			
			return "redirect:/customers";
		}	
	 
	 @GetMapping("/customers/delete/{id}")
		public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
			try {
				service.delete(id);			
				ra.addFlashAttribute("message", "The customer ID " + id + " has been deleted successfully.");
				
			} catch (CustomerNotFoundException ex) {
				ra.addFlashAttribute("message", ex.getMessage());
			}
			
			return "redirect:/customers";
		}
}
