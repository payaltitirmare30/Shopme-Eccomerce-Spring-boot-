package com.shopme.common.entity;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=128, nullable=false, unique=true)
	private String name;
	
	@Column(length=64, nullable=false, unique=true)	
	private String alias;
	
	@Column(length=128, nullable=false)	
	private String image;
	
	private boolean enabled;
	
	@Column(name="all_parent_ids", length=256, nullable = true)
	private String allParentIDs;
	
	@OneToOne
	@JoinColumn(name="parent_id")
	private Category parent;
	
	
	@OneToMany(mappedBy="parent")
	@OrderBy("name asc")
	private Set<Category> children = new HashSet<>();
	
	public Category() {
			}	
	
	public Category(Integer id) {
		this.id=id;
	}
	
	public Category(String name ) {
		super();
		this.name = name;
		this.alias= name;
		this.image="default.png";
	}

	public Category(String name ,Category parent) {
		this(name);
		this.parent=parent;
		}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	@Transient
	public String getImagePath() {
		if(this.id == null)return "/images/image-thumbnail.png";
		
		return "/category-images/" +this.id +"/"+this.image;
	}
		
	@Transient
	private boolean hasChildren;

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	//method to get id and name
	public static Category copyIdAndName(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		
		return copyCategory;
		}
	
	public static Category copyIdAndName(Integer id , String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		
		return category;
		}
	
	public static Category copyFull(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		copyCategory.setImage(category.getImage());
		copyCategory.setAlias(category.getAlias());
		copyCategory.setEnabled(category.isEnabled());
		copyCategory.setHasChildren(category.getChildren().size() > 0);
		
		return copyCategory;
	}
	//set category name including --
	public static Category copyFull(Category category, String name) {
		Category copyCategory = Category.copyFull(category);
		copyCategory.setName(name);
	 
		return copyCategory;
	}
	
	
}
