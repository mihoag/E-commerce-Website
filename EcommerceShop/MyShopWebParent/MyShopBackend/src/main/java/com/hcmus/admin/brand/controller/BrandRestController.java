package com.hcmus.admin.brand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.brand.BrandService;

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
}
