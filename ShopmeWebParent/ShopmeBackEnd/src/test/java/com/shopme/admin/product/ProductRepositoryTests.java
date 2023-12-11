package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.shopme.common.entity.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false) //to keep the data permanent in db
public class ProductRepositoryTests {

	@Autowired	
	private ProductRepository repo;
	
	@Autowired	
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		
		Brand brand = entityManager.find(Brand.class, 9);
		Category category = entityManager.find(Category.class, 15);
		
		Product product = new Product();
		product.setName("Apple Galaxy A31");
		product.setAlias("apple_galaxyA31");
		product.setShortDescription("A good smartphone from Apple");
		product.setFullDescription("This is very good smartphone full description");
		
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setPrice(456);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct = repo.save(product);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);		
	}
	
	
	@Test
	public void testSavedProductWithImages() {
	   Integer productId =1;
	   Product product = repo.findById(productId).get();
	   
	   product.setMainImage("main image.jpg");
	   product.addExtraImage("extra image 1.png");
	   product.addExtraImage("extra image 2.png");
	   product.addExtraImage("extra image 3.png");
	   
	   Product savedProduct = repo.save(product);
	   
	   assertThat(savedProduct.getImages().size()).isEqualTo(3);
		
	}
	
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();
		
		product.addDetail("os", "windows");
		product.addDetail("color", "black");
		product.addDetail("model", "meditek");
		
		 Product savedProduct = repo.save(product);
		   
		 assertThat(savedProduct.getDetails()).isNotEmpty();
			
	}
}
