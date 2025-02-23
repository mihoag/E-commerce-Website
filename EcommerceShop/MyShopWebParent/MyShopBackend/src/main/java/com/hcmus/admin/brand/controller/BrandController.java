package com.hcmus.admin.brand.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.brand.BrandService;
import com.hcmus.admin.brand.export.BrandExcelExporter;
import com.hcmus.admin.category.CategoryService;
import com.hcmus.admin.util.AmazonS3Util;
import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;
import com.hcmus.common.exception.BrandNotFoundException;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/brands")
public class BrandController {
	private String defaultRedirectURL = "redirect:/brands/page/1?sortField=name&sortDir=asc";
	@Autowired private BrandService brandService;	
	@Autowired private CategoryService categoryService;

	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword,  Model model
			) {
		
		if(keyword == null)
	    {
	    	keyword = "";
	    }
	    Page<Brand> pageBrand= brandService.listByPage(pageNum, sortField, sortDir, keyword);
	    List<Brand> listBrand = pageBrand.getContent();
	    long totalElement = pageBrand.getTotalElements();
	    
	    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
	       
	    model.addAttribute("listBrand", listBrand);
		model.addAttribute("sideBarFieldName", "brands");
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageBrand.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
	    model.addAttribute("keyword", keyword);
		model.addAttribute("totalElement", totalElement);
	    
		return "brand/brand";		
	}
	
	@GetMapping("/new")
	public String newBrand(Model model) {
		List<Category> listCategories = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("brand", new Brand());
		model.addAttribute("title", "Create New Brand");
		model.addAttribute("sideBarFieldName", "brands");
		return "brand/brand_form";		
	}
	
	@PostMapping("/save")
	public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			brand.setLogo(fileName);
			
			Brand savedBrand = brandService.save(brand);
			String uploadDir = "brand-logos/" + savedBrand.getId();
			
			AmazonS3Util.removeFolder(uploadDir);
			AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
		
		} else {
			brandService.save(brand);
		}
		
		ra.addFlashAttribute("message", "The brand has been saved successfully.");
		return defaultRedirectURL + "&keyword=" + brand.getName();		
	}
	
	@GetMapping("/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Brand brand = brandService.get(id);
			List<Category> listCategories = categoryService.listCategoriesUsedInForm();
			
			model.addAttribute("brand", brand);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("title", "Edit Brand (ID: " + id + ")");
			
			return "brand/brand_form";			
		} catch (BrandNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return defaultRedirectURL;
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBrand(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			brandService.delete(id);
			String brandDir = "brand-logos/" + id;
			AmazonS3Util.removeFolder(brandDir);
			redirectAttributes.addFlashAttribute("message", 
					"The brand ID " + id + " has been deleted successfully");
		} catch (BrandNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return defaultRedirectURL;
	}
	
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<Brand> brands = brandService.listAll();
	    BrandExcelExporter exporter = new BrandExcelExporter();
	    exporter.export(brands, response);
	}
	
	
	@GetMapping("/**")
	public String listFirstPage() {
		return defaultRedirectURL;
	}
}
