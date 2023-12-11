package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFountException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll(){
		return repo.findAll();
	}
	
	public Product save(Product product) {
		if(product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		}else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
	
		product.setUpdatedTime(new Date());
		
		return repo.save(product);		
	}	
	
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
		
	public void delete(Integer id) throws ProductNotFountException {
		Long countById = repo.countById(id);
		
		if(countById == null || countById==0) {
			throw new ProductNotFountException("Could not find any product with ID"+ id);
		}
		repo.deleteById(id);
	}
	
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);
		
		if(isCreatingNew) {
			if(productByName != null) return "Duplicate";
		}
		else {
			if(productByName != null && productByName.getId() != id ) {
				return "Duplicate";
			}
		}
		return "OK";
	}
	
	public Product getProductById(Integer id ) throws ProductNotFountException {
		try {
			return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new ProductNotFountException("Could not find any product with ID"+ id);			
		}
	}
}
