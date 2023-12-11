package com.shopme.admin.category;

import java.io.IOException;
import java.util.List;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.*;
import com.shopme.common.exception.CategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String listAll(Model model) {
		List<Category> listCategories = service.listAll();
		model.addAttribute("listCategories",listCategories);
		return "categories/categories";		
	}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		List<Category> listCategories = service.listCategoriesUsedInForm();
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("category", new Category());
		model.addAttribute("pageTitle", "Create new Category");
		
		return "categories/category_form";
	}
	
	@PostMapping("/categories/save") //@REquestParam is used bcz we have file in form 
	public String saveCategory(Category category, @RequestParam("fileImage")MultipartFile multipartFile, 
			  RedirectAttributes  ra) throws IOException {
		if(!multipartFile.isEmpty()) //if multipartfile is not empth then save the image
		{
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());		
		category.setImage(fileName);
		
		Category savedCategory = service.saveCategory(category);
		String uploadDir ="../category-images/"+ savedCategory.getId(); //using two.. to create images outside of the project directory
		
		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);		
		}else {
			   service.saveCategory(category);
		}
		ra.addFlashAttribute("message","TheCategory has been saved succefully ");		
		return "redirect:/categories";	
	}
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name="id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Category category = service.get(id);
			List<Category> listCategories = service.listCategoriesUsedInForm();
			
			model.addAttribute("category",category);
			model.addAttribute("listCategories",listCategories);
			model.addAttribute("pageTitle","Edit Category (ID: "+ id +")");
			
			return "categories/category_form";			
		} catch(CategoryNotFoundException ex) {
			ra.addFlashAttribute("message",ex.getMessage());
			return "redirect:/categories";
		}
	}
	
	@GetMapping("/categories/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status")boolean enabled, RedirectAttributes ra) {
		service.updateCategoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled": "disabled"; 
		String message = "The Category ID "+ id+ "has been "+status ;
		ra.addFlashAttribute("message",message);
		
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes) {
	try {
		service.delete(id);
	     String categoryDir= "../category-images/" +id;
	     FileUploadUtil.cleanDir(categoryDir);
	    
	     redirectAttributes.addFlashAttribute("message", "The category ID "+ id +" has been deleted successfully");
	}
	catch(CategoryNotFoundException ex) {
		redirectAttributes.addFlashAttribute("message", ex.getMessage());
	}
	return "redirect:/categories";
	}
	
}
