package com.shopme.admin.category;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopme.common.entity.Category;
import com.shopme.common.exception.CategoryNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository cRepo;
	
	public List<Category> listAll() {
		//return cRepo.findAll();
		List<Category> rootCategories = cRepo.findRootCategories();
		return listHierarchicalCategories(rootCategories);
	}
	
	public Category saveCategory(Category category) {
		return cRepo.save(category);
	}
	
	public Category get(Integer id)throws CategoryNotFoundException{
		try {
			return cRepo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with ID "+ id);
		}
	}
	
	
	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		cRepo.updateEnabledStatus(id, enabled);
	}
	
	
	
	private List<Category> listHierarchicalCategories(List<Category> rootCategories){
		List<Category> hierarchicalCategories = new ArrayList<>();	
		
		for(Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory));
			
		Set<Category> children=	rootCategory.getChildren();
		
		for(Category subCategory : children) {
			String name= "--"+ subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory , 1 );
		}
		}
		
		return hierarchicalCategories;
	}
	
		
	public void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent , int subLevel) {
		Set<Category> children=	parent.getChildren();
		int newSubLevel = subLevel + 1;
		
		for(Category  subCategory : children) {
			String name= ""; 
			for(int i=0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory, name));
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);			
		}		
	}
	//code for getting categories in hirarchical manner
	public List<Category> listCategoriesUsedInForm(){
		List<Category> categoriesUsedInForm = new ArrayList<Category>();
	Iterable<Category> categories	= cRepo.findAll();
	
	for(Category category : categories) {
		if(category.getParent() == null) {
			categoriesUsedInForm.add(Category.copyIdAndName(category));
			Set<Category> children = category.getChildren();
			
			for(Category subCategory : children) {
				String name= "--"+ subCategory.getName();
				
				categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
				
				listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory, 1);
			}
		}	  
	}	
		return categoriesUsedInForm;
	}
	
	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {

		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}
	}
	
	public void delete(Integer id) throws CategoryNotFoundException {
		Long countById = cRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}
		
		cRepo.deleteById(id);
	}	
	
	
	
	
}
