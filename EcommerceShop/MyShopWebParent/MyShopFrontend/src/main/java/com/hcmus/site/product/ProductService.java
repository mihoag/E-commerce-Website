package com.hcmus.site.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.product.Product;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 10;

	@Autowired
	private ProductRepository repo;

	public Page<Product> listByCategory(int pageNum, Integer categoryId, String keyword) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

		System.out.println(pageNum - 1);
		System.out.println(categoryId);
		System.out.println(categoryIdMatch);
		return repo.listByCategory(categoryId, categoryIdMatch, keyword, pageable);
	}

	public Product getProduct(String alias) throws ProductNotFoundException {
		Product product = repo.findByAlias(alias);
		if (product == null) {
			throw new ProductNotFoundException("Could not find any product with alias " + alias);
		}
		return product;
	}

	public List<Product> getProductByCate(Category cate) {
		return repo.findByCategory(cate);
	}
}
