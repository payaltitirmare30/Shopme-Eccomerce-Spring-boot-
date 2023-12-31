package com.shopme.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.shopme.common.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories();
	 
	
	public Long countById(Integer id);
	
	@Query("UPDATE Category c SET c.enabled=?2 WHERE c.id=?1 ")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
