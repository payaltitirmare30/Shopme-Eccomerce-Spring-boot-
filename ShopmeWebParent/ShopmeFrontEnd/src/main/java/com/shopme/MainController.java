package com.shopme;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.shopme.category.CategoryService;
import com.shopme.common.entity.ShoppingCart;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(" ")
	public String viewHomePage(Model model, Principal principal , HttpSession session)
	{
		if(principal != null) {
			session.setAttribute("username", principal.getName());
			Customer customer = customerService.getCustomerByEmail(principal.getName());
			ShoppingCart cart = customer.getCart();
			session.setAttribute("totalItems", cart.getTotalItems());
		}
		else {
			session.removeAttribute("username");
				
		}
		List<Category> listCategories = categoryService.listNoChildCategories();
		model.addAttribute("listCategories", listCategories);
		
		return "index";
	}
		
	/*@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication ==null) {
			return "login";
		}
		
		return "redirect :/";
	} */
	
	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}
	
}
