package com.hcmus.site.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService cusService;
	
	@PostMapping("/check_email")
	public String checkByEmail(String email)
	{
		return cusService.checkEmail(email);
	}
}
