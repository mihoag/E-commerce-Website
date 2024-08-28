package com.hcmus.admin.product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.product.ProductDTO;
import com.hcmus.admin.product.ProductService;
import com.hcmus.common.entity.product.Product;
import com.hcmus.common.exception.ProductNotFoundException;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductService service;
	
	@PostMapping("/checkname")
	public String check_unique(Integer id, String name)
	{
		return service.checkUnique(id, name);
	}
	
	@GetMapping("/{id}")
	public ProductDTO getProductInfo(@PathVariable("id") Integer id) 
			throws ProductNotFoundException {
		Product product = service.get(id);
		return new ProductDTO(product.getName(), product.getMainImagePath(), 
				product.getDiscountPrice(), product.getCost());
	}
}
