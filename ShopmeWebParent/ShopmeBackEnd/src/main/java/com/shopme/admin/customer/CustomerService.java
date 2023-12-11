package com.shopme.admin.customer;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	List<Customer> listAllCustomer(){
		return repo.findAll();
	}
	
	public void createCustomer(Customer customer ) {		
		
	boolean isUpdatingCustomer = (customer.getId() !=null);
	if(isUpdatingCustomer) {
		Customer existingCustomer = repo.findById(customer.getId()).get();
		 
		if(customer.getPassword().isEmpty()) {
			customer.setPassword(existingCustomer.getPassword());
		}
		else {
			encodePassword(customer);
		}
	}
	else {	
		encodePassword(customer);
	}
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		 repo.save(customer);
	}
	 
	public void save (Customer customer) {
		if(!customer.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(customer.getPassword());
			customer.setPassword(encodedPassword);
		}else {
			Customer customerInDB = repo.findById(customer.getId()).get();
			customer.setPassword(customerInDB.getPassword());
		}
		repo.save(customer);
	}
	
	private void encodePassword(Customer customer) {
		
		 String encodedPassword = passwordEncoder.encode(customer.getPassword());
	     customer.setPassword(encodedPassword);	     
	}
	
	public Customer getCustomerById(Integer id) throws CustomerNotFoundException {
		 try {
		  return repo.findById(id).get();
		 }catch(NoSuchElementException ex) {
			 throw new CustomerNotFoundException("Could not find any user with ID"+id);		
		 }
		 
	}
	
public void delete(Integer id) throws CustomerNotFoundException {
		Long count = repo.countById(id);
		if (count == null || count == 0) {
			throw new CustomerNotFoundException("Could not find any customers with ID " + id);
		}
		
		repo.deleteById(id);
     }

	public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
	
}
