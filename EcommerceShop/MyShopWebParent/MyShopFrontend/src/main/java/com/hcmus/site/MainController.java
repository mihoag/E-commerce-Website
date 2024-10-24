package com.hcmus.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hcmus.common.entity.Category;
import com.hcmus.site.category.CategoryService;

@Controller
public class MainController {

	@Autowired
	private CategoryService service;

	@GetMapping("")
	public String userHome(Model model, @Param("keyword") String keyword) {
		if (keyword == null) {
			keyword = "";
		}
		List<Category> categories = service.seachByKeyWord(keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchField", "categories");
		model.addAttribute("listCategories", categories);
		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}
}
