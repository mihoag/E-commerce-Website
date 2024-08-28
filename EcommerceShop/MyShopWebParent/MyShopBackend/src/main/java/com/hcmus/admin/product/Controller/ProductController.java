package com.hcmus.admin.product.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.brand.BrandService;
import com.hcmus.admin.category.CategoryService;
import com.hcmus.admin.product.ProductSaveHelper;
import com.hcmus.admin.product.ProductService;
import com.hcmus.admin.security.MyShopUserDetails;
import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.product.Product;
import com.hcmus.common.exception.BrandNotFoundException;
import com.hcmus.common.exception.ProductNotFoundException;
import com.hcmus.common.exception.UserNotFoundException;

@Controller
@RequestMapping("/products")
public class ProductController {

	private String defaultRedirectURL = "redirect:/products/page/%d?sortField=%s&sortDir=%s&keyword=%s&categoryId=%d";
	
	@Autowired private ProductService productService;
	@Autowired private BrandService brandService;
	@Autowired private CategoryService categoryService;
	

	
	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword, Integer categoryId, Model model) {
		
		     if(keyword == null)
		     {
		    	 keyword = "";
		     }
		     
		     Page<Product> pageProduct = productService.listByPage(pageNum, sortField, sortDir, keyword, categoryId);
		     
		
		     List<Product> lsProduct = pageProduct.getContent();
		     String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		     
		     
		     List<Category> listCategories = categoryService.listCategoriesUsedInForm();
				
			 if (categoryId != null) model.addAttribute("categoryId", categoryId);
		     
			 model.addAttribute("listCategories", listCategories);
		     model.addAttribute("products", lsProduct);
		     model.addAttribute("sideBarFieldName", "products");
			 model.addAttribute("currentPage", pageNum);
			 model.addAttribute("totalPages", pageProduct.getTotalPages());
			 model.addAttribute("sortField", sortField);
		     model.addAttribute("sortDir", sortDir);
			 model.addAttribute("reverseSortDir", reverseSortDir);
			 model.addAttribute("keyword", keyword);
		     model.addAttribute("totalElement", pageProduct.getTotalElements());
		     return "products/products";	
	}
	
	@GetMapping("/product/{id}/enabled/{status}")
	public String updateProductEnable(@PathVariable("id") int id, @PathVariable("status") boolean status, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword, @Param("page") int page, @Param("categoryId") Integer categoryId) throws UserNotFoundException
	{
		productService.updateProductEnabledStatus(id, status);
	    return String.format(defaultRedirectURL, page, sortField, sortDir,keyword, categoryId);
	}
	
	@GetMapping("/new")
	public String newProduct(Model model)
	{
		Product product = new Product();
		
		List<Brand> brands = brandService.listAll();
		
		
		model.addAttribute("listBrands", brands);
		model.addAttribute("product", product);
	    model.addAttribute("sideBarFieldName", "products");
		model.addAttribute("title", "Create New Product");
		model.addAttribute("numberOfExistingExtraImages", 0);
		return "products/products_form";
	}
	
	@GetMapping("/detail/{id}")
	public String viewProductDetails(@PathVariable("id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Product product = productService.get(id);			
			model.addAttribute("product", product);		
			return "products/product_detail_modal";
		} catch (ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			
			return defaultRedirectURL;
		}
	}	
	
	@PostMapping("/save")
	public String saveProduct(Product product, RedirectAttributes ra,
			@RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,			
			@RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues,
			@RequestParam(name = "imageIDs", required = false) String[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames,
			@AuthenticationPrincipal MyShopUserDetails loggedUser
			) throws IOException {
		
		if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Editor")) {
			if (loggedUser.hasRole("Salesperson")) {
				productService.saveProductPrice(product);
				ra.addFlashAttribute("message", "The product has been saved successfully.");			
				return  String.format(defaultRedirectURL, 1, "name", "asc",product.getName(), 0);
			}
		}
		
		ProductSaveHelper.setMainImageName(mainImageMultipart, product);
		ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
		ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);
		ProductSaveHelper.setProductDetails(detailIDs, detailNames, detailValues, product);
			
		Product savedProduct = productService.save(product);
		
		ProductSaveHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);
		
		ProductSaveHelper.deleteExtraImagesWeredRemovedOnForm(product);
		
		ra.addFlashAttribute("message", "The product has been saved successfully.");
		
		return  String.format(defaultRedirectURL, 1, "name", "asc",product.getName(), 0);
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		productService.delete(id);
		String productDir = "product-images/" + id;
		FileUploadUtil.removeDir(productDir);
		
		redirectAttributes.addAttribute("message", 
				"The product has ID "+ id +" has been deleted successfully");
		
		return  String.format(defaultRedirectURL, 1, "name", "asc", "", 0);
	}
	
	
	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable("id") Integer id, Model model,
			RedirectAttributes ra, @AuthenticationPrincipal MyShopUserDetails loggedUser) {
		try {
			Product product = productService.get(id);
			List<Brand> listBrands = brandService.listAll();
			Integer numberOfExistingExtraImages = product.getImages().size();
			
			boolean isReadOnlyForSalesperson = false;
			
			if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Editor")) {
				if (loggedUser.hasRole("Salesperson")) {
					isReadOnlyForSalesperson = true;
				}
			}
			
			model.addAttribute("sideBarFieldName", "products");
			model.addAttribute("isReadOnlyForSalesperson", isReadOnlyForSalesperson);
			model.addAttribute("product", product);
			model.addAttribute("listBrands", listBrands);
			model.addAttribute("title", "Edit Product (ID: " + id + ")");
			model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);
			
			return "products/products_form";
			
		} catch (ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return  String.format(defaultRedirectURL, 1, "name", "asc", "", 0);
		}
	}
	
	
	
	@GetMapping("/**")
	public String listFirstPage()
	{
		return String.format(defaultRedirectURL, 1, "name", "asc","", 0);
	}
	
	
}
