package com.shopme.product;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductDto {

	private Integer id;
	
	private String name;
	
	private String alias;
	
	private String shortDescription;
	
	private String fullDescription;
	
	private Date createdTime;
	
	private Date updatedTime;
		
	private boolean enabled;
	
	private boolean inStock;
		
	private float cost;
	
	private float price; 
	
	private float discountPercent;
	//for dimentions of product
	private float length;
	private float width;
	private float height;
	private float weight;
}
