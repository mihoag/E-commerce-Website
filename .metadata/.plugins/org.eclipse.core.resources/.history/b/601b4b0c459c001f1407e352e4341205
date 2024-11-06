package com.hcmus.admin.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.CustomerDTO;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
     
	@Autowired
	private CustomerService cusService;
	
	@GetMapping
	public List<CustomerDTO> getAll(@Param("keyword") String keyword)
	{
		if(keyword == null)
		{
			keyword = "";
		}
		
		return convertDTO(cusService.getAllCustomer(keyword));
	}
	
	public List<CustomerDTO> convertDTO(List<Customer> customers)
	{
		List<CustomerDTO> dtos = new ArrayList<>();
		for(Customer customer : customers)
		{
			dtos.add(new CustomerDTO(customer.getId(),customer.getFirstName(), customer.getLastName(), customer.getAvatarPath()));
		}
		return dtos;
	}
	
	
	
}
