package com.shopme.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shopme.common.entity.*;
import com.shopme.shoppingcart.CustomerNotFoundException;

import java.util.*;

@Service
public class CustomerService {

		@Autowired
	   private CustomerRepository customerRepo;
	
		@Autowired
		PasswordEncoder passwordEncoder;  
	

	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null ; //if its null means customer is unique
	}
	
	public void registerCustomer(Customer customer) {
		encodePassword(customer);
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		
		customerRepo.save(customer);
	} 

	private void encodePassword(Customer customer) {
		
		 String encodedPassword = passwordEncoder.encode(customer.getPassword());
	     customer.setPassword(encodedPassword);
	     
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepo.findByEmail(email);
	}
	
	public Customer updateAccount(Customer customer) {
		/*Customer customerInDB = customerRepo.findById(customerInForm.getId()).get();
		
		if(! customerInDB.getPassword().isEmpty()) {
			customerInDB.setPassword(customerInForm.getPassword());
			encodePassword(customerInDB);
		}*/
		if(!customer.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(customer.getPassword());
			customer.setPassword(encodedPassword);
		}else {
			Customer customerInDB = customerRepo.findById(customer.getId()).get();
			customer.setPassword(customerInDB.getPassword());
		}
		
		customer.setEnabled(true);
		return customerRepo.save(customer);
	}
	
	public Customer getCustomerById(Integer id) throws CustomerNotFoundException {
		try {
		return customerRepo.findById(id).get();
		
		}catch(NoSuchElementException ex)
		{
			throw new CustomerNotFoundException("Could not find any user with ID"+id);
		}
	}
}
