package com.hcmus.admin.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.category.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
	
	@Autowired
	private CategoryService service;
	
    // check unique name
    @PostMapping("/check_name")
	public String checkDuplicateName(Integer id ,  String name) {
		return service.checkUniqueName(id, name) ? "OK" : "Duplicated";
    }

	// check unique alias
    @PostMapping("/check_alias")
   	public String checkDuplicateAlias(Integer id ,  String alias) {
   		return service.checkUniqueAlias(id, alias) ? "OK" : "Duplicated";
    }
}
