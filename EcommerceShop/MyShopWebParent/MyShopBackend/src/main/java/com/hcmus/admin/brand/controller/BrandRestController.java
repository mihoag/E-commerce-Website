package com.hcmus.admin.brand.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.brand.BrandService;
import com.hcmus.admin.category.CategoryDto;
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;
import com.hcmus.common.exception.BrandNotFoundException;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

	@Autowired
	private BrandService service;
	  // check unique name
    @PostMapping("/check_name")
	public String checkDuplicateName(Integer id ,  String name) {
		return service.checkUnique(id, name);
    }
    

	@GetMapping("/{id}/categories")
	public List<CategoryDto> listCategories(@PathVariable("id") Integer id) throws BrandNotFoundException
	{
		Brand brand = service.get(id);
		Set<Category> listCategories = brand.getCategories();
		
		List<CategoryDto> categories = new ArrayList<>();
		for(Category cate : listCategories)
		{
			CategoryDto dto = new CategoryDto();
			dto.setId(cate.getId());
			dto.setName(cate.getName());
			categories.add(dto);
		}
		return categories;
		//return listCategorie
	}
}
