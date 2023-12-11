package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.shopme.common.entity.*;
import com.shopme.customer.CustomerRepository;

public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer =customerRepo.findByEmail(email);
		if(customer ==null) {
			throw new UsernameNotFoundException("Could not find customer with email:"+ email);
			
		}
		return new CustomerUserDetails(customer);
				
	}

}
