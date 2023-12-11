package com.shopme.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.shopme.common.entity.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)

public class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository repo;
	
	@Test
	public void testCreateCustomer()      
	{
		Customer customer = new Customer("mayuri@gmail.com","mayuri12345","mayuri","titirmare","1234567895","nagpur","nagpur","shahapur","India","maharashtra","1234");
		
		Customer savedCustomer = repo.save(customer);
		//assertThat(savedCustomer.getId()).isGreaterThan(0);	
	}
	
}
