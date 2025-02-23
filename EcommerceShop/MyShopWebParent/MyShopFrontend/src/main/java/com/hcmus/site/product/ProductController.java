package com.hcmus.site.product;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.Review;
import com.hcmus.common.entity.product.Product;
import com.hcmus.site.category.CategoryService;
import com.hcmus.site.review.ReviewService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/c/{alias}")
	public String listFirstPage(@PathVariable("alias") String alias, Model model) {
		return listByPage(alias, 1, "", model);
	}

	@GetMapping("/c/{alias}/page/{pageNum}")
	public String listByPage(@PathVariable("alias") String alias, @PathVariable("pageNum") Integer pageNum,
			@Param("keyword") String keyword, Model model) {
		if (keyword == null) {
			keyword = "";
		}
		Category cate = categoryService.getByAlias(alias);
		List<Category> listParentCategories = categoryService.getCategoryParents(cate);
		Page<Product> pageProducts = productService.listByCategory(pageNum, cate.getId(), keyword);

		model.addAttribute("searchField", "products");
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalElements", pageProducts.getTotalElements());
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("listProducts", pageProducts.getContent());
		model.addAttribute("category", cate);
		model.addAttribute("listParentCategories", listParentCategories);
		return "product/products_by_category";
	}

	@GetMapping("/p/{alias}")
	public String getDetailProduct(@PathVariable String alias, Model model) {
		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());

			Category categoryParent = product.getCategory();
			List<Product> relatedProducts = productService.getProductByCate(categoryParent);
			relatedProducts.remove(product);
			List<Review> reviews = reviewService.getReviewByProductId(product.getId());

			model.addAttribute("listReviews", reviews);
			model.addAttribute("relatedProducts", relatedProducts);
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);

			// model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getShortName());

			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}
}
