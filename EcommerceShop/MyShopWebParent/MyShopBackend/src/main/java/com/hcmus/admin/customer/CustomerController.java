package com.hcmus.admin.customer;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.brand.export.BrandExcelExporter;
import com.hcmus.admin.customer.export.CustomerExcelExporter;
import com.hcmus.admin.setting.country.CountryRepository;
import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.product.Product;
import com.hcmus.common.exception.CustomerNotFoundException;
import com.hcmus.common.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CountryRepository countryRepo;
	
	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword, Model model)
	{
		if(keyword == null)
		{
			keyword = "";
		}
	    Page<Customer> pageCustomers = customerService.listByPage(pageNum, sortField, sortDir, keyword);
	    
	    
	    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
	    
	     model.addAttribute("listCustomers", pageCustomers.getContent());
	    
	     model.addAttribute("sideBarFieldName", "customers");
		 model.addAttribute("currentPage", pageNum);
		 model.addAttribute("totalPages", pageCustomers.getTotalPages());
		 model.addAttribute("sortField", sortField);
	     model.addAttribute("sortDir", sortDir);
		 model.addAttribute("reverseSortDir", reverseSortDir);
		 model.addAttribute("keyword", keyword);
	     model.addAttribute("totalElement", pageCustomers.getTotalElements());
	    
	    return "customers/customers";
	}
	
	@GetMapping("/detail/{id}")
	public String getDetailCustomer(@PathVariable("id") Integer id, Model model) throws CustomerNotFoundException
	{
		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		return "customers/customer_detail_modal";
	}
	
	@GetMapping("/edit/{id}")
	public String editCustomer(@PathVariable("id") Integer id, Model model) throws CustomerNotFoundException
	{
		Customer customer = customerService.getCustomerById(id);
		List<Country> listCountries = countryRepo.findAll();
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("customer", customer);
		return "customers/customer_update_form";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		customerService.deleteCustomer(id);
	
		redirectAttributes.addFlashAttribute("message", 
				"The customer has ID "+ id +" has been deleted successfully");
		return listFirstPage(model);
	}
	
	@PostMapping("/save")
	public String saveCustomer(Customer customer, Model model, RedirectAttributes redirectAttributes)
	{
		Customer savedCustomer =  customerService.saveCustomer(customer);
		redirectAttributes.addFlashAttribute("message", "Save the customer successfully");
		return listFirstPage(model);
	}
	
	@GetMapping("/customer/{id}/enabled/{status}")
	public String updateCustomerEnable(@PathVariable("id") int id, @PathVariable("status") boolean status, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword, @Param("page") int page, Model model)
	{
		customerService.updateCustomerEnable(id, status);
		return listByPage(page, sortField, sortDir, keyword, model);
	}
	
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<Customer> customers = customerService.getAllCustomer("");
	    CustomerExcelExporter exporter = new CustomerExcelExporter();
	    exporter.export(customers, response);
	}
	
	
	@GetMapping("/**")
	public String listFirstPage(Model model)
	{
		return listByPage(1, "id", "asc", "", model);
	}
	
}
