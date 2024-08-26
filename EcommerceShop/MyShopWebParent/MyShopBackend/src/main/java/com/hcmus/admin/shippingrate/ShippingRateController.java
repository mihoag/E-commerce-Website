package com.hcmus.admin.shippingrate;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

import com.hcmus.admin.brand.export.BrandExcelExporter;
import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.ShippingRate;
import com.hcmus.common.exception.BrandNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/shipping_rates")
public class ShippingRateController {
	
	@Autowired private ShippingRateService shippingService;
	
	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword,  Model model
			) {
		
		if(keyword == null)
	    {
	    	keyword = "";
	    }
		
	    Page<ShippingRate> pageShipping = shippingService.listByPage(pageNum, sortField, sortDir, keyword);
	    List<ShippingRate> listShipping = pageShipping.getContent();
	    long totalElement = pageShipping.getTotalElements();
	    
	    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
	       
	    model.addAttribute("listShipping", listShipping);
		model.addAttribute("sideBarFieldName", "shipping_rates");
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageShipping.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
	    model.addAttribute("keyword", keyword);
		model.addAttribute("totalElement", totalElement);
	    
		return "shipping_rates/shippingrate";		
	}
	
	@GetMapping("/cod/{id}/enabled/{enabled}")
	public String updateCod(@PathVariable("id") Integer id, @PathVariable("enabled") boolean enabled,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword, @Param("page") int page, Model model) throws ShippingRateNotFoundException
	{
		System.out.println(enabled);
		shippingService.updateCOD(id, enabled);
		return listByPage(page, sortField, sortDir, keyword, model);
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRate(@PathVariable(name = "id") Integer id,
			Model model, RedirectAttributes ra) {
		try {
			shippingService.delete(id);
			ra.addFlashAttribute("message", "The shipping rate ID " + id + " has been deleted.");
		} catch (ShippingRateNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return listFirstPage(model);
	}	
	
	
	@GetMapping("/new")
	public String newRate(Model model) {
		List<Country> listCountries = shippingService.listAllCountries();
		
		model.addAttribute("rate", new ShippingRate());
		model.addAttribute("listCountries", listCountries);	
		model.addAttribute("title", "New Shipping Rate");
		model.addAttribute("sideBarFieldName", "shipping_rates");
		return "shipping_rates/shipping_rate_form";		
	}
	
	@GetMapping("/edit/{id}")
	public String editRate(@PathVariable(name = "id") Integer id,
			Model model, RedirectAttributes ra) {
		try {
			ShippingRate rate = shippingService.get(id);
			List<Country> listCountries = shippingService.listAllCountries();
			
			model.addAttribute("listCountries", listCountries);			
			model.addAttribute("rate", rate);
			model.addAttribute("title", "Edit Rate (ID: " + id + ")");
			model.addAttribute("sideBarFieldName", "shipping_rates");
			
			return "shipping_rates/shipping_rate_form";
		} catch (ShippingRateNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return listFirstPage(model);
		}
	}


	@PostMapping("/save")
	public String saveRate(ShippingRate rate, RedirectAttributes ra, Model model) {
		try {
			shippingService.save(rate);
			ra.addFlashAttribute("message", "The shipping rate has been saved successfully.");
		} catch (ShippingRateAlreadyExistsException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		 return listByPage(1, "country", "asc", rate.getState(), model);
	}
	
	
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<ShippingRate> shippingRates = shippingService.listAll();
	    ShippingRateExcelExporter exporter = new ShippingRateExcelExporter();
	    exporter.export(shippingRates, response);
	}
	
	@GetMapping("/**")
	public String listFirstPage(Model model)
	{
	   return listByPage(1, "country", "asc", "", model);
	}
	
	
}
