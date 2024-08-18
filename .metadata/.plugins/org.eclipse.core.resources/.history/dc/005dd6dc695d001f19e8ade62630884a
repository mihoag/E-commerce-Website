package com.hcmus.site.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.Customer;
import com.hcmus.site.setting.CountryRepository;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CountryRepository countryRepo;
	
	
	
	@GetMapping("/register")
	public String showRegisterForm(Model model)
	{
		List<Country> listCountries = countryRepo.findAll();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("title", "Customer Registration");
		model.addAttribute("customer", new Customer());
		
		return "register/register_form";
	}
	
}
