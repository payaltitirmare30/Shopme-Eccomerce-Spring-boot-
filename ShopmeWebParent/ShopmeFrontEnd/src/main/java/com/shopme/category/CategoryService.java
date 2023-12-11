package com.shopme.category;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listNoChildCategories(){
		
		 List<Category> listNoChildCategories = new ArrayList<>();
		 
		 List<Category> listEnabledCategories = repo.findAllEnabled();

		 listEnabledCategories.forEach(category ->{
			 Set<Category> children = category.getChildren();
			 if(children == null || children.size()==0) {
				 listNoChildCategories.add(category);
			 }
		 });
		 return listNoChildCategories;
	}
	
	public Category getCategory(String alias) {
		return repo.findByAliasEnabled(alias);
	}
	
	public List<Category> getCategoryParents(Category child){
		List<Category> listParents = new ArrayList<>();
		
		Category parent = child.getParent();
		while(parent != null) {
			listParents.add(0, parent);
			parent = parent.getParent();
			
		}
		listParents.add(child);
		
		return listParents;
	}
}
