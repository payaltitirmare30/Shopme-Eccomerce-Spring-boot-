package com.shopme.admin.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import com.shopme.common.entity.User;
import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.Role;
@Controller
public class UserController {
  
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers",listUsers);
		
		return "users/users";
		
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles =service.listRoles();
		
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "users/user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user , RedirectAttributes redirectAttribute ,
			@RequestParam("image") MultipartFile multipartFile	) throws IOException {
		
		System.out.println(user);
		//if image is null then not storing/saving form data 
		if(!multipartFile.isEmpty()) {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setPhotos(fileName);
		 User saveUser = service.save(user);
		 
		String  uploadDir = "user-photos/" +saveUser.getId();
		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		else {  
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
				service.save(user);
			
		}
		redirectAttribute.addFlashAttribute("message","The user has been saved succefully");
		return "redirect:/users";
	} 
	

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id")Integer id ,RedirectAttributes redirectAttribute,Model model) {
		 
		try {
			List<Role> listRoles =service.listRoles();
			User user = service.getUserById(id);
				
			model.addAttribute("user",user);
			model.addAttribute("pageTitle","Edit User (ID: "+id+ ")");
			model.addAttribute("listRoles",listRoles);
			
			  return "users/user_form";
			
			} catch (UserNotFoundException e) {
				  redirectAttribute.addFlashAttribute("message", e.getMessage());
				  return "redirect:/users"; 
			}
		
	}  
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name="id")Integer id  ,Model model,RedirectAttributes redirectAttributes) throws UserNotFoundException {
		try {
	          service.deleteUser(id);
	          redirectAttributes.addFlashAttribute("message", "The User ID "+id+" has been deleted successfully");
		}catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
	    return "redirect:/users";
	}
	  
	@GetMapping("/users/{id}/enabled/{status}")
	public String userEnabledStatus(@PathVariable (name="id")Integer id, @PathVariable(name="status")boolean enabled, RedirectAttributes redirectAttributes) {
	
	     service.updateUserEnabledStatus(id, enabled);
	     String status = enabled ? "enabled":"disabled";
	     redirectAttributes.addFlashAttribute("message", "The User ID"+ id +" has been "+status +"  successfully");
		 return "redirect:/users";
	} 
	
	
}
